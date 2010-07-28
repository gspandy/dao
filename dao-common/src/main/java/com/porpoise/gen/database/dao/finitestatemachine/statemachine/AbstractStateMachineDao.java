/**
 * AbstractStateMachineDao.java

 */
package com.porpoise.gen.database.dao.finitestatemachine.statemachine;

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

import com.porpoise.gen.finitestatemachine.statemachine.StateMachine;
import com.porpoise.gen.finitestatemachine.statemachine.StateMachineBuilder;

import com.porpoise.gen.database.dao.finitestatemachine.statenode.StateNodeDao;
import com.porpoise.gen.finitestatemachine.statenode.StateNodeBuilder;
import com.porpoise.gen.finitestatemachine.statenode.StateNodeBuilder;
import com.porpoise.gen.finitestatemachine.statenode.IStateNodeDetails;
import com.porpoise.gen.database.visitors.SingleScalarResultVisitor;
import com.porpoise.gen.database.visitors.Visitors;


/**
 * AbstractStateMachineDao
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 *
 */
abstract class AbstractStateMachineDao extends AbstractDao
{
    /**
     * A AbstractStateMachineDao instance may hold state so as to support deferred calls.
     * For instance, some object may initialize a new AbstractStateMachineDao instance to
     * hand back to the caller which will at some point call {@link #insert(IDbTransaction)}, 
     * {@link #update(IDbTransaction)} or {@link #delete(IDbTransaction)}.
     */
    private StateMachine stateMachine;
    private final Long stateMachineId;
 

    protected final static Map<String, String> PROPERTY_TO_COLUMN_NAME_MAP;

    static
    {
        final Map<String, String> temp = new HashMap<String, String>();
        // property name is the same as NAME
        // property nodes is the same as NODES
        PROPERTY_TO_COLUMN_NAME_MAP = Collections.unmodifiableMap(temp);
    }


    /**
     * default constructor. This implementation won't hold any state.
     */
    public AbstractStateMachineDao()
    {
        this((Long) null);
    }

    /**
     * @param stateMachineValue     
     */
    public AbstractStateMachineDao(final StateMachine stateMachineValue
     )
    {
        this(stateMachineValue == null ? null : stateMachineValue.getId());
        this.stateMachine = stateMachineValue;
     }

    /**
     * @param stateMachineIdValue the id of the state machine
     */
    public AbstractStateMachineDao(final Long stateMachineIdValue)
    {
        this.stateMachineId = stateMachineIdValue;
    }

 
    /**
     * insert method
     * 
     * @param transaction
     *            the database transaction to use for the insert
     * @return the new ID for the inserted state machine
     * @throws DbException
     */
    public Long insert(final IDbTransaction transaction) throws DbException
    {
        final Long id = insertStateMachine(transaction, this.stateMachine );
        return id;
    }

    /**
     * delete method
     * 
     * @param transaction
     *            the database transaction to use for the delete
     * @return the deleted state machine
     * @throws DbException
     */
    public StateMachine delete(final IDbTransaction transaction) throws DbException
    {
        final StateMachineBuilder deletedStateMachine = deleteStateMachine(transaction, this.stateMachineId, false);
        return deletedStateMachine.build();
    }

    /**
     * update method
     * 
     * @param transaction
     *            the database transaction to use for the delete
     * @return the updated state machine
     * @throws DbException
     */
    public StateMachine update(final IDbTransaction transaction) throws DbException
    {
        updateStateMachine(transaction, this.stateMachine );
        return this.stateMachine;
    }

    /**
     * get state machine for the given ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the state machine to load
     * @return the StateMachine object for the ID
     */
    protected StateMachine getStateMachine(final IDbTransaction transaction, final Long id)
    {
        return getStateMachineBuilder(transaction, id).build();
    }
    

