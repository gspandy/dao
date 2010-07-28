/**
 * StateTransitionAssembler.java

 */
package com.porpoise.gen.database.assembler.finitestatemachine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.porpoise.gen.finitestatemachine.statetransition.StateTransitionBuilder;
import com.porpoise.gen.finitestatemachine.statetransition.IStateTransitionDetails;

import com.porpoise.gen.finitestatemachine.statenode.IStateNodeDetails;
import com.porpoise.gen.metadata.finitestatemachine.StateNodeMetaData;

import com.porpoise.gen.metadata.finitestatemachine.StateTransitionMetaData;
import com.porpoise.gen.metadata.IMetaDataField;

import com.porpoise.gen.database.assembler.AbstractAssembler;
import com.porpoise.gen.cache.ObjectCache;
import com.porpoise.gen.database.assembler.AssemblerContext;
import com.porpoise.gen.database.IDbTransaction;
import com.porpoise.gen.database.dao.finitestatemachine.statetransition.StateTransitionDao;
import com.porpoise.gen.metadata.IAssembler;

/**
 * StateTransitionAssembler
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 *
 * A StateTransitionAssembler assembler can construct state transition objects from disparate sources - a database
 * transaction, cache and ID
 */
public final class StateTransitionAssembler
{

    public static IAssembler<Long, IStateTransition> getAssembler(final IDbTransaction transaction, final ObjectCache cache)
    {
        return new AbstractAssembler<Long, IStateTransition>(transaction, cache)
        {
            @Override
            public StateTransition assemble(final Long stateTransitionId)
            {
                return StateTransitionAssembler.assemble(getTransaction(), getCache(), stateTransitionId);
            }
        };
    }

    /**
     *
     */
    private StateTransitionAssembler()
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
     * @return a list of {@link IStateTransition} objects
     */
    public static List<StateTransition> assembleList(final IDbTransaction transaction, final ObjectCache objectCache,
            final Collection<Long> ids)
    {
        final List<StateTransition> stateTransitionList = new ArrayList<StateTransition>();
        for (final Long id : ids)
        {
            stateTransitionList.add(assemble(transaction, objectCache, id));
        }
        return stateTransitionList;
    }
    /**
     * Assembly a full StateTransition object from the cache and transaction
     * 
     * @param transaction
     *            the database transaction to load
     * @param objectCache
     *            the cache from which to load cached objects
     * @param stateTransitionId
     *            the ID of the stateTransition to load
     * @return the loaded stateTransition
     */
    public static StateTransition assemble(final IDbTransaction transaction, final ObjectCache objectCache, final Long stateTransitionId)
    {
        StateTransition stateTransition = objectCache.getStateTransitionMap().get(stateTransitionId);
        if (stateTransition == null)
        {
            final StateTransitionBuilder stateTransitionBuilder = StateTransitionDao.getStateTransitionBuilder(transaction, stateTransitionId);
            
            if (stateTransitionBuilder != null)
            {
                stateTransition = stateTransitionBuilder.build();
                objectCache.getStateTransitionMap().put(stateTransitionId, stateTransition);
             }        }
        return stateTransition;
    }



    /**
     * save
     * 
     * @param context
     * @param stateTransition
     * @return the ID of the saved stateTransition 
     */
    public static Long save(final AssemblerContext context, final StateTransition stateTransition)
    {

        // ============================================================
        //
        // COMMIT THE STATE TRANSITION ITSELF  
        //
        // ============================================================
        final IStateNode stateNodeTransitions = 
            context.getReference(
                StateNodeMetaData.StateNodeDescriptor.TRANSITIONS,
                stateTransition);
        
        final Long stateNodeTransitionsId;
        if (stateNodeTransitions != null)
        {
            stateNodeTransitionsId = stateNodeTransitions.getId();
        }
        else
        {
            stateNodeTransitionsId = null;
        }

 
        if (stateTransition.getId() == null)
        {
        
            final Long id = StateTransitionDao.insertStateTransition(context.getTransaction(), 
                stateTransition, 
                stateNodeTransitionsId
             );
            stateTransition.setId(id);
        }
        else
        {
            StateTransitionDao.updateStateTransition(context.getTransaction(), stateTransition, 
                stateNodeTransitionsId
             );
        }

        context.getObjectCache().getStateTransitionMap().put(stateTransition.getId(), stateTransition);

 

        return stateTransition.getId();
    }


    /**
     * persist the state transition to the database using the given transaction.
     * 
     * TODO
     * This method should NOT be used directly, however. Individual 
     * object actions should be used. 
     * 
     * @param transaction
     *            the Transaction used to persist the state transition
     * @param stateTransition
     *            the state transition to persist
     *
    public static void persistStateTransition(final IDbTransaction transaction, final StateTransition stateTransition,  final Long stateNodeTransitionsId  )
    {
         final Long stateTransitionId = StateTransitionDao.insertStateTransition(transaction, stateTransition);
        stateTransition.setId(stateTransitionId);
     }
*/
}
