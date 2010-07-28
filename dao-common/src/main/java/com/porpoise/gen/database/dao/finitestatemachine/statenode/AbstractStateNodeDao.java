/**
 * AbstractStateNodeDao.java

 */
package com.porpoise.gen.database.dao.finitestatemachine.statenode;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

import com.porpoise.gen.database.DbException;
import com.porpoise.gen.database.IDbTransaction;
import com.porpoise.gen.database.dao.AbstractDao;
import com.porpoise.gen.database.Null;
import com.porpoise.gen.database.visitors.AbstractResultSetVisitor;
import com.porpoise.gen.database.visitors.FirstResultToMapVisitor;
import com.porpoise.gen.database.visitors.MultipleResultToMapVisitor;
import com.porpoise.gen.database.visitors.Visitors;

import com.porpoise.gen.finitestatemachine.statenode.StateNode;
import com.porpoise.gen.finitestatemachine.statenode.StateNodeBuilder;

import com.porpoise.gen.database.dao.finitestatemachine.statetransition.StateTransitionDao;
import com.porpoise.gen.finitestatemachine.statetransition.StateTransitionBuilder;
import com.porpoise.gen.finitestatemachine.statetransition.StateTransitionBuilder;
import com.porpoise.gen.finitestatemachine.statetransition.IStateTransitionDetails;
import com.porpoise.gen.database.visitors.SingleScalarResultVisitor;
import com.porpoise.gen.database.visitors.Visitors;


/**
 * AbstractStateNodeDao
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 *
 */
abstract class AbstractStateNodeDao extends AbstractDao
{
    /**
     * A AbstractStateNodeDao instance may hold state so as to support deferred calls.
     * For instance, some object may initialize a new AbstractStateNodeDao instance to
     * hand back to the caller which will at some point call {@link #insert(IDbTransaction)}, 
     * {@link #update(IDbTransaction)} or {@link #delete(IDbTransaction)}.
     */
    private StateNode stateNode;
    private final Long stateNodeId;
    private Long stateMachineNodesId;
 

    protected final static Map<String, String> PROPERTY_TO_COLUMN_NAME_MAP;

    static
    {
        final Map<String, String> temp = new HashMap<String, String>();
        // property name is the same as NAME
        // property transitions is the same as TRANSITIONS
        PROPERTY_TO_COLUMN_NAME_MAP = Collections.unmodifiableMap(temp);
    }


    /**
     * default constructor. This implementation won't hold any state.
     */
    public AbstractStateNodeDao()
    {
        this((Long) null);
    }

    /**
     * @param stateNodeValue     
     */
    public AbstractStateNodeDao(final StateNode stateNodeValue, 
        Long stateMachineNodesIdValue
     )
    {
        this(stateNodeValue == null ? null : stateNodeValue.getId());
        this.stateNode = stateNodeValue;
        stateMachineNodesId = stateMachineNodesIdValue;
     }

    /**
     * @param stateNodeIdValue the id of the state node
     */
    public AbstractStateNodeDao(final Long stateNodeIdValue)
    {
        this.stateNodeId = stateNodeIdValue;
    }

 
    /**
     * insert method
     * 
     * @param transaction
     *            the database transaction to use for the insert
     * @return the new ID for the inserted state node
     * @throws DbException
     */
    public Long insert(final IDbTransaction transaction) throws DbException
    {
        final Long id = insertStateNode(transaction, this.stateNode,   this.stateMachineNodesId );
        return id;
    }

    /**
     * delete method
     * 
     * @param transaction
     *            the database transaction to use for the delete
     * @return the deleted state node
     * @throws DbException
     */
    public StateNode delete(final IDbTransaction transaction) throws DbException
    {
        final StateNodeBuilder deletedStateNode = deleteStateNode(transaction, this.stateNodeId, false);
        return deletedStateNode.build();
    }

    /**
     * update method
     * 
     * @param transaction
     *            the database transaction to use for the delete
     * @return the updated state node
     * @throws DbException
     */
    public StateNode update(final IDbTransaction transaction) throws DbException
    {
        updateStateNode(transaction, this.stateNode,   this.stateMachineNodesId );
        return this.stateNode;
    }

    /**
     * get state node for the given ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the state node to load
     * @return the StateNode object for the ID
     */
    protected StateNode getStateNode(final IDbTransaction transaction, final Long id)
    {
        return getStateNodeBuilder(transaction, id).build();
    }
    

    void initStateNodeBuilderLazy(
        final IDbTransaction transaction, 
        final StateNodeBuilder stateNodeBuilder, 
        final Long id)
    {
        
            //
            // Transitions List
            //
            transaction.executeQuery(
                new AbstractResultSetVisitor() {
                    @Override
                    public boolean onResultSet(final ResultSet resultSet) throws SQLException
                    {
                        final Long transitionsId = getLong(resultSet, 1);
                        stateNodeBuilder.addTransitionsId(transitionsId);
                        return true;
                    }
                },
                "SELECT ID FROM abp_STATE_TRANSITION " +
 
                    " WHERE STATE_NODE_TRANSITIONS_ID = ?", id);
           }

