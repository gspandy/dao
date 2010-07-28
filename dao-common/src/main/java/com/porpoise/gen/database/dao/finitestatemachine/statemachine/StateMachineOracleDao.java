/**
 * StateMachineOracleDao.java

 */
package com.porpoise.gen.database.dao.finitestatemachine.statemachine;

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

import com.porpoise.gen.finitestatemachine.statemachine.StateMachine;
import com.porpoise.gen.finitestatemachine.statemachine.StateMachineBuilder;

import com.porpoise.gen.database.dao.finitestatemachine.statenode.StateNodeDao;
import com.porpoise.gen.finitestatemachine.statenode.StateNodeBuilder;
import com.porpoise.gen.finitestatemachine.statenode.IStateNodeDetails;

/**
 * StateMachineOracleDao
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
class StateMachineOracleDao extends AbstractStateMachineDao
{
    public StateMachineOracleDao()
    {
        super();
    }

    public StateMachineOracleDao(final StateMachine stateMachine
     )
    {
        super(stateMachine
         );
    }

    public StateMachineOracleDao(final Long id)
    {
        super(id);
    }

    /**
     * create the new state machine
     * @param transaction the transaction used to create the entity
     * @param stateMachineValue the State Machine to create
      */
     @Override
    protected Long insertStateMachineSafe(
        final IDbTransaction transaction,
        final StateMachine stateMachineValue
     )
    {
        final String createSql = getInsertSql();

        transaction.executeUpdate(createSql, 
                                                nullable(stateMachineValue.getName()) // Name
                
                                
                                );

        //
        // fetch the ID from the last insert
        //
        final FirstResultToMapVisitor visitor = transaction.executeQuery(Visitors.firstResultVisitor(),
                "SELECT IDENTITY_VAL_LOCAL() FROM abp_STATE_MACHINE");
        
        // FIXME -this was used as it was found that IDENTITY_VAL_LOCAL was returning multiple (identical) values ... ? 
        final Object next = visitor.getResultRow().values().iterator().next();
        return Long.valueOf(next.toString());
    }
 
}
