/**
 * StateTransitionDao.java

 */
package com.porpoise.gen.database.dao.finitestatemachine.statetransition;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.porpoise.gen.database.IDbTransaction;
import com.porpoise.gen.database.init.Databases;

import com.porpoise.gen.finitestatemachine.statetransition.StateTransition;
import com.porpoise.gen.finitestatemachine.statetransition.StateTransitionBuilder;


 
import com.porpoise.gen.logging.AaronLog;


/**
 * StateTransitionDao
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class StateTransitionDao
{
    private static final AaronLog          LOG = AaronLog.getLog(StateTransitionDao.class);
    
    private static AbstractStateTransitionDao daoImpl;

    /*
     * StateTransitionDao will delegate all calls to an AbstractStateTransitionDao instance,
     * which will be specific to the configured database vendor;
     */
    private synchronized static AbstractStateTransitionDao getDao()
    {
        if (daoImpl == null)
        {
            daoImpl = newStateTransitionDao(Databases.getVendor(), null, null);
        }
        return daoImpl;
    }

    /*
     * create a new AbstractStateTransitionDao for the given configuration
     */
    private static AbstractStateTransitionDao newStateTransitionDao(
        final Databases databaseVendor, 
        final StateTransition stateTransition ,   final Long stateNodeTransitionsIdValue  )
    {
        LOG.info("Configuring StateTransitionDao for " + databaseVendor);
        
        final AbstractStateTransitionDao dao;
        if (Databases.DERBY.equals(databaseVendor))
        {
            dao = new StateTransitionDerbyDao(stateTransition ,   stateNodeTransitionsIdValue );
        }
        else if (Databases.ORACLE.equals(databaseVendor))
        {
            dao = new StateTransitionOracleDao(stateTransition ,   stateNodeTransitionsIdValue );
        }
        else if (Databases.MYSQL.equals(databaseVendor))
        {
            dao = new StateTransitionMySqlDao(stateTransition ,   stateNodeTransitionsIdValue );
        }
        else
        {
            throw new RuntimeException("No suitable DAO could be found for database type " + databaseVendor);
        }
        
        return dao;
    }

    //
    // uninstantiable
    //
    private StateTransitionDao()
    {
        super();
    }

    /**
     * get state transition for the given ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the state transition to load
     * @return the StateTransition object for the ID
     */
    public static StateTransition getStateTransition(final IDbTransaction transaction, final Long id)
    {
        return getDao().getStateTransitionBuilder(transaction, id).build();
    }
    
    /**
     * get state transition for the given ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the state transition to load
     * @return a StateTransitionBuilder for the ID
     */
    public static StateTransitionBuilder getStateTransitionBuilder(final IDbTransaction transaction, final Long id)
    {
        return getDao().getStateTransitionBuilder(transaction, id);
    }

    /**
     * create the new state transition
     * @param transaction the transaction used to create the entity
     * @param stateTransition the State Transition to create
     */
    public static Long insertStateTransition(
        final IDbTransaction transaction,
        final StateTransition stateTransition, 
        Long stateNodeTransitionsId
     )
    {
        return getDao().insertStateTransition(
            transaction,
            stateTransition, 
            stateNodeTransitionsId
         );        
    }


    /**
     * @param transaction
     * @param stateTransition
     * @param transitionsId
     */
    public static void updateStateTransition(final IDbTransaction transaction, final StateTransition stateTransition, 
                final Long stateNodeTransitionsId
            )
    {
        getDao().updateStateTransition(
            transaction, 
            stateTransition, 
            stateNodeTransitionsId
        );
    }

    /**
     * @param transaction the transaction to use to delete the entity
     * @param id The id of the State Transition to delete
     * @param cascade true if the delete should cascade to referenced types
     * @return a builder for the deleted state transition 
     */
    public static StateTransitionBuilder deleteStateTransition(final IDbTransaction transaction, final Long id, final boolean cascade)
    {
        return getDao().deleteStateTransition(transaction, id, cascade);
    }


    /**
     * find state transition based on the query
     * @param transaction the transaction used to find the state transition objects 
     * @param query the query suffix to use
     * @param parameters the parameters to use by the query. The values will be used to supply values for the ? place-holders 
     * @return a collection of StateTransition objects for the query
     */
    public static List<StateTransition> findStateTransition(final IDbTransaction transaction, final String whereClause, Object ... params)
    {
        return getDao().findStateTransition(transaction, whereClause, params);
    }

    /**
     * convenience method for loading a collection of ${form.Name.toLowreCase()} objects for
     * the collection of IDs 
     * @return a collection of all ${form.Name.toLowreCase()} by the given IDs
     */
    public static Collection<StateTransition> findStateTransitions(final IDbTransaction dbTransaction,
            final Collection<Long> stateTransitionIdToLoadList)
    {
        return getDao().findStateTransitions(dbTransaction, stateTransitionIdToLoadList);
    }

    /**
     * find state transition builders based on the query
     * @param transaction the transaction used to find the state transition builder objects 
     * @param query the query suffix to use
     * @param parameters the parameters to use by the query. The values will be used to supply values for the ? place-holders 
     * @return a collection of StateTransition builders objects for the query
     */
    public static List<StateTransitionBuilder> findStateTransitionBuilders(final IDbTransaction transaction, final String whereClause, final Object ... params)
    {
        return getDao().findStateTransitionBuilders(transaction, whereClause, params);
    }

    /**
     * find state transition referenced by the state node's transitions ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the ${reference.ReferencingField.toLowerCase()} to load
     * @return a collection of StateTransition objects
     */
    public static Collection<StateTransition> findStateTransitionForStateNodeTransitions(
        final IDbTransaction transaction, final Long stateNodeTransitionsId)
    {
        return getDao().findStateTransitionForStateNodeTransitions(
            transaction, stateNodeTransitionsId);
    }

    /**
     * find state transition builders referenced by the state node's transitions ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the ${reference.ReferencingField.toLowerCase()} to load
     * @return a collection of StateTransition objects
     */
    public static Collection<StateTransitionBuilder> findStateTransitionBuildersForStateNodeTransitions(
        final IDbTransaction transaction, final Long stateNodeTransitionsId)
    {
        return getDao().findStateTransitionBuildersForStateNodeTransitions(
            transaction, stateNodeTransitionsId);
    }
 
}