    void initStateNodeBuilderEager(
        final IDbTransaction transaction, 
        final StateNodeBuilder stateNodeBuilder, 
        final Long id)
    {
        
            //
            // Transitions List
            //
            final List<StateTransition> transitionsList;
            transitionsList = StateTransitionDao.findStateTransition(
                transaction, 
                " WHERE STATE_NODE_ID = ?", id);
        stateNodeBuilder.setTransitions(transitionsList);
     }
     /**
     * get state node for the given ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the state node to load
     * @return a StateNodeBuilder for the ID
     */
    protected StateNodeBuilder getStateNodeBuilder(final IDbTransaction transaction, final Long id)
    {
        final boolean eager = false;
        return getStateNodeBuilder(transaction, id, eager);
     }
    /**
     * get state node for the given ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the state node to load
     * @param eager 
     * @return a StateNodeBuilder for the ID
     */
    protected StateNodeBuilder getStateNodeBuilder(final IDbTransaction transaction, final Long id, final boolean eager)
    {
        final String sql = StateNodeResultSetVisitor.GET_SQL + " WHERE ID=?";
        final StateNodeResultSetVisitor visitor = transaction.executeQuery(new StateNodeResultSetVisitor(), sql, id);
        
        final StateNodeBuilder stateNodeBuilder = visitor.getSingleStateNodeBuilder();
        if (stateNodeBuilder != null)
        {
            stateNodeBuilder.id(id);
            if (eager)
            {
                initStateNodeBuilderEager(transaction, stateNodeBuilder, id);
            }
            else // lazy
            {
                initStateNodeBuilderLazy(transaction, stateNodeBuilder, id);
            }
        }
        return stateNodeBuilder;
    }
 
    /**
     * @return the insert SQL 
     */
    protected String getInsertSql()
    {

        final StringBuilder query = new StringBuilder("INSERT INTO abp_STATE_NODE (");
        //query.append("ID, ");
        
                                //
        // primitive fields
        //
                query.append("NAME,  "); // string -> String
        
                        
                                //
        // referenced by fields
        //
                query.append("STATE_MACHINE_NODES_ID ");
        
        query.append(") VALUES (");
        
                                        query.append("?, "); // NAME
        
                                
                                        query.append("? ");// FK NODES_ID
        
        query.append(")");


        return query.toString();
    }

    /**
     * create the new state node
     * @param transaction the transaction used to create the entity
     * @param stateNodeValue the State Node to create
     * @param stateMachineNodesIdValue the referencing stateMachine ID 
      */
    protected Long insertStateNode(
        final IDbTransaction transaction,
        final StateNode stateNodeValue, 
        final Long stateMachineNodesIdValue
     )
    {
        if (stateNodeValue.getId() != null)
        {
            throw new IllegalArgumentException("Can't insert a new State Node with non-null ID " + stateNodeValue.getId());
        }
        
        final Long id = insertStateNodeSafe( transaction,
            stateNodeValue, 
            stateMachineNodesIdValue
         );
        return id;
    }

    /**
     * create the new state node
     * @param transaction the transaction used to create the entity
     * @param stateNodeValue the State Node to create
     * @param stateMachineNodesIdValue the referencing stateMachine ID 
      */
    protected abstract Long insertStateNodeSafe(
        final IDbTransaction transaction,
        final StateNode stateNodeValue, 
        final Long stateMachineNodesIdValue
     );
 
 
//TODO - Transitions one to many lists
 
    /**
     * @param transaction
     * @param stateNode
                     * @param nodesId
             */
    protected void updateStateNode(final IDbTransaction transaction, final StateNode stateNode, 
                final Long stateMachineNodesId
            )
    {
        if (stateNode.getId() == null)
        {
            throw new IllegalArgumentException(String.format("Can't update stateNode %d with a null ID", stateNode));
        }
        final StringBuilder query = new StringBuilder("UPDATE abp_STATE_NODE SET ");
        
                                //
        // primitive fields
        //
                query.append("NAME=?, ");
        
                        
                                //
        // referenced by fields
        //
                query.append("STATE_MACHINE_NODES_ID=?");
            
        query.append(" WHERE ID=?");
            
        final String updateSql = query.toString();
        transaction.executeUpdate(updateSql, // 
                                        nullable(stateNode.getName()), //
                                                                nullable(stateMachineNodesId)
,
        stateNode.getId()
        );

    }

    /**
     * @param transaction the transaction to use to delete the entity
     * @param id The id of the State Node to delete
     * @param cascade true if the delete should cascade to referenced types
     * @return a builder for the deleted state node 
     */
    protected StateNodeBuilder deleteStateNode(final IDbTransaction transaction, final Long id, final boolean cascade)
    {
        final StateNodeBuilder stateNodeBuilder = getStateNodeBuilder(transaction, id);
        final StateNode stateNode = stateNodeBuilder.build();
        
        if (stateNode != null)
        {
            if (cascade)
            {
// list field count: 1
                //
                //delete references to list objects from state node
                //
                deleteTransitionsReferencedFromStateNode(transaction, id);
                 //
                //delete references to this state node
                //
                transaction.executeUpdate("DELETE FROM abp_STATE_MACHINE WHERE STATE_NODE_ID=?", id);
             }
             transaction.executeUpdate("DELETE FROM abp_STATE_NODE WHERE ID=?", id);
        }
        
        return stateNodeBuilder;
    }

