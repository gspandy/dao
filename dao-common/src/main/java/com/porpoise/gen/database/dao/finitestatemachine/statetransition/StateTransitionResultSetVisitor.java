/**
 * StateTransitionResultSetVisitor.java

 */
package com.porpoise.gen.database.dao.finitestatemachine.statetransition;

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

import com.porpoise.gen.finitestatemachine.statetransition.StateTransitionBuilder;

import com.porpoise.gen.finitestatemachine.statetransition.StateTransition;

/**
 * StateTransitionResultSetVisitor
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
class StateTransitionResultSetVisitor extends AbstractResultSetVisitor implements Function<StateTransitionBuilder, IStateTransition>
{
    static final String GET_SQL = getBaseSelectSQL();

    private List<StateTransitionBuilder> builders;

    /**
     * Default Constructor
     */    
    public StateTransitionResultSetVisitor()
    {
        this(10);
    }

    /**
     * @param initialSize the expected size
     */
    public StateTransitionResultSetVisitor(int initialSize)
    {
        this.builders = new ArrayList<StateTransitionBuilder>(initialSize);
    }
    
    /**
     * @return a list of StateTransition builders
     */
    public List<StateTransitionBuilder> getBuilders()
    {
        return this.builders;
    }
    
    /**
     * @return a list of StateTransition objects
     */
    public List<StateTransition> getStateTransitions()
    {
        return Lists.transform(this.builders, this);
    }

    /**
     * @param builder the builder used to crate new StateTransition objects
     * @return the StateTransition built from the builder 
     */
    public StateTransition apply(StateTransitionBuilder builder)
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

         query.append("NAME, ");
         query.append("FROM_STATE, ");
         query.append("TO_STATE");
        query.append(" FROM abp_STATE_TRANSITION ");
            
        return query.toString();
    }

    @Override
    public boolean onResultSet(final ResultSet resultSet)
    {
        int columnIndex = 1;
        
        final StateTransitionBuilder builder = new StateTransitionBuilder();  

        builder.id(getLong(resultSet, columnIndex++));
        //
        // NAME
        //
                builder.name(getString(resultSet, columnIndex++));
                //
        // FROM_STATE
        //
                builder.fromState(getString(resultSet, columnIndex++));
                //
        // TO_STATE
        //
                builder.toState(getString(resultSet, columnIndex++));
        
        builders.add(builder);
        
        return CONTINUE;
    }

    /**
     * convenience method for when zero or one result was expected
     * 
     * @return the single state transition returned
     */
    public StateTransition getSingleStateTransition()
    {
        return getSingleStateTransitionBuilder().build();
    }

    /**
     * convenience method for when zero or one result was expected
     * 
     * @return the single state transition builder returned
     */
    public StateTransitionBuilder getSingleStateTransitionBuilder()
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
