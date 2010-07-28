/**
 * AbstractStateTransitionDao.java

 */
package com.porpoise.gen.database.dao.finitestatemachine.statetransition;

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

import com.porpoise.gen.finitestatemachine.statetransition.StateTransition;
import com.porpoise.gen.finitestatemachine.statetransition.StateTransitionBuilder;

import com.porpoise.gen.database.visitors.SingleScalarResultVisitor;
import com.porpoise.gen.database.visitors.Visitors;


/**
 * AbstractStateTransitionDao
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 *
 */
abstract class AbstractStateTransitionDao extends AbstractDao
{
    /**
     * A AbstractStateTransitionDao instance may hold state so as to support deferred calls.
     * For instance, some object may initialize a new AbstractStateTransitionDao instance to
     * hand back to the caller which will at some point call {@link #insert(IDbTransaction)}, 
     * {@link #update(IDbTransaction)} or {@link #delete(IDbTransaction)}.
     */
    private StateTransition stateTransition;
    private final Long stateTransitionId;
    private Long stateNodeTransitionsId;
 

    protected final static Map<String, String> PROPERTY_TO_COLUMN_NAME_MAP;

    static
    {
        final Map<String, String> temp = new HashMap<String, String>();
        // property name is the same as NAME
        temp.put("FROMSTATE", "FROM_STATE");
        temp.put("TOSTATE", "TO_STATE");
        PROPERTY_TO_COLUMN_NAME_MAP = Collections.unmodifiableMap(temp);
    }


    /**
     * default constructor. This implementation won't hold any state.
     */
    public AbstractStateTransitionDao()
    {
        this((Long) null);
    }

    /**
     * @param stateTransitionValue     
     */
    public AbstractStateTransitionDao(final StateTransition stateTransitionValue, 
        Long stateNodeTransitionsIdValue
     )
    {
        this(stateTransitionValue == null ? null : stateTransitionValue.getId());
        this.stateTransition = stateTransitionValue;
        stateNodeTransitionsId = stateNodeTransitionsIdValue;
     }

    /**
     * @param stateTransitionIdValue the id of the state transition
     */
    public AbstractStateTransitionDao(final Long stateTransitionIdValue)
    {
        this.stateTransitionId = stateTransitionIdValue;
    }

 
    /**
     * insert method
     * 
     * @param transaction
     *            the database transaction to use for the insert
     * @return the new ID for the inserted state transition
     * @throws DbException
     */
    public Long insert(final IDbTransaction transaction) throws DbException
    {
        final Long id = insertStateTransition(transaction, this.stateTransition,   this.stateNodeTransitionsId );
        return id;
    }

    /**
     * delete method
     * 
     * @param transaction
     *            the database transaction to use for the delete
     * @return the deleted state transition
     * @throws DbException
     */
    public StateTransition delete(final IDbTransaction transaction) throws DbException
    {
        final StateTransitionBuilder deletedStateTransition = deleteStateTransition(transaction, this.stateTransitionId, false);
        return deletedStateTransition.build();
    }

    /**
     * update method
     * 
     * @param transaction
     *            the database transaction to use for the delete
     * @return the updated state transition
     * @throws DbException
     */
    public StateTransition update(final IDbTransaction transaction) throws DbException
    {
        updateStateTransition(transaction, this.stateTransition,   this.stateNodeTransitionsId );
        return this.stateTransition;
    }

    /**
     * get state transition for the given ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the state transition to load
     * @return the StateTransition object for the ID
     */
    protected StateTransition getStateTransition(final IDbTransaction transaction, final Long id)
    {
        return getStateTransitionBuilder(transaction, id).build();
    }
    
     /**
     * get state transition for the given ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the state transition to load
     * @return a StateTransitionBuilder for the ID
     */
    protected StateTransitionBuilder getStateTransitionBuilder(final IDbTransaction transaction, final Long id)
    {
        final String sql = StateTransitionResultSetVisitor.GET_SQL + " WHERE ID=?";
        final StateTransitionResultSetVisitor visitor = transaction.executeQuery(new StateTransitionResultSetVisitor(), sql, id);
        
        final StateTransitionBuilder stateTransitionBuilder = visitor.getSingleStateTransitionBuilder();
        if (stateTransitionBuilder != null)
        {
            stateTransitionBuilder.id(id);
        }
        return stateTransitionBuilder;
     }
 