     /**
      * @param transaction
      * @param stateNodeId 
      */
     protected void deleteTransitionsReferencedFromStateNode(
         final IDbTransaction transaction, 
         final Long stateNodeId)
     {
         transaction.executeUpdate("DELETE FROM abp_STATE_TRANSITION WHERE TRANSITIONS_ID=?", stateNodeId);
     }

    /**
     * find state node based on the query
     * @param transaction the transaction used to find the state node objects 
     * @param query the query suffix to use
     * @param parameters the parameters to use by the query. The values will be used to supply values for the ? place-holders 
     * @return a collection of StateNode objects for the query
     */
    protected List<StateNode> findStateNode(final IDbTransaction transaction, final String whereClause, Object ... params)
    {
        final String sql = StateNodeResultSetVisitor.GET_SQL
                + prepareWhereClause(whereClause);
        
        final StateNodeResultSetVisitor visitor = transaction.executeQuery(new StateNodeResultSetVisitor(), sql, params);

        return visitor.getStateNodes();
    }

    /**
     * convenience method for loading a collection of ${form.Name.toLowreCase()} objects for
     * the collection of IDs 
     * @return a collection of all ${form.Name.toLowreCase()} by the given IDs
     */
    protected Collection<StateNode> findStateNodes(final IDbTransaction dbTransaction,
            final Collection<Long> stateNodeIdToLoadList)
    {
        final StringBuilder whereClause = new StringBuilder("WHERE ID IN (");
        for (int i = stateNodeIdToLoadList.size(); --i >= 0;)
        {
            whereClause.append("?,");
        }
        whereClause.delete(whereClause.length() - 1, whereClause.length());
        whereClause.append(")");
        return findStateNode(dbTransaction, whereClause.toString(), stateNodeIdToLoadList.toArray());
    }

    /**
     * find state node builders based on the query
     * @param transaction the transaction used to find the state node builder objects 
     * @param query the query suffix to use
     * @param parameters the parameters to use by the query. The values will be used to supply values for the ? place-holders 
     * @return a collection of StateNode builders objects for the query
     */
    protected List<StateNodeBuilder> findStateNodeBuilders(final IDbTransaction transaction, final String whereClause, Object ... params)
    {
        final String sql = StateNodeResultSetVisitor.GET_SQL
                + prepareWhereClause(whereClause);
        final StateNodeResultSetVisitor visitor = transaction.executeQuery(new StateNodeResultSetVisitor(), sql, params);
        return visitor.getBuilders();
    }

    /**
     * find state node referenced by the state machine's nodes ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the ${reference.ReferencingField.toLowerCase()} to load
     * @return a collection of StateNode objects
     */
    protected Collection<StateNode> findStateNodeForStateMachineNodes(
        final IDbTransaction transaction, final Long stateMachineNodesId)
    {
        //return findStateNode(transaction, " JOIN abp_STATE_MACHINE ON abp_STATE_MACHINE.NODES_ID = abp_STATE_NODE.ID WHERE abp_STATE_MACHINE.ID=?", stateMachineNodesId);
        return findStateNode(transaction, " WHERE STATE_MACHINE_NODES_ID=?", stateMachineNodesId);
    }

    /**
     * find state node builders referenced by the state machine's nodes ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the ${reference.ReferencingField.toLowerCase()} to load
     * @return a collection of StateNode objects
     */
    protected Collection<StateNodeBuilder> findStateNodeBuildersForStateMachineNodes(
        final IDbTransaction transaction, final Long StateMachinenodesId)
    {
        return findStateNodeBuilders(transaction, " WHERE STATE_MACHINE_NODES_ID=?", StateMachinenodesId);
    }
 
    /**
     * @param transaction
     * @return the visitor
     */
    public MultipleResultToMapVisitor listAll(final IDbTransaction transaction)
    {
        return transaction.executeQuery(Visitors.multiResultVisitor(), getSelectAllSql());
    }
    
    /**
     * @return the query to select all fields in the 
     */
    public String getSelectAllSql()
    {
        final StringBuilder query = new StringBuilder("SELECT ");
         query.append("ID, ");

         query.append("NAME, ");
        //
        // foreign keys
        //
        query.append("NODES_ID ");
        query.append(" FROM abp_STATE_NODE ");
        final String sql = query.toString();
        return sql;
    }

    @Override
    protected String prepareWhereClause(final String whereClause)
    {
        String preparedWhereClause = super.prepareWhereClause(whereClause)
                .toUpperCase();
        for (final Entry<String, String> entry : PROPERTY_TO_COLUMN_NAME_MAP.entrySet())
        {
            final String propertyName = entry.getKey();
            final String columnName = entry.getValue();

            preparedWhereClause = preparedWhereClause.replaceAll(propertyName,
                    columnName);
        }
        return preparedWhereClause;
    }

}
