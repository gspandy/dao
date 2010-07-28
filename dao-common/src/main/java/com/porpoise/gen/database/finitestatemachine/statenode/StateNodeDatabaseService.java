/**
 * StateNodeDatabaseService.java

 */
package com.porpoise.gen.database.finitestatemachine.statenode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.porpoise.gen.search.SearchRange;
import com.porpoise.gen.search.SearchResult;
import com.porpoise.gen.aaron.ApplicationInfo;
import com.porpoise.gen.aaron.SessionInfo;

import com.porpoise.gen.exception.ValidationException;

import com.porpoise.gen.finitestatemachine.statenode.StateNodeBuilder;
import com.porpoise.gen.finitestatemachine.statenode.api.IStateNodeService;
import com.porpoise.gen.finitestatemachine.statenode.api.IStateNodeService;
import com.porpoise.gen.finitestatemachine.statenode.StateNode;
import com.porpoise.gen.finitestatemachine.statenode.*;
import com.porpoise.gen.cache.ObjectCache;
import com.porpoise.gen.database.AbstractDatabaseService;
import com.porpoise.gen.database.assembler.finitestatemachine.StateNodeAssembler;
import com.porpoise.gen.database.DbConnectionFactory;
import com.porpoise.gen.database.IDbTransaction;
import com.porpoise.gen.database.dao.finitestatemachine.statenode.StateNodeDao;
import com.porpoise.gen.database.assembler.AssemblerSuppliers;
import com.porpoise.gen.database.assembler.AssemblerContext;
import com.porpoise.gen.query.CustomQueryImpl;
import com.porpoise.gen.util.collections.Function;
import com.porpoise.gen.util.collections.Lists;

/**
 * StateNodeDatabaseService
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 *
 * <p>
 * service implementation for the state node class
 * </p>
 */
public class StateNodeDatabaseService extends AbstractDatabaseService implements IStateNodeService
{
    /**
     * Get Transition For Name
     *
     * @return the StateNode
     * @throws ValidationException     
     */
    @Override
    public StateNode getTransitionForName(final StateNode stateNode) throws ValidationException
    {
        // TODO - implement me!
        return stateNode;
    }


    /**
     * create a new state node
     * @param stateNode the state node to create
     * @return the created state node
     * @throws ValidationException
     */
    @Override
    public IStateNodeAccessor createStateNode(final IStateNodeAccessor stateNode) throws ValidationException
    {
        final IDbTransaction transaction = DbConnectionFactory.getInstance().startNewTransaction();

        final StateNode loadedStateNode;
        try
        {
            final ObjectCache cache = newCache();
            final AssemblerContext context = new AssemblerContext(transaction,
                    cache);
            final Long id = StateNodeAssembler.save(context, stateNode);
            
            //loadedStateNode = StateNodeAssembler.assemble(transaction, cache, id, true);
            loadedStateNode = stateNode;
            loadedStateNode.setId(id);

            commit(context);
        }
        finally
        {
            DbConnectionFactory.getInstance().close(transaction);
        }
        return loadedStateNode;
    }

    /** 
     * @param id the id of the state node to delete
     *
     * @return the deleted state node 
     * @throws ValidationException
     */
    @Override
    public StateNode deleteStateNode(final Long id) throws ValidationException
    {
        final IDbTransaction transaction = DbConnectionFactory.getInstance().startNewTransaction();
        final StateNode stateNode; 
        try
        {
            stateNode = getStateNode(id);
            StateNodeDao.deleteStateNode(transaction, id, false);

            transaction.commit();

            // TODO - lock the session cache
            final ObjectCache cache = getSession().getSessionCache();
            cache.getStateNodeMap().remove(id);
            cache.flushStateNodeCache();
        }
        finally
        {
            DbConnectionFactory.getInstance().close(transaction);
        }
        return stateNode;
    }

