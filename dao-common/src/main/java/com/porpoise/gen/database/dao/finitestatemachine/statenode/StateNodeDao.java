/**
 * StateNodeDao.java

 */
package com.porpoise.gen.database.dao.finitestatemachine.statenode;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.porpoise.gen.database.IDbTransaction;
import com.porpoise.gen.database.init.Databases;

import com.porpoise.gen.finitestatemachine.statenode.StateNode;
import com.porpoise.gen.finitestatemachine.statenode.StateNodeBuilder;

import com.porpoise.gen.database.dao.finitestatemachine.statetransition.StateTransitionDao;
import com.porpoise.gen.finitestatemachine.statetransition.StateTransitionBuilder;
import com.porpoise.gen.finitestatemachine.statetransition.IStateTransitionDetails;

 
import com.porpoise.gen.logging.AaronLog;


/**
 * StateNodeDao
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class StateNodeDao
{
    private static final AaronLog          LOG = AaronLog.getLog(StateNodeDao.class);
    
    private static AbstractStateNodeDao daoImpl;

    /*
     * StateNodeDao will delegate all calls to an AbstractStateNodeDao instance,
     * which will be specific to the configured database vendor;
     */
    private synchronized static AbstractStateNodeDao getDao()
    {
        if (daoImpl == null)
        {
            daoImpl = newStateNodeDao(Databases.getVendor(), null, null);
        }
        return daoImpl;
    }

    /*
     * create a new AbstractStateNodeDao for the given configuration
     */
    private static AbstractStateNodeDao newStateNodeDao(
        final Databases databaseVendor, 
        final StateNode stateNode ,   final Long stateMachineNodesIdValue  )
    {
        LOG.info("Configuring StateNodeDao for " + databaseVendor);
        
        final AbstractStateNodeDao dao;
        if (Databases.DERBY.equals(databaseVendor))
        {
            dao = new StateNodeDerbyDao(stateNode ,   stateMachineNodesIdValue );
        }
        else if (Databases.ORACLE.equals(databaseVendor))
        {
            dao = new StateNodeOracleDao(stateNode ,   stateMachineNodesIdValue );
        }
        else if (Databases.MYSQL.equals(databaseVendor))
        {
            dao = new StateNodeMySqlDao(stateNode ,   stateMachineNodesIdValue );
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
    private StateNodeDao()
    {
        super();
    }

    /**
     * get state node for the given ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the state node to load
     * @return the StateNode object for the ID
     */
    public static StateNode getStateNode(final IDbTransaction transaction, final Long id)
    {
        return getDao().getStateNodeBuilder(transaction, id).build();
    }
    
    /**
     * get state node for the given ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the state node to load
     * @return a StateNodeBuilder for the ID
     */
    public static StateNodeBuilder getStateNodeBuilder(final IDbTransaction transaction, final Long id)
    {
        return getDao().getStateNodeBuilder(transaction, id);
    }

    /**
     * create the new state node
     * @param transaction the transaction used to create the entity
     * @param stateNode the State Node to create
     */
    public static Long insertStateNode(
        final IDbTransaction transaction,
        final StateNode stateNode, 
        Long stateMachineNodesId
     )
    {
        return getDao().insertStateNode(
            transaction,
            stateNode, 
            stateMachineNodesId
         );        
    }

 
//TODO - Transitions one to many lists

    /**
     * @param transaction
     * @param stateNode
     * @param nodesId
     */
    public static void updateStateNode(final IDbTransaction transaction, final StateNode stateNode, 
                final Long stateMachineNodesId
            )
    {
        getDao().updateStateNode(
            transaction, 
            stateNode, 
            stateMachineNodesId
        );
    }

    /**
     * @param transaction the transaction to use to delete the entity
     * @param id The id of the State Node to delete
     * @param cascade true if the delete should cascade to referenced types
     * @return a builder for the deleted state node 
     */
    public static StateNodeBuilder deleteStateNode(final IDbTransaction transaction, final Long id, final boolean cascade)
    {
        return getDao().deleteStateNode(transaction, id, cascade);
    }


     /**
      * @param transaction
      * @param stateNodeId 
      */
     public static void deleteTransitionsReferencedFromStateNode(
         final IDbTransaction transaction, 
         final Long stateNodeId)
     {
         getDao().deleteTransitionsReferencedFromStateNode(
             transaction, 
             stateNodeId);
     }

    /**
     * find state node based on the query
     * @param transaction the transaction used to find the state node objects 
     * @param query the query suffix to use
     * @param parameters the parameters to use by the query. The values will be used to supply values for the ? place-holders 
     * @return a collection of StateNode objects for the query
     */
    public static List<StateNode> findStateNode(final IDbTransaction transaction, final String whereClause, Object ... params)
    {
        return getDao().findStateNode(transaction, whereClause, params);
    }

    /**
     * convenience method for loading a collection of ${form.Name.toLowreCase()} objects for
     * the collection of IDs 
     * @return a collection of all ${form.Name.toLowreCase()} by the given IDs
     */
    public static Collection<StateNode> findStateNodes(final IDbTransaction dbTransaction,
            final Collection<Long> stateNodeIdToLoadList)
    {
        return getDao().findStateNodes(dbTransaction, stateNodeIdToLoadList);
    }

    /**
     * find state node builders based on the query
     * @param transaction the transaction used to find the state node builder objects 
     * @param query the query suffix to use
     * @param parameters the parameters to use by the query. The values will be used to supply values for the ? place-holders 
     * @return a collection of StateNode builders objects for the query
     */
    public static List<StateNodeBuilder> findStateNodeBuilders(final IDbTransaction transaction, final String whereClause, final Object ... params)
    {
        return getDao().findStateNodeBuilders(transaction, whereClause, params);
    }

    /**
     * find state node referenced by the state machine's nodes ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the ${reference.ReferencingField.toLowerCase()} to load
     * @return a collection of StateNode objects
     */
    public static Collection<StateNode> findStateNodeForStateMachineNodes(
        final IDbTransaction transaction, final Long stateMachineNodesId)
    {
        return getDao().findStateNodeForStateMachineNodes(
            transaction, stateMachineNodesId);
    }

    /**
     * find state node builders referenced by the state machine's nodes ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the ${reference.ReferencingField.toLowerCase()} to load
     * @return a collection of StateNode objects
     */
    public static Collection<StateNodeBuilder> findStateNodeBuildersForStateMachineNodes(
        final IDbTransaction transaction, final Long stateMachineNodesId)
    {
        return getDao().findStateNodeBuildersForStateMachineNodes(
            transaction, stateMachineNodesId);
    }
 
}
