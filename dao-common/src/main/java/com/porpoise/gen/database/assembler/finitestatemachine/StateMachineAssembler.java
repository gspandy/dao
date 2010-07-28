/**
 * StateMachineAssembler.java

 */
package com.porpoise.gen.database.assembler.finitestatemachine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.porpoise.gen.finitestatemachine.statemachine.StateMachineBuilder;
import com.porpoise.gen.finitestatemachine.statemachine.IStateMachineDetails;

import com.porpoise.gen.finitestatemachine.statenode.IStateNodeDetails;
import com.porpoise.gen.metadata.finitestatemachine.StateNodeMetaData;

import com.porpoise.gen.metadata.finitestatemachine.StateMachineMetaData;
import com.porpoise.gen.metadata.IMetaDataField;

import com.porpoise.gen.database.assembler.AbstractAssembler;
import com.porpoise.gen.cache.ObjectCache;
import com.porpoise.gen.database.assembler.AssemblerContext;
import com.porpoise.gen.database.IDbTransaction;
import com.porpoise.gen.database.dao.finitestatemachine.statemachine.StateMachineDao;
import com.porpoise.gen.metadata.IAssembler;

/**
 * StateMachineAssembler
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 *
 * A StateMachineAssembler assembler can construct state machine objects from disparate sources - a database
 * transaction, cache and ID
 */
public final class StateMachineAssembler
{

    public static IAssembler<Long, IStateMachine> getAssembler(final IDbTransaction transaction, final ObjectCache cache)
    {
        return new AbstractAssembler<Long, IStateMachine>(transaction, cache)
        {
            @Override
            public StateMachine assemble(final Long stateMachineId)
            {
                return StateMachineAssembler.assemble(getTransaction(), getCache(), stateMachineId, false );
            }
        };
    }

    /**
     *
     */
    private StateMachineAssembler()
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
     * @return a list of {@link IStateMachine} objects
     */
    public static List<StateMachine> assembleList(final IDbTransaction transaction, final ObjectCache objectCache,
            final Collection<Long> ids)
    {
        final List<StateMachine> stateMachineList = new ArrayList<StateMachine>();
        for (final Long id : ids)
        {
            stateMachineList.add(assemble(transaction, objectCache, id));
        }
        return stateMachineList;
    }
    /**
     * Assembly a full StateMachine object from the cache and transaction
     * 
     * @param transaction
     *            the database transaction to load
     * @param objectCache
     *            the cache from which to load cached objects
     * @param stateMachineId
     *            the ID of the stateMachine to load
     * @return the loaded stateMachine
     */
    public static StateMachine assemble(final IDbTransaction transaction, final ObjectCache objectCache, final Long stateMachineId)
    {
        return assemble(transaction, objectCache, stateMachineId, true);
    }
    /**
     * Assembly a full StateMachine object from the cache and transaction
     * 
     * @param transaction
     *            the database transaction to load
     * @param objectCache
     *            the cache from which to load cached objects
     * @param stateMachineId
     *            the ID of the stateMachine to load
     * @param assembleDeep
     *            flag to determine if an object should be a shallow or deep copy      * @return the loaded stateMachine
     */
    public static StateMachine assemble(final IDbTransaction transaction, final ObjectCache objectCache, final Long stateMachineId, final boolean assembleDeep)
    {
        StateMachine stateMachine = objectCache.getStateMachineMap().get(stateMachineId);
        if (stateMachine == null)
        {
            final StateMachineBuilder stateMachineBuilder = StateMachineDao.getStateMachineBuilder(transaction, stateMachineId);
            
            if (stateMachineBuilder != null)
            {
                stateMachine = stateMachineBuilder.build();
                objectCache.getStateMachineMap().put(stateMachineId, stateMachine);
                if (assembleDeep)
                {            
                    //
                    // Nodes
                    //
                    stateMachineBuilder.setNodes(StateNodeAssembler.assembleList(transaction, objectCache, stateMachineBuilder.getNodesIds()));
                 }             }        }
        return stateMachine;
    }



    /**
     * save
     * 
     * @param context
     * @param stateMachine
     * @return the ID of the saved stateMachine 
     */
    public static Long save(final AssemblerContext context, final StateMachine stateMachine)
    {

        // ============================================================
        //
        // COMMIT THE STATE MACHINE ITSELF  
        //
        // ============================================================
 
        if (stateMachine.getId() == null)
        {
        
            final Long id = StateMachineDao.insertStateMachine(context.getTransaction(), 
                stateMachine
             );
            stateMachine.setId(id);
        }
        else
        {
            StateMachineDao.updateStateMachine(context.getTransaction(), stateMachine
             );
        }

        context.getObjectCache().getStateMachineMap().put(stateMachine.getId(), stateMachine);

        // ============================================================
        //
        // THE ONE-TO-MANY FIELDS: these need to be persisted afterward
        // our StateMachine
        //
        // ============================================================
        //
        // Nodes list
        //
        final List<StateNode> nodesList = stateMachine.getNodes();
        if (nodesList != null)
        {
            for (final StateNode nodesItem : nodesList)
            {
                context.registerReference(
                    StateMachineMetaData.StateMachineDescriptor.NODES,
                    nodesItem,
                    stateMachine
                );

                final Long id = StateNodeAssembler.save(context, nodesItem);
                if (nodesItem.getId() == null)
                {
                    nodesItem.setId(id);
                }
            }
        }
        else
        {
            // TODO - delete old nodes - ?
        }
 

        return stateMachine.getId();
    }


    /**
     * persist the state machine to the database using the given transaction.
     * 
     * TODO
     * This method should NOT be used directly, however. Individual 
     * object actions should be used. 
     * 
     * @param transaction
     *            the Transaction used to persist the state machine
     * @param stateMachine
     *            the state machine to persist
     *
    public static void persistStateMachine(final IDbTransaction transaction, final StateMachine stateMachine )
    {
         final Long stateMachineId = StateMachineDao.insertStateMachine(transaction, stateMachine);
        stateMachine.setId(stateMachineId);

        //
        // Nodes
        //
                // State Machine.Nodes : State Machine.Nodes -> State Node
                //
        final List<StateNode> nodesList = stateMachine.getNodes();
        for (final StateNode item : nodesList)
        {
//
// oneToMany referencing forms:
// , 
//stateMachine
//Nodes
//
        
            StateNodeAssembler.persistStateNode(transaction, item, stateMachineId);
        }
      }
*/
}
