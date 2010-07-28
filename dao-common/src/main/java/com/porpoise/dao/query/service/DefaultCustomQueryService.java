/**
 * DefaultCustomQueryService.java
 */
package com.porpoise.dao.query.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Preconditions;
import com.porpoise.dao.database.DbConnectionFactory;
import com.porpoise.dao.database.IDbTransaction;
import com.porpoise.dao.database.visitors.AbstractResultSetVisitor;
import com.porpoise.dao.database.visitors.AtLeastOneResultSetVisitor;
import com.porpoise.dao.database.visitors.FirstResultToMapVisitor;
import com.porpoise.dao.database.visitors.SingleScalarResultVisitor;
import com.porpoise.dao.database.visitors.Visitors;
import com.porpoise.dao.query.CustomQueryImpl;
import com.porpoise.dao.query.ICustomQuery;
import com.porpoise.dao.query.UserQueryParameter;

/**
 * DefaultCustomQueryService created: Jul 27, 2010
 * 
 * @author Aaron
 */
public class DefaultCustomQueryService implements ICustomQueryService
{
    private final DbConnectionFactory factory;

    static class QueryLoader extends AtLeastOneResultSetVisitor
    {
        private final List<ICustomQuery> customQueries = new ArrayList<ICustomQuery>();

        @Override
        public boolean onResultSet(final ResultSet resultSet) throws SQLException
        {
            int columnIndex = 1;
            final long idValue = resultSet.getLong(columnIndex++);
            final Long id = resultSet.wasNull() ? null : Long.valueOf(idValue);
            final String name = resultSet.getString(columnIndex++);
            final String desc = resultSet.getString(columnIndex++);
            final String sql = resultSet.getString(columnIndex++);

            final CustomQueryImpl customQuery = new CustomQueryImpl(id, name, desc, sql);

            this.customQueries.add(customQuery);

            return true;
        }

        /**
         * @return the customQueries
         */
        public List<ICustomQuery> getCustomQueries()
        {
            return this.customQueries;
        }
    }

    static class QueryParamLoader extends AbstractResultSetVisitor
    {
        private final List<UserQueryParameter> queryParams = new ArrayList<UserQueryParameter>();

        @Override
        public boolean onResultSet(final ResultSet resultSet) throws SQLException
        {
            int columnIndex = 1;
            final String key = resultSet.getString(columnIndex++);
            final String name = resultSet.getString(columnIndex++);
            final String defaultValue = resultSet.getString(columnIndex++);

            final UserQueryParameter param = new UserQueryParameter(key, defaultValue);
            param.setFriendlyName(name);

            this.queryParams.add(param);

            return true;
        }

        /**
         * @return the customQueries
         */
        public List<UserQueryParameter> getParams()
        {
            return this.queryParams;
        }
    }

    /**
     * Constructor
     * 
     * @param connectionFactory
     */
    public DefaultCustomQueryService(final DbConnectionFactory connectionFactory)
    {
        this.factory = Preconditions.checkNotNull(connectionFactory);
    }

    /**
     * delete the custom query
     * 
     * @param customQuery
     *            The query to delete
     */
    public void delete(final ICustomQuery customQuery)
    {
        if (customQuery == null)
        {
            return;
        }
        final IDbTransaction transaction = this.factory.startNewTransaction();
        try
        {
            transaction.executeUpdate("DELETE FROM CUSTOM_QUERIES WHERE ID=?", customQuery.getId());
            transaction.executeUpdate("DELETE FROM QUERY_PARAMETERS WHERE QUERY_ID=?", customQuery.getId());
            transaction.commit();
        }
        finally
        {
            transaction.close();
        }
    }

    /**
     * get the query with the given name
     * 
     * @param id
     *            The id of the custom query to fetch
     * @return the query
     */
    public ICustomQuery get(final Long id)
    {
        final IDbTransaction transaction = this.factory.startNewTransaction();

        CustomQueryImpl customQuery;
        try
        {
            customQuery = get(transaction, id);
        }
        finally
        {
            transaction.close();
        }

        return customQuery;
    }

