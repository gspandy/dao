/**
 * StateMachineResultSetVisitor.java

 */
package com.porpoise.gen.database.dao.finitestatemachine.statemachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.ResultSet;

import com.porpoise.gen.util.collections.Lists;
import com.porpoise.gen.util.collections.Function;
import com.porpoise.gen.database.visitors.AbstractResultSetVisitor;
import com.porpoise.gen.database.IDbTransaction;
import com.porpoise.gen.database.IDbTransaction;
import com.porpoise.gen.database.dao.AbstractDao;

import com.porpoise.gen.database.DbException;

import com.porpoise.gen.finitestatemachine.statemachine.StateMachineBuilder;

import com.porpoise.gen.finitestatemachine.statemachine.StateMachine;

/**
 * StateMachineResultSetVisitor
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
class StateMachineResultSetVisitor extends AbstractResultSetVisitor implements Function<StateMachineBuilder, IStateMachine>
{
    static final String GET_SQL = getBaseSelectSQL();

    private List<StateMachineBuilder> builders;

    /**
     * Default Constructor
     */    
    public StateMachineResultSetVisitor()
    {
        this(10);
    }

    /**
     * @param initialSize the expected size
     */
    public StateMachineResultSetVisitor(int initialSize)
    {
        this.builders = new ArrayList<StateMachineBuilder>(initialSize);
    }
    
    /**
     * @return a list of StateMachine builders
     */
    public List<StateMachineBuilder> getBuilders()
    {
        return this.builders;
    }
    
    /**
     * @return a list of StateMachine objects
     */
    public List<StateMachine> getStateMachines()
    {
        return Lists.transform(this.builders, this);
    }

    /**
     * @param builder the builder used to crate new StateMachine objects
     * @return the StateMachine built from the builder 
     */
    public StateMachine apply(StateMachineBuilder builder)
    {
        return builder.build();
    }

    /**
     * @return the basic select query
     */
    private static String getBaseSelectSQL()
    {
        final StringBuilder query = new StringBuilder("SELECT ");
          query.append("ID, ");

         query.append("NAME");
        query.append(" FROM abp_STATE_MACHINE ");
            
        return query.toString();
    }

    @Override
    public boolean onResultSet(final ResultSet resultSet)
    {
        int columnIndex = 1;
        
        final StateMachineBuilder builder = new StateMachineBuilder();  

        builder.id(getLong(resultSet, columnIndex++));
        //
        // NAME
        //
                builder.name(getString(resultSet, columnIndex++));
        
        builders.add(builder);
        
        return CONTINUE;
    }

    /**
     * convenience method for when zero or one result was expected
     * 
     * @return the single state machine returned
     */
    public StateMachine getSingleStateMachine()
    {
        return getSingleStateMachineBuilder().build();
    }

    /**
     * convenience method for when zero or one result was expected
     * 
     * @return the single state machine builder returned
     */
    public StateMachineBuilder getSingleStateMachineBuilder()
    {
        if (this.builders.isEmpty())
        {
            return null;
        }
        if (this.builders.size() == 1)
        {
            return getBuilders().get(0);
        }
        throw new IllegalStateException(String.format("multiple results (%s) were returned", Integer.valueOf(this.builders.size())));
    }

}
