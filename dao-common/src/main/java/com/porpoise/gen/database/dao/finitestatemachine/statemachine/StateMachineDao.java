/**
 * StateMachineDao.java

 */
package com.porpoise.gen.database.dao.finitestatemachine.statemachine;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.porpoise.gen.database.IDbTransaction;
import com.porpoise.gen.database.init.Databases;

import com.porpoise.gen.finitestatemachine.statemachine.StateMachine;
import com.porpoise.gen.finitestatemachine.statemachine.StateMachineBuilder;

import com.porpoise.gen.database.dao.finitestatemachine.statenode.StateNodeDao;
import com.porpoise.gen.finitestatemachine.statenode.StateNodeBuilder;
import com.porpoise.gen.finitestatemachine.statenode.IStateNodeDetails;

 
import com.porpoise.gen.logging.AaronLog;


/**
 * StateMachineDao
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class StateMachineDao
{
    private static final AaronLog          LOG = AaronLog.getLog(StateMachineDao.class);
    
    private static AbstractStateMachineDao daoImpl;

    /*
     * StateMachineDao will delegate all calls to an AbstractStateMachineDao instance,
     * which will be specific to the configured database vendor;
     */
    private synchronized static AbstractStateMachineDao getDao()
    {
        if (daoImpl == null)
        {
            daoImpl = newStateMachineDao(Databases.getVendor(), null);
        }
        return daoImpl;
    }

    /*
     * create a new AbstractStateMachineDao for the given configuration
     */
    private static AbstractStateMachineDao newStateMachineDao(
        final Databases databaseVendor, 
        final StateMachine stateMachine   )
    {
        LOG.info("Configuring StateMachineDao for " + databaseVendor);
        
        final AbstractStateMachineDao dao;
        if (Databases.DERBY.equals(databaseVendor))
        {
            dao = new StateMachineDerbyDao(stateMachine  );
        }
        else if (Databases.ORACLE.equals(databaseVendor))
        {
            dao = new StateMachineOracleDao(stateMachine  );
        }
        else if (Databases.MYSQL.equals(databaseVendor))
        {
            dao = new StateMachineMySqlDao(stateMachine  );
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
    private StateMachineDao()
    {
        super();
    }

    /**
     * get state machine for the given ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the state machine to load
     * @return the StateMachine object for the ID
     */
    public static StateMachine getStateMachine(final IDbTransaction transaction, final Long id)
    {
        return getDao().getStateMachineBuilder(transaction, id).build();
    }
    
    /**
     * get state machine for the given ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the state machine to load
     * @return a StateMachineBuilder for the ID
     */
    public static StateMachineBuilder getStateMachineBuilder(final IDbTransaction transaction, final Long id)
    {
        return getDao().getStateMachineBuilder(transaction, id);
    }

    /**
     * create the new state machine
     * @param transaction the transaction used to create the entity
     * @param stateMachine the State Machine to create
     */
    public static Long insertStateMachine(
        final IDbTransaction transaction,
        final StateMachine stateMachine
     )
    {
        return getDao().insertStateMachine(
            transaction,
            stateMachine
         );        
    }

 
//TODO - Nodes one to many lists

    /**
     * @param transaction
     * @param stateMachine
     */
    public static void updateStateMachine(final IDbTransaction transaction, final StateMachine stateMachine
            )
    {
        getDao().updateStateMachine(
            transaction, 
            stateMachine
        );
    }

    /**
     * @param transaction the transaction to use to delete the entity
     * @param id The id of the State Machine to delete
     * @param cascade true if the delete should cascade to referenced types
     * @return a builder for the deleted state machine 
     */
    public static StateMachineBuilder deleteStateMachine(final IDbTransaction transaction, final Long id, final boolean cascade)
    {
        return getDao().deleteStateMachine(transaction, id, cascade);
    }


     /**
      * @param transaction
      * @param stateMachineId 
      */
     public static void deleteNodesReferencedFromStateMachine(
         final IDbTransaction transaction, 
         final Long stateMachineId)
     {
         getDao().deleteNodesReferencedFromStateMachine(
             transaction, 
             stateMachineId);
     }

    /**
     * find state machine based on the query
     * @param transaction the transaction used to find the state machine objects 
     * @param query the query suffix to use
     * @param parameters the parameters to use by the query. The values will be used to supply values for the ? place-holders 
     * @return a collection of StateMachine objects for the query
     */
    public static List<StateMachine> findStateMachine(final IDbTransaction transaction, final String whereClause, Object ... params)
    {
        return getDao().findStateMachine(transaction, whereClause, params);
    }

    /**
     * convenience method for loading a collection of ${form.Name.toLowreCase()} objects for
     * the collection of IDs 
     * @return a collection of all ${form.Name.toLowreCase()} by the given IDs
     */
    public static Collection<StateMachine> findStateMachines(final IDbTransaction dbTransaction,
            final Collection<Long> stateMachineIdToLoadList)
    {
        return getDao().findStateMachines(dbTransaction, stateMachineIdToLoadList);
    }

    /**
     * find state machine builders based on the query
     * @param transaction the transaction used to find the state machine builder objects 
     * @param query the query suffix to use
     * @param parameters the parameters to use by the query. The values will be used to supply values for the ? place-holders 
     * @return a collection of StateMachine builders objects for the query
     */
    public static List<StateMachineBuilder> findStateMachineBuilders(final IDbTransaction transaction, final String whereClause, final Object ... params)
    {
        return getDao().findStateMachineBuilders(transaction, whereClause, params);
    }

 
}