    /** 
     * @param query the SQL query
     * @param parameters The parameters to use substitute for the SQL query placeholders 
     * @return a list of state node matching the given query 
     */
    @Override
    public SearchResult<StateNode> findStateNode(final String query, final Map<String, Serializable> parameters, final SearchRange range)
    {
        return findStateNode(query, parameters, true, range);
    }
    /** 
     * @param query the SQL query
     * @param parameters The parameters to use substitute for the SQL query placeholders 
     * @return a list of state node matching the given query 
     */
    public SearchResult<StateNode> findStateNode(final String query, final Map<String, Serializable> parameters, final boolean fetchEager, final SearchRange range)
    {
        List<StateNode> stateNodeList;
        final IDbTransaction transaction = DbConnectionFactory.getInstance().startNewTransaction();
        final ObjectCache cache = getSession().getSessionCache();
        
        try
        {
            final CustomQueryImpl customQuery = new CustomQueryImpl(query);

            if (parameters != null)
            {
                for (final Entry<String, Serializable> param : parameters.entrySet())
                {
                    customQuery.setUserParameter(param.getKey(), param.getValue().toString());
                }
            }

            final Object[] params = customQuery.getJdbcParameters().toArray(new Object[0]);
            final List<StateNodeBuilder> builders = StateNodeDao.findStateNodeBuilders(transaction, customQuery.getJdbcSql(),
                    params);
            //stateNodeList = Lists.transform(builders, MutableStateNodeFunction.getSharedInstance());

            final Function<StateNodeBuilder, IStateNode> function = getFunction(transaction,
                    cache ,
                    fetchEager );
            stateNodeList = Lists.transform(builders, function);          
        }
        finally
        {
            DbConnectionFactory.getInstance().close(transaction);
        }
        //TODO - restrict the search!
        return new SearchResult(range, stateNodeList.size(), stateNodeList);
    }

    /** 
     * @param id the ID of the state node to fetch
     * @return The state node with the given id 
     */
    public StateNode getStateNode(final Long id)
    {
        StateNode stateNode;
        final IDbTransaction transaction = DbConnectionFactory.getInstance().startNewTransaction();
        try
        {
            stateNode = StateNodeAssembler.assemble(transaction, newCache(), id);
        }
        finally
        {
            DbConnectionFactory.getInstance().close(transaction);
        }
        return stateNode;
    }

    /** 
     * @return all persisted state node objects
     */
    public SearchResult<? extends IStateNodeAccessor> listStateNodes(final SearchRange range)
    {
        return findStateNode("", null, range);
    }

    /** 
     * @param stateNode the state node to update
     * @return The updated stateNode 
     * @throws ValidationException
     */
    public IStateNodeAccessor updateStateNode(final IStateNodeAccessor stateNode) throws ValidationException
    {
        if (stateNode.getId() == null)
        {
            throw new IllegalArgumentException(String.format("Cannot update %s as it doesn't have an ID", stateNode));
        }

        final IDbTransaction transaction = DbConnectionFactory.getInstance().startNewTransaction();

        final StateNode updatedStateNode;
        try
        {
            final ObjectCache cache = newCache();
            final AssemblerContext context = new AssemblerContext(transaction,
                    cache);
            final Long id = StateNodeAssembler.save(context, stateNode);
            
            updatedStateNode = stateNode;
            updatedStateNode.setId(id);

            commit(context);
        }
        finally
        {
            DbConnectionFactory.getInstance().close(transaction);
        }
        return updatedStateNode;
    }
    
    private static Function<StateNodeBuilder, IStateNode> getFunction(final IDbTransaction transaction,
            final ObjectCache cache ,
            final boolean fetchEager )
    {
        final Function<StateNodeBuilder, IStateNode> function;
        if (fetchEager)
        {
            function = new Function<StateNodeBuilder, IStateNode>() {
                @Override
                public StateNode apply(final StateNodeBuilder from)
                {
                    final StateNode assembledStateNode = StateNodeAssembler.assemble(transaction,
                            cache,
                            from.build().getId());
                    return assembledStateNode;
                }
            };
        }
        else
        {
            function = new Function<StateNodeBuilder, IStateNode>() {
                @Override
                public StateNode apply(final StateNodeBuilder from)
                {
                    return from.build();
                }
            };
        }
        return function;
     }
}
