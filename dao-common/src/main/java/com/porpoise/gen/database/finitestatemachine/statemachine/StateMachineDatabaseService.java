/**
 * StateMachineDatabaseService.java

 */
package com.porpoise.gen.database.finitestatemachine.statemachine;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.porpoise.gen.search.SearchRange;
import com.porpoise.gen.search.SearchResult;
import com.porpoise.gen.aaron.ApplicationInfo;
import com.porpoise.gen.aaron.SessionInfo;

import com.porpoise.gen.exception.ValidationException;

import com.porpoise.gen.finitestatemachine.statemachine.StateMachineBuilder;
import com.porpoise.gen.finitestatemachine.statemachine.api.IStateMachineService;
import com.porpoise.gen.finitestatemachine.statemachine.api.IStateMachineService;
import com.porpoise.gen.finitestatemachine.statemachine.StateMachine;
import com.porpoise.gen.finitestatemachine.statemachine.*;
import com.porpoise.gen.cache.ObjectCache;
import com.porpoise.gen.database.AbstractDatabaseService;
import com.porpoise.gen.database.assembler.finitestatemachine.StateMachineAssembler;
import com.porpoise.gen.database.DbConnectionFactory;
import com.porpoise.gen.database.IDbTransaction;
import com.porpoise.gen.database.dao.finitestatemachine.statemachine.StateMachineDao;
import com.porpoise.gen.database.assembler.AssemblerSuppliers;
import com.porpoise.gen.database.assembler.AssemblerContext;
import com.porpoise.gen.query.CustomQueryImpl;
import com.porpoise.gen.util.collections.Function;
import com.porpoise.gen.util.collections.Lists;

/**
 * StateMachineDatabaseService
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 *
 * <p>
 * service implementation for the state machine class
 * </p>
 */
public class StateMachineDatabaseService extends AbstractDatabaseService implements IStateMachineService
{
    /**
     * Get State For Name
     *
     * @return the StateMachine
     * @throws ValidationException     
     */
    @Override
    public StateMachine getStateForName(final StateMachine stateMachine) throws ValidationException
    {
        // TODO - implement me!
        return stateMachine;
    }

    /**
     * Get Transition For Name
     *
     * @return the StateMachine
     * @throws ValidationException     
     */
    @Override
    public StateMachine getTransitionForName(final StateMachine stateMachine) throws ValidationException
    {
        // TODO - implement me!
        return stateMachine;
    }


    /**
     * create a new state machine
     * @param stateMachine the state machine to create
     * @return the created state machine
     * @throws ValidationException
     */
    @Override
    public IStateMachineAccessor createStateMachine(final IStateMachineAccessor stateMachine) throws ValidationException
    {
        final IDbTransaction transaction = DbConnectionFactory.getInstance().startNewTransaction();

        final StateMachine loadedStateMachine;
        try
        {
            final ObjectCache cache = newCache();
            final AssemblerContext context = new AssemblerContext(transaction,
                    cache);
            final Long id = StateMachineAssembler.save(context, stateMachine);
            
            //loadedStateMachine = StateMachineAssembler.assemble(transaction, cache, id, true);
            loadedStateMachine = stateMachine;
            loadedStateMachine.setId(id);

            commit(context);
        }
        finally
        {
            DbConnectionFactory.getInstance().close(transaction);
        }
        return loadedStateMachine;
    }

    /** 
     * @param id the id of the state machine to delete
     *
     * @return the deleted state machine 
     * @throws ValidationException
     */
    @Override
    public StateMachine deleteStateMachine(final Long id) throws ValidationException
    {
        final IDbTransaction transaction = DbConnectionFactory.getInstance().startNewTransaction();
        final StateMachine stateMachine; 
        try
        {
            stateMachine = getStateMachine(id);
            StateMachineDao.deleteStateMachine(transaction, id, false);

            transaction.commit();

            // TODO - lock the session cache
            final ObjectCache cache = getSession().getSessionCache();
            cache.getStateMachineMap().remove(id);
            cache.flushStateMachineCache();
        }
        finally
        {
            DbConnectionFactory.getInstance().close(transaction);
        }
        return stateMachine;
    }