    private CustomQueryImpl get(final IDbTransaction transaction, final Long id)
    {
        CustomQueryImpl customQuery;
        final QueryLoader queryLoader = transaction.executeQuery(new QueryLoader(),
                "SELECT ID, NAME, DESCRIPTION, QUERY_SQL FROM CUSTOM_QUERIES WHERE ID=?", id);

        final QueryParamLoader paramLoader = transaction.executeQuery(new QueryParamLoader(),
                "SELECT PARAM_KEY, PARAM_NAME, DEFAULT_VALUE FROM QUERY_PARAMETERS WHERE QUERY_ID=?", id);

        final List<ICustomQuery> customQueries = queryLoader.getCustomQueries();
        if (customQueries.isEmpty())
        {
            customQuery = null;
        }
        else
        {
            customQuery = (CustomQueryImpl) customQueries.get(0);
            customQuery.setUserParameters(paramLoader.getParams());
        }
        return customQuery;
    }

    /**
     * list all queries
     * 
     * @see eas.ter.query.service.ICustomQueryService#list()
     */
    public Collection<ICustomQuery> list()
    {
        final List<ICustomQuery> customQueries = new ArrayList<ICustomQuery>();
        final IDbTransaction transaction = this.factory.startNewTransaction();
        try
        {
            transaction.executeQuery(new AbstractResultSetVisitor() {
                @Override
                @SuppressWarnings("synthetic-access")
                public boolean onResultSet(final ResultSet resultSet) throws SQLException
                {
                    final long id = resultSet.getLong(1);
                    final CustomQueryImpl loadedQuery = get(transaction, Long.valueOf(id));
                    customQueries.add(loadedQuery);
                    return true;
                }
            }, "SELECT ID FROM CUSTOM_QUERIES");
        }
        finally
        {
            transaction.close();
        }

        return customQueries;
    }

    /**
     * save the query
     * 
     * @param customQuery
     *            the query to save
     */
    public void save(final ICustomQuery customQuery)
    {
        IDbTransaction transaction = null;

        try
        {
            transaction = this.factory.startNewTransaction();

            boolean create = true;

            Long id = null;

            if (customQuery.getId() != null)
            {
                final SingleScalarResultVisitor visitor = transaction.executeQuery(Visitors.scalarVisitor(),
                        "SELECT COUNT(*) FROM CUSTOM_QUERIES WHERE ID=?", customQuery.getId());
                id = visitor.getLong();
                create = id.intValue() > 0;
            }

            if (create)
            {
                create(transaction, customQuery);
            }
            else
            {
                update(transaction, customQuery);
            }

            transaction.commit();
        }
        finally
        {
            this.factory.close(transaction);
        }
    }

    private void create(final IDbTransaction transaction, final ICustomQuery customQuery)
    {
        transaction.executeUpdate("INSERT INTO CUSTOM_QUERIES(NAME, DESCRIPTION, QUERY_SQL) VALUES (?,?,?)", customQuery.getName(),
                customQuery.getDescription(), customQuery.getQuerySql());

        final FirstResultToMapVisitor visitor = transaction.executeQuery(Visitors.firstResultVisitor(),
                "SELECT IDENTITY_VAL_LOCAL() FROM CUSTOM_QUERIES");
        final Object next = visitor.getResultRow().values().iterator().next();
        final Long id = Long.valueOf(next.toString());
        customQuery.setId(id);
        insertUserParameters(transaction, customQuery);
    }

    /**
     * update the custom query
     * 
     * @param transaction
     *            the transaction with which to update the custom query
     * @param customQuery
     *            the custom query to update
     */
    private void update(final IDbTransaction transaction, final ICustomQuery customQuery)
    {
        transaction.executeUpdate(//
                "UPDATE CUSTOM_QUERIES SET DESCRIPTION=?, QUERY_SQL=?, NAME=? WHERE ID=?",//
                customQuery.getDescription(),// 
                customQuery.getQuerySql(), //
                customQuery.getName(),//
                customQuery.getId()//
                );

        transaction.executeUpdate("DELETE FROM QUERY_PARAMETERS WHERE QUERY_ID=?", customQuery.getId());

        insertUserParameters(transaction, customQuery);
    }

    /**
     * insert custom query parameters
     * 
     * @param transaction
     *            the transaction with which to create the custom query
     * @param customQuery
     *            the custom query to insert
     */
    private void insertUserParameters(final IDbTransaction transaction, final ICustomQuery customQuery)
    {
        for (final UserQueryParameter param : customQuery)
        {
            transaction.executeUpdate("INSERT INTO QUERY_PARAMETERS(QUERY_ID, PARAM_KEY, PARAM_NAME, DEFAULT_VALUE) " + "VALUES (?,?,?,?)",// 
                    customQuery.getId(), //
                    param.getKey(), //
                    param.getFriendlyName(), //
                    param.getValue()//
                    );
        }
    }
}
