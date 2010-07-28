/**
 * StateNodeAssembler.java

 */
package com.porpoise.gen.database.assembler.finitestatemachine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.porpoise.gen.finitestatemachine.statenode.StateNodeBuilder;
import com.porpoise.gen.finitestatemachine.statenode.IStateNodeDetails;

import com.porpoise.gen.finitestatemachine.statemachine.IStateMachineDetails;
import com.porpoise.gen.metadata.finitestatemachine.StateMachineMetaData;
import com.porpoise.gen.finitestatemachine.statetransition.IStateTransitionDetails;
import com.porpoise.gen.metadata.finitestatemachine.StateTransitionMetaData;

import com.porpoise.gen.metadata.finitestatemachine.StateNodeMetaData;
import com.porpoise.gen.metadata.IMetaDataField;

import com.porpoise.gen.database.assembler.AbstractAssembler;
import com.porpoise.gen.cache.ObjectCache;
import com.porpoise.gen.database.assembler.AssemblerContext;
import com.porpoise.gen.database.IDbTransaction;
import com.porpoise.gen.database.dao.finitestatemachine.statenode.StateNodeDao;
import com.porpoise.gen.metadata.IAssembler;

/**
 * StateNodeAssembler
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 *
 * A StateNodeAssembler assembler can construct state node objects from disparate sources - a database
 * transaction, cache and ID
 */
public final class StateNodeAssembler
{

    public static IAssembler<Long, IStateNode> getAssembler(final IDbTransaction transaction, final ObjectCache cache)
    {
        return new AbstractAssembler<Long, IStateNode>(transaction, cache)
        {
            @Override
            public StateNode assemble(final Long stateNodeId)
            {
                return StateNodeAssembler.assemble(getTransaction(), getCache(), stateNodeId, false );
            }
        };
    }

    /**
     *
     */
    private StateNodeAssembler()
    {
        //uninstantiable();
    }

    /**
     * @param transaction
     *            The database transaction for all entities which need to be
     *            loaded
     * @param objectCache
     *            The object cache to consult for cached entities
     * @param ids
     *            The list of IDs to load
     * @return a list of {@link IStateNode} objects
     */
    public static List<StateNode> assembleList(final IDbTransaction transaction, final ObjectCache objectCache,
            final Collection<Long> ids)
    {
        final List<StateNode> stateNodeList = new ArrayList<StateNode>();
        for (final Long id : ids)
        {
            stateNodeList.add(assemble(transaction, objectCache, id));
        }
        return stateNodeList;
    }
    /**
     * Assembly a full StateNode object from the cache and transaction
     * 
     * @param transaction
     *            the database transaction to load
     * @param objectCache
     *            the cache from which to load cached objects
     * @param stateNodeId
     *            the ID of the stateNode to load
     * @return the loaded stateNode
     */
    public static StateNode assemble(final IDbTransaction transaction, final ObjectCache objectCache, final Long stateNodeId)
    {
        return assemble(transaction, objectCache, stateNodeId, true);
    }
    /**
     * Assembly a full StateNode object from the cache and transaction
     * 
     * @param transaction
     *            the database transaction to load
     * @param objectCache
     *            the cache from which to load cached objects
     * @param stateNodeId
     *            the ID of the stateNode to load
     * @param assembleDeep
     *            flag to determine if an object should be a shallow or deep copy      * @return the loaded stateNode
     */
    public static StateNode assemble(final IDbTransaction transaction, final ObjectCache objectCache, final Long stateNodeId, final boolean assembleDeep)
    {
        StateNode stateNode = objectCache.getStateNodeMap().get(stateNodeId);
        if (stateNode == null)
        {
            final StateNodeBuilder stateNodeBuilder = StateNodeDao.getStateNodeBuilder(transaction, stateNodeId);
            
            if (stateNodeBuilder != null)
            {
                stateNode = stateNodeBuilder.build();
                objectCache.getStateNodeMap().put(stateNodeId, stateNode);
                if (assembleDeep)
                {            
                    //
                    // Transitions
                    //
                    stateNodeBuilder.setTransitions(StateTransitionAssembler.assembleList(transaction, objectCache, stateNodeBuilder.getTransitionsIds()));
                 }             }        }
        return stateNode;
    }



    /**
     * save
     * 
     * @param context
     * @param stateNode
     * @return the ID of the saved stateNode 
     */
    public static Long save(final AssemblerContext context, final StateNode stateNode)
    {

        // ============================================================
        //
        // COMMIT THE STATE NODE ITSELF  
        //
        // ============================================================
        final IStateMachine stateMachineNodes = 
            context.getReference(
                StateMachineMetaData.StateMachineDescriptor.NODES,
                stateNode);
        
        final Long stateMachineNodesId;
        if (stateMachineNodes != null)
        {
            stateMachineNodesId = stateMachineNodes.getId();
        }
        else
        {
            stateMachineNodesId = null;
        }

 
        if (stateNode.getId() == null)
        {
        
            final Long id = StateNodeDao.insertStateNode(context.getTransaction(), 
                stateNode, 
                stateMachineNodesId
             );
            stateNode.setId(id);
        }
        else
        {
            StateNodeDao.updateStateNode(context.getTransaction(), stateNode, 
                stateMachineNodesId
             );
        }

        context.getObjectCache().getStateNodeMap().put(stateNode.getId(), stateNode);

        // ============================================================
        //
        // THE ONE-TO-MANY FIELDS: these need to be persisted afterward
        // our StateNode
        //
        // ============================================================
        //
        // Transitions list
        //
        final List<StateTransition> transitionsList = stateNode.getTransitions();
        if (transitionsList != null)
        {
            for (final StateTransition transitionsItem : transitionsList)
            {
                context.registerReference(
                    StateNodeMetaData.StateNodeDescriptor.TRANSITIONS,
                    transitionsItem,
                    stateNode
                );

                final Long id = StateTransitionAssembler.save(context, transitionsItem);
                if (transitionsItem.getId() == null)
                {
                    transitionsItem.setId(id);
                }
            }
        }
        else
        {
            // TODO - delete old transitions - ?
        }
 

        return stateNode.getId();
    }


    /**
     * persist the state node to the database using the given transaction.
     * 
     * TODO
     * This method should NOT be used directly, however. Individual 
     * object actions should be used. 
     * 
     * @param transaction
     *            the Transaction used to persist the state node
     * @param stateNode
     *            the state node to persist
     *
    public static void persistStateNode(final IDbTransaction transaction, final StateNode stateNode,  final Long stateMachineNodesId  )
    {
         final Long stateNodeId = StateNodeDao.insertStateNode(transaction, stateNode);
        stateNode.setId(stateNodeId);

        //
        // Transitions
        //
                // State Node.Transitions : State Node.Transitions -> State Transition
                //
        final List<StateTransition> transitionsList = stateNode.getTransitions();
        for (final StateTransition item : transitionsList)
        {
//
// oneToMany referencing forms:
// , 
//stateNode
//Transitions
//
        
            StateTransitionAssembler.persistStateTransition(transaction, item, stateNodeId);
        }
      }
*/
}