    void initStateMachineBuilderLazy(
        final IDbTransaction transaction, 
        final StateMachineBuilder stateMachineBuilder, 
        final Long id)
    {
        
            //
            // Nodes List
            //
            transaction.executeQuery(
                new AbstractResultSetVisitor() {
                    @Override
                    public boolean onResultSet(final ResultSet resultSet) throws SQLException
                    {
                        final Long nodesId = getLong(resultSet, 1);
                        stateMachineBuilder.addNodesId(nodesId);
                        return true;
                    }
                },
                "SELECT ID FROM abp_STATE_NODE " +
 
                    " WHERE STATE_MACHINE_NODES_ID = ?", id);
           }

    void initStateMachineBuilderEager(
        final IDbTransaction transaction, 
        final StateMachineBuilder stateMachineBuilder, 
        final Long id)
    {
        
            //
            // Nodes List
            //
            final List<StateNode> nodesList;
            nodesList = StateNodeDao.findStateNode(
                transaction, 
                " WHERE STATE_MACHINE_ID = ?", id);
        stateMachineBuilder.setNodes(nodesList);
     }
     /**
     * get state machine for the given ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the state machine to load
     * @return a StateMachineBuilder for the ID
     */
    protected StateMachineBuilder getStateMachineBuilder(final IDbTransaction transaction, final Long id)
    {
        final boolean eager = false;
        return getStateMachineBuilder(transaction, id, eager);
     }
    /**
     * get state machine for the given ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the state machine to load
     * @param eager 
     * @return a StateMachineBuilder for the ID
     */
    protected StateMachineBuilder getStateMachineBuilder(final IDbTransaction transaction, final Long id, final boolean eager)
    {
        final String sql = StateMachineResultSetVisitor.GET_SQL + " WHERE ID=?";
        final StateMachineResultSetVisitor visitor = transaction.executeQuery(new StateMachineResultSetVisitor(), sql, id);
        
        final StateMachineBuilder stateMachineBuilder = visitor.getSingleStateMachineBuilder();
        if (stateMachineBuilder != null)
        {
            stateMachineBuilder.id(id);
            if (eager)
            {
                initStateMachineBuilderEager(transaction, stateMachineBuilder, id);
            }
            else // lazy
            {
                initStateMachineBuilderLazy(transaction, stateMachineBuilder, id);
            }
        }
        return stateMachineBuilder;
    }
 
    /**
     * @return the insert SQL 
     */
    protected String getInsertSql()
    {

        final StringBuilder query = new StringBuilder("INSERT INTO abp_STATE_MACHINE (");
        //query.append("ID, ");
        
                                //
        // primitive fields
        //
                query.append("NAME "); // string -> String
        
                        
                        
        query.append(") VALUES (");
        
                                        query.append("?"); // NAME
        
                                
                                
        query.append(")");


        return query.toString();
    }

    /**
     * create the new state machine
     * @param transaction the transaction used to create the entity
     * @param stateMachineValue the State Machine to create
      */
    protected Long insertStateMachine(
        final IDbTransaction transaction,
        final StateMachine stateMachineValue
     )
    {
        if (stateMachineValue.getId() != null)
        {
            throw new IllegalArgumentException("Can't insert a new State Machine with non-null ID " + stateMachineValue.getId());
        }
        
        final Long id = insertStateMachineSafe( transaction,
            stateMachineValue
         );
        return id;
    }

    /**
     * create the new state machine
     * @param transaction the transaction used to create the entity
     * @param stateMachineValue the State Machine to create
      */
    protected abstract Long insertStateMachineSafe(
        final IDbTransaction transaction,
        final StateMachine stateMachineValue
     );
 
 
//TODO - Nodes one to many lists
 