    /**
     * @return the insert SQL 
     */
    protected String getInsertSql()
    {

        final StringBuilder query = new StringBuilder("INSERT INTO abp_STATE_TRANSITION (");
        //query.append("ID, ");
        
                                //
        // primitive fields
        //
                query.append("NAME,  "); // string -> String
                query.append("FROM_STATE,  "); // string -> String
                query.append("TO_STATE,  "); // string -> String
        
                        
                                //
        // referenced by fields
        //
                query.append("STATE_NODE_TRANSITIONS_ID ");
        
        query.append(") VALUES (");
        
                                        query.append("?, "); // NAME
                query.append("?, "); // FROM_STATE
                query.append("?, "); // TO_STATE
        
                                
                                        query.append("? ");// FK TRANSITIONS_ID
        
        query.append(")");


        return query.toString();
    }

    /**
     * create the new state transition
     * @param transaction the transaction used to create the entity
     * @param stateTransitionValue the State Transition to create
     * @param stateNodeTransitionsIdValue the referencing stateNode ID 
      */
    protected Long insertStateTransition(
        final IDbTransaction transaction,
        final StateTransition stateTransitionValue, 
        final Long stateNodeTransitionsIdValue
     )
    {
        if (stateTransitionValue.getId() != null)
        {
            throw new IllegalArgumentException("Can't insert a new State Transition with non-null ID " + stateTransitionValue.getId());
        }
        
        final Long id = insertStateTransitionSafe( transaction,
            stateTransitionValue, 
            stateNodeTransitionsIdValue
         );
        return id;
    }

    /**
     * create the new state transition
     * @param transaction the transaction used to create the entity
     * @param stateTransitionValue the State Transition to create
     * @param stateNodeTransitionsIdValue the referencing stateNode ID 
      */
    protected abstract Long insertStateTransitionSafe(
        final IDbTransaction transaction,
        final StateTransition stateTransitionValue, 
        final Long stateNodeTransitionsIdValue
     );
 
 
    /**
     * @param transaction
     * @param stateTransition
                     * @param transitionsId
             */
    protected void updateStateTransition(final IDbTransaction transaction, final StateTransition stateTransition, 
                final Long stateNodeTransitionsId
            )
    {
        if (stateTransition.getId() == null)
        {
            throw new IllegalArgumentException(String.format("Can't update stateTransition %d with a null ID", stateTransition));
        }
        final StringBuilder query = new StringBuilder("UPDATE abp_STATE_TRANSITION SET ");
        
                                //
        // primitive fields
        //
                query.append("NAME=?, ");
                query.append("FROM_STATE=?, ");
                query.append("TO_STATE=?, ");
        
                        
                                //
        // referenced by fields
        //
                query.append("STATE_NODE_TRANSITIONS_ID=?");
            
        query.append(" WHERE ID=?");
            
        final String updateSql = query.toString();
        transaction.executeUpdate(updateSql, // 
                                        nullable(stateTransition.getName()), //
                        nullable(stateTransition.getFromState()), //
                        nullable(stateTransition.getToState()), //
                                                                nullable(stateNodeTransitionsId)
,
        stateTransition.getId()
        );

    }

    /**
     * @param transaction the transaction to use to delete the entity
     * @param id The id of the State Transition to delete
     * @param cascade true if the delete should cascade to referenced types
     * @return a builder for the deleted state transition 
     */
    protected StateTransitionBuilder deleteStateTransition(final IDbTransaction transaction, final Long id, final boolean cascade)
    {
        final StateTransitionBuilder stateTransitionBuilder = getStateTransitionBuilder(transaction, id);
        final StateTransition stateTransition = stateTransitionBuilder.build();
        
        if (stateTransition != null)
        {
            if (cascade)
            {
// list field count: 0
                 //
                //delete references to this state transition
                //
                transaction.executeUpdate("DELETE FROM abp_STATE_NODE WHERE STATE_TRANSITION_ID=?", id);
             }
             transaction.executeUpdate("DELETE FROM abp_STATE_TRANSITION WHERE ID=?", id);
        }
        
        return stateTransitionBuilder;
    }


