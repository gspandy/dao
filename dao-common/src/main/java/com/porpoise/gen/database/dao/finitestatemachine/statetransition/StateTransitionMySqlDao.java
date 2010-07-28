/**
 * StateTransitionMySqlDao.java

 */
package com.porpoise.gen.database.dao.finitestatemachine.statetransition;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.porpoise.gen.database.IDbTransaction;
import com.porpoise.gen.database.Null;
import com.porpoise.gen.database.visitors.AbstractResultSetVisitor;
import com.porpoise.gen.database.visitors.FirstResultToMapVisitor;
import com.porpoise.gen.database.visitors.Visitors;

import com.porpoise.gen.finitestatemachine.statetransition.StateTransition;
import com.porpoise.gen.finitestatemachine.statetransition.StateTransitionBuilder;


/**
 * StateTransitionMySqlDao
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
class StateTransitionMySqlDao extends AbstractStateTransitionDao
{
    public StateTransitionMySqlDao()
    {
        super();
    }

    public StateTransitionMySqlDao(final StateTransition stateTransition, 
        final Long stateNodeTransitionsIdValue
     )
    {
        super(stateTransition, 
        stateNodeTransitionsIdValue
         );
    }

    public StateTransitionMySqlDao(final Long id)
    {
        super(id);
    }

    /**
     * create the new state transition
     * @param transaction the transaction used to create the entity
     * @param stateTransitionValue the State Transition to create
     * @param stateNodeTransitionsIdValue the referencing stateNode ID 
      */
    @Override
    protected Long insertStateTransitionSafe(
        final IDbTransaction transaction,
        final StateTransition stateTransitionValue, 
        final Long stateNodeTransitionsIdValue
     )
    {
        final String createSql = getInsertSql();

        transaction.executeUpdate(createSql, 
                                                nullable(stateTransitionValue.getName()),  // Name
                                nullable(stateTransitionValue.getFromState()),  // From State
                                nullable(stateTransitionValue.getToState()),  // To State
                
                                
                                        nullable(stateNodeTransitionsIdValue)//
        );

        //
        // fetch the ID from the last insert
        //
        final FirstResultToMapVisitor visitor = transaction.executeQuery(Visitors.firstResultVisitor(),
                "SELECT LAST_INSERT_ID()");
        
        // FIXME -this was used as it was found that IDENTITY_VAL_LOCAL was returning multiple (identical) values ... ? 
        final Object next = visitor.getResultRow().values().iterator().next();
        return Long.valueOf(next.toString());
    }
 }