    /**
     * @param transaction
     * @param stateMachine
                     */
    protected void updateStateMachine(final IDbTransaction transaction, final StateMachine stateMachine
            )
    {
        if (stateMachine.getId() == null)
        {
            throw new IllegalArgumentException(String.format("Can't update stateMachine %d with a null ID", stateMachine));
        }
        final StringBuilder query = new StringBuilder("UPDATE abp_STATE_MACHINE SET ");
        
                                //
        // primitive fields
        //
                query.append("NAME=?");
        
                        
                            
        query.append(" WHERE ID=?");
            
        final String updateSql = query.toString();
        transaction.executeUpdate(updateSql, // 
                                        nullable(stateMachine.getName())//
                                                        ,
        stateMachine.getId()
        );

    }

    /**
     * @param transaction the transaction to use to delete the entity
     * @param id The id of the State Machine to delete
     * @param cascade true if the delete should cascade to referenced types
     * @return a builder for the deleted state machine 
     */
    protected StateMachineBuilder deleteStateMachine(final IDbTransaction transaction, final Long id, final boolean cascade)
    {
        final StateMachineBuilder stateMachineBuilder = getStateMachineBuilder(transaction, id);
        final StateMachine stateMachine = stateMachineBuilder.build();
        
        if (stateMachine != null)
        {
            if (cascade)
            {
// list field count: 1
                //
                //delete references to list objects from state machine
                //
                deleteNodesReferencedFromStateMachine(transaction, id);
              }
             transaction.executeUpdate("DELETE FROM abp_STATE_MACHINE WHERE ID=?", id);
        }
        
        return stateMachineBuilder;
    }

     /**
      * @param transaction
      * @param stateMachineId 
      */
     protected void deleteNodesReferencedFromStateMachine(
         final IDbTransaction transaction, 
         final Long stateMachineId)
     {
         transaction.executeUpdate("DELETE FROM abp_STATE_NODE WHERE NODES_ID=?", stateMachineId);
     }

    /**
     * find state machine based on the query
     * @param transaction the transaction used to find the state machine objects 
     * @param query the query suffix to use
     * @param parameters the parameters to use by the query. The values will be used to supply values for the ? place-holders 
     * @return a collection of StateMachine objects for the query
     */
    protected List<StateMachine> findStateMachine(final IDbTransaction transaction, final String whereClause, Object ... params)
    {
        final String sql = StateMachineResultSetVisitor.GET_SQL
                + prepareWhereClause(whereClause);
        
        final StateMachineResultSetVisitor visitor = transaction.executeQuery(new StateMachineResultSetVisitor(), sql, params);

        return visitor.getStateMachines();
    }

    /**
     * convenience method for loading a collection of ${form.Name.toLowreCase()} objects for
     * the collection of IDs 
     * @return a collection of all ${form.Name.toLowreCase()} by the given IDs
     */
    protected Collection<StateMachine> findStateMachines(final IDbTransaction dbTransaction,
            final Collection<Long> stateMachineIdToLoadList)
    {
        final StringBuilder whereClause = new StringBuilder("WHERE ID IN (");
        for (int i = stateMachineIdToLoadList.size(); --i >= 0;)
        {
            whereClause.append("?,");
        }
        whereClause.delete(whereClause.length() - 1, whereClause.length());
        whereClause.append(")");
        return findStateMachine(dbTransaction, whereClause.toString(), stateMachineIdToLoadList.toArray());
    }

    /**
     * find state machine builders based on the query
     * @param transaction the transaction used to find the state machine builder objects 
     * @param query the query suffix to use
     * @param parameters the parameters to use by the query. The values will be used to supply values for the ? place-holders 
     * @return a collection of StateMachine builders objects for the query
     */
    protected List<StateMachineBuilder> findStateMachineBuilders(final IDbTransaction transaction, final String whereClause, Object ... params)
    {
        final String sql = StateMachineResultSetVisitor.GET_SQL
                + prepareWhereClause(whereClause);
        final StateMachineResultSetVisitor visitor = transaction.executeQuery(new StateMachineResultSetVisitor(), sql, params);
        return visitor.getBuilders();
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

         query.append("NAME");
        query.append(" FROM abp_STATE_MACHINE ");
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