    /**
     * find state transition based on the query
     * @param transaction the transaction used to find the state transition objects 
     * @param query the query suffix to use
     * @param parameters the parameters to use by the query. The values will be used to supply values for the ? place-holders 
     * @return a collection of StateTransition objects for the query
     */
    protected List<StateTransition> findStateTransition(final IDbTransaction transaction, final String whereClause, Object ... params)
    {
        final String sql = StateTransitionResultSetVisitor.GET_SQL
                + prepareWhereClause(whereClause);
        
        final StateTransitionResultSetVisitor visitor = transaction.executeQuery(new StateTransitionResultSetVisitor(), sql, params);

        return visitor.getStateTransitions();
    }

    /**
     * convenience method for loading a collection of ${form.Name.toLowreCase()} objects for
     * the collection of IDs 
     * @return a collection of all ${form.Name.toLowreCase()} by the given IDs
     */
    protected Collection<StateTransition> findStateTransitions(final IDbTransaction dbTransaction,
            final Collection<Long> stateTransitionIdToLoadList)
    {
        final StringBuilder whereClause = new StringBuilder("WHERE ID IN (");
        for (int i = stateTransitionIdToLoadList.size(); --i >= 0;)
        {
            whereClause.append("?,");
        }
        whereClause.delete(whereClause.length() - 1, whereClause.length());
        whereClause.append(")");
        return findStateTransition(dbTransaction, whereClause.toString(), stateTransitionIdToLoadList.toArray());
    }

    /**
     * find state transition builders based on the query
     * @param transaction the transaction used to find the state transition builder objects 
     * @param query the query suffix to use
     * @param parameters the parameters to use by the query. The values will be used to supply values for the ? place-holders 
     * @return a collection of StateTransition builders objects for the query
     */
    protected List<StateTransitionBuilder> findStateTransitionBuilders(final IDbTransaction transaction, final String whereClause, Object ... params)
    {
        final String sql = StateTransitionResultSetVisitor.GET_SQL
                + prepareWhereClause(whereClause);
        final StateTransitionResultSetVisitor visitor = transaction.executeQuery(new StateTransitionResultSetVisitor(), sql, params);
        return visitor.getBuilders();
    }

    /**
     * find state transition referenced by the state node's transitions ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the ${reference.ReferencingField.toLowerCase()} to load
     * @return a collection of StateTransition objects
     */
    protected Collection<StateTransition> findStateTransitionForStateNodeTransitions(
        final IDbTransaction transaction, final Long stateNodeTransitionsId)
    {
        //return findStateTransition(transaction, " JOIN abp_STATE_NODE ON abp_STATE_NODE.TRANSITIONS_ID = abp_STATE_TRANSITION.ID WHERE abp_STATE_NODE.ID=?", stateNodeTransitionsId);
        return findStateTransition(transaction, " WHERE STATE_NODE_TRANSITIONS_ID=?", stateNodeTransitionsId);
    }

    /**
     * find state transition builders referenced by the state node's transitions ID
     *
     * @param transaction the database transaction used to load the entity
     * @param id the ID of the ${reference.ReferencingField.toLowerCase()} to load
     * @return a collection of StateTransition objects
     */
    protected Collection<StateTransitionBuilder> findStateTransitionBuildersForStateNodeTransitions(
        final IDbTransaction transaction, final Long StateNodetransitionsId)
    {
        return findStateTransitionBuilders(transaction, " WHERE STATE_NODE_TRANSITIONS_ID=?", StateNodetransitionsId);
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
         query.append("FROM_STATE, ");
         query.append("TO_STATE, ");
        //
        // foreign keys
        //
        query.append("TRANSITIONS_ID ");
        query.append(" FROM abp_STATE_TRANSITION ");
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