    /** 
     * @param query the SQL query
     * @param parameters The parameters to use substitute for the SQL query placeholders 
     * @return a list of state machine matching the given query 
     */
    @Override
    public SearchResult<StateMachine> findStateMachine(final String query, final Map<String, Serializable> parameters, final SearchRange range)
    {
        return findStateMachine(query, parameters, true, range);
    }
    /** 
     * @param query the SQL query
     * @param parameters The parameters to use substitute for the SQL query placeholders 
     * @return a list of state machine matching the given query 
     */
    public SearchResult<StateMachine> findStateMachine(final String query, final Map<String, Serializable> parameters, final boolean fetchEager, final SearchRange range)
    {
        List<StateMachine> stateMachineList;
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
            final List<StateMachineBuilder> builders = StateMachineDao.findStateMachineBuilders(transaction, customQuery.getJdbcSql(),
                    params);
            //stateMachineList = Lists.transform(builders, MutableStateMachineFunction.getSharedInstance());

            final Function<StateMachineBuilder, IStateMachine> function = getFunction(transaction,
                    cache ,
                    fetchEager );
            stateMachineList = Lists.transform(builders, function);          
        }
        finally
        {
            DbConnectionFactory.getInstance().close(transaction);
        }
        //TODO - restrict the search!
        return new SearchResult(range, stateMachineList.size(), stateMachineList);
    }

    /** 
     * @param id the ID of the state machine to fetch
     * @return The state machine with the given id 
     */
    public StateMachine getStateMachine(final Long id)
    {
        StateMachine stateMachine;
        final IDbTransaction transaction = DbConnectionFactory.getInstance().startNewTransaction();
        try
        {
            stateMachine = StateMachineAssembler.assemble(transaction, newCache(), id);
        }
        finally
        {
            DbConnectionFactory.getInstance().close(transaction);
        }
        return stateMachine;
    }

    /** 
     * @return all persisted state machine objects
     */
    public SearchResult<? extends IStateMachineAccessor> listStateMachines(final SearchRange range)
    {
        return findStateMachine("", null, range);
    }

    /** 
     * @param stateMachine the state machine to update
     * @return The updated stateMachine 
     * @throws ValidationException
     */
    public IStateMachineAccessor updateStateMachine(final IStateMachineAccessor stateMachine) throws ValidationException
    {
        if (stateMachine.getId() == null)
        {
            throw new IllegalArgumentException(String.format("Cannot update %s as it doesn't have an ID", stateMachine));
        }

        final IDbTransaction transaction = DbConnectionFactory.getInstance().startNewTransaction();

        final StateMachine updatedStateMachine;
        try
        {
            final ObjectCache cache = newCache();
            final AssemblerContext context = new AssemblerContext(transaction,
                    cache);
            final Long id = StateMachineAssembler.save(context, stateMachine);
            
            updatedStateMachine = stateMachine;
            updatedStateMachine.setId(id);

            commit(context);
        }
        finally
        {
            DbConnectionFactory.getInstance().close(transaction);
        }
        return updatedStateMachine;
    }
    
    private static Function<StateMachineBuilder, IStateMachine> getFunction(final IDbTransaction transaction,
            final ObjectCache cache ,
            final boolean fetchEager )
    {
        final Function<StateMachineBuilder, IStateMachine> function;
        if (fetchEager)
        {
            function = new Function<StateMachineBuilder, IStateMachine>() {
                @Override
                public StateMachine apply(final StateMachineBuilder from)
                {
                    final StateMachine assembledStateMachine = StateMachineAssembler.assemble(transaction,
                            cache,
                            from.build().getId());
                    return assembledStateMachine;
                }
            };
        }
        else
        {
            function = new Function<StateMachineBuilder, IStateMachine>() {
                @Override
                public StateMachine apply(final StateMachineBuilder from)
                {
                    return from.build();
                }
            };
        }
        return function;
     }
}
