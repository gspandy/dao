/**
 * StateNodeMySqlDao.java

 */
package com.porpoise.gen.database.dao.finitestatemachine.statenode;

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

import com.porpoise.gen.finitestatemachine.statenode.StateNode;
import com.porpoise.gen.finitestatemachine.statenode.StateNodeBuilder;

import com.porpoise.gen.database.dao.finitestatemachine.statetransition.StateTransitionDao;
import com.porpoise.gen.finitestatemachine.statetransition.StateTransitionBuilder;
import com.porpoise.gen.finitestatemachine.statetransition.IStateTransitionDetails;

/**
 * StateNodeMySqlDao
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
class StateNodeMySqlDao extends AbstractStateNodeDao
{
    public StateNodeMySqlDao()
    {
        super();
    }

    public StateNodeMySqlDao(final StateNode stateNode, 
        final Long stateMachineNodesIdValue
     )
    {
        super(stateNode, 
        stateMachineNodesIdValue
         );
    }

    public StateNodeMySqlDao(final Long id)
    {
        super(id);
    }

    /**
     * create the new state node
     * @param transaction the transaction used to create the entity
     * @param stateNodeValue the State Node to create
     * @param stateMachineNodesIdValue the referencing stateMachine ID 
      */
    @Override
    protected Long insertStateNodeSafe(
        final IDbTransaction transaction,
        final StateNode stateNodeValue, 
        final Long stateMachineNodesIdValue
     )
    {
        final String createSql = getInsertSql();

        transaction.executeUpdate(createSql, 
                                                nullable(stateNodeValue.getName()),  // Name
                
                                
                                        nullable(stateMachineNodesIdValue)//
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
