/**
 * StateNodeResultSetVisitor.java

 */
package com.porpoise.gen.database.dao.finitestatemachine.statenode;

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

import com.porpoise.gen.finitestatemachine.statenode.StateNodeBuilder;

import com.porpoise.gen.finitestatemachine.statenode.StateNode;

/**
 * StateNodeResultSetVisitor
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
class StateNodeResultSetVisitor extends AbstractResultSetVisitor implements Function<StateNodeBuilder, IStateNode>
{
    static final String GET_SQL = getBaseSelectSQL();

    private List<StateNodeBuilder> builders;

    /**
     * Default Constructor
     */    
    public StateNodeResultSetVisitor()
    {
        this(10);
    }

    /**
     * @param initialSize the expected size
     */
    public StateNodeResultSetVisitor(int initialSize)
    {
        this.builders = new ArrayList<StateNodeBuilder>(initialSize);
    }
    
    /**
     * @return a list of StateNode builders
     */
    public List<StateNodeBuilder> getBuilders()
    {
        return this.builders;
    }
    
    /**
     * @return a list of StateNode objects
     */
    public List<StateNode> getStateNodes()
    {
        return Lists.transform(this.builders, this);
    }

    /**
     * @param builder the builder used to crate new StateNode objects
     * @return the StateNode built from the builder 
     */
    public StateNode apply(StateNodeBuilder builder)
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
        query.append(" FROM abp_STATE_NODE ");
            
        return query.toString();
    }

    @Override
    public boolean onResultSet(final ResultSet resultSet)
    {
        int columnIndex = 1;
        
        final StateNodeBuilder builder = new StateNodeBuilder();  

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
     * @return the single state node returned
     */
    public StateNode getSingleStateNode()
    {
        return getSingleStateNodeBuilder().build();
    }

    /**
     * convenience method for when zero or one result was expected
     * 
     * @return the single state node builder returned
     */
    public StateNodeBuilder getSingleStateNodeBuilder()
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
