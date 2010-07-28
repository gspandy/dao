/**
 * DbConnectionFactory.java

 */
package com.porpoise.gen.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;


import com.porpoise.gen.database.init.Databases;
import com.porpoise.gen.database.init.IDatabaseVendor;
import com.porpoise.gen.util.Validation;
import com.porpoise.gen.logging.AaronLog;

/**
 * DbConnectionFactory
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 * <p>
 * A DbConnectionFactory is used to register database connections.
 * {@link DataSource}, {@link Connection}, and {@link IDbTransaction}
 * objects can then be attained from this factory.
 * </p>
 * <p>
 * The preferred access to a database is via an IDbTransaction, however,
 * as that guarantees connections will be properly closed after use.
 * </p>
 * <p>
 * Also, though the DbConnectionFactory is a singleton, classes should still
 * require if possible for DbConnectionFactory instances to be passed in so as
 * not to abuse this fact.
 * </p>
 * <p>
 * A DbConnectionFactory will need to have datasources registered with it, and
 * the preferred method of doing so is via an {@link IDatabaseVendor}.
 * IDatabaseType implementations are available via the {@link Databases} object.
 * </p>
 * <p>
 * So, for example, the intended usage is:
 * </p>
 * 
 * <pre>
 * public class MyClass
 * {
 *     public static void main(String[] args)
 *     {
 *         // initialise a factory
 *         DbConnectionFactory factory = Databases.derby().init();
 *         
 *         // begin using the factory
 *         factory.startNewTransaction().executeUpdate(...).commit();
 *     }
 * }
 * </pre>
 */
public enum DbConnectionFactory
{
    /**
     * the singleton instance. As of java 1.5, the preferred singleton mechanism
     * is to use them as an enumeration
     */
    INSTANCE;

    static
    {
        Databases.initFromConfiguration();
    }

    private static final AaronLog                       LOG              = AaronLog.getLog(DbConnectionFactory.class);

    private final Collection<IDbTransaction> openTransactions = new ArrayList<IDbTransaction>();
    private final Map<String, DataSource>          datasourceByKey  = new ConcurrentHashMap<String, DataSource>();
    private String                                 defaultDatasourceKey;

    private DbConnectionFactory()
    {
        //
        // add shutdown hook to ensure all connections are closed
        //
        final Runnable shutdown = new Runnable()
        {
            public void run()
            {
                closeAllConnections();
            }
        };
        final Thread hook = new Thread(shutdown);
        Runtime.getRuntime().addShutdownHook(hook);
    }

    /**
     * close all open connections
     */
    public void closeAllConnections()
    {
        final Collection<IDbTransaction> copy = new ArrayList<IDbTransaction>(this.openTransactions);
        for (final IDbTransaction trans : copy)
        {
            try
            {
                trans.close();
            }
            catch (final Exception e)
            {
                LOG.error("Error closing transaction", e);
            }
        }
        this.openTransactions.clear();
    }

    /**
     * <p>
     * Return the singleton factory instance. This usage is discouraged,
     * however, as clients of the DbConnectionFactory should advertise their
     * dependence on it through their API.
     * </p>
     * <p>
     * For example, the former example is preferred to the latter:
     * </p>
     * 
     * <pre>
     * public void doSomething(DbConnectionFactory factory) { ... }
     * </pre>
     * 
     * <pre>
     * public void doSomething()
     * {
     *     DbConnectionFactory factory = DbConnectionFactory.getInstance();
     * }
     * </pre>
     * 
     * @return the database connection factory
     */
    public static DbConnectionFactory getInstance()
    {
        return INSTANCE;
    }

    void setDefaultDatasource(final String key)
    {
        if (!this.datasourceByKey.containsKey(key))
        {
            throw new IllegalArgumentException("No datasource registered for key " + key);
        }
        this.defaultDatasourceKey = key;
    }

    /**
     * register a datasource with the connection factory. If no default
     * datasources have been registered, then this datasource will be used as
     * the default.
     * 
     * @param key
     *            the non-null key against which to register the datasource
     * @param dataSource
     *            the non-null datasource to register
     * @return the datasource previously registered against that key or null if
     *         none were already registered.
     */
    public DataSource registerDatasource(final String key, final DataSource dataSource)
    {
        Validation.notNull(key, "the datasource key cannot be null");
        Validation.notNull(dataSource, "the datasource cannot be null");

        final DataSource oldvalue = this.datasourceByKey.put(key, dataSource);
        if (this.defaultDatasourceKey == null)
        {
            setDefaultDatasource(key);
        }
        return oldvalue;
    }

    /**
     * remove the datasource at the given key
     * 
     * @param key
     * @return the removed datasource
     */
    public DataSource removeDatasource(final String key)
    {
        if (this.defaultDatasourceKey != null && this.defaultDatasourceKey.equals(key))
        {
            this.defaultDatasourceKey = null;
        }
        DataSource removed;
        if (key == null)
        {
            removed = null;
        }
        else
        {
            removed = this.datasourceByKey.remove(key);
        }
        return removed;
    }

    /**
     * safe call to check if a datasource is registered with the given key
     * 
     * @param key
     *            the datasource key
     * @return the datasource at the given key
     */
    public boolean isDataSourceRegistered(final String key)
    {
        return this.datasourceByKey.containsKey(key);
    }

    /**
     * @param key
     *            the key for which a givne datasource is expected to be
     *            registered
     * @return the datasource for the given key
     * @throws IllegalArgumentException
     *             if no datasource exists for the given key
     */
    public DataSource getDataSource(final String key)
    {
        final DataSource dataSource = this.datasourceByKey.get(key);
        if (dataSource == null)
        {
            throw new IllegalArgumentException("No datasource exists for " + key);
        }
        return dataSource;
    }

    /**
     * @return the default data source
     */
    public DataSource getDefaultDataSource()
    {
        return this.datasourceByKey.get(this.defaultDatasourceKey);
    }

    /**
     * return a database connection from a datasource registered with the given
     * name (key)
     * 
     * @param name
     *            the key against which a datasource has been registered
     * @return the connection
     */
    public Connection getNamedConnection(final String name)
    {
        final DataSource dataSource = getDataSource(name);
        return initConnection(dataSource);
    }

    /**
     * Convenience method for attaining a database connection from the default
     * datasource, instead of having to use:
     * 
     * <pre>
     * DbConnectionFactory.getInstance().getDefaultDataSource().getNamedConnection(&quot;...&quot;);
     * </pre>
     * 
     * @return a database connection
     */
    public static Connection getDefaultConnection()
    {
        if (getInstance().defaultDatasourceKey == null)
        {
            throw new IllegalStateException(
                    "no datasources have been registered. Have you called init on an IDatabaseType?");
        }
        final DataSource defaultDataSource = getInstance().getDefaultDataSource();
        if (defaultDataSource == null)
        {
            throw new IllegalStateException(String
                    .format("no datasources have been registered by the default name '%s'.",
                            getInstance().defaultDatasourceKey));
        }
        return initConnection(defaultDataSource);
    }

    /*
     * all methods using connections should ensure they initialise them using
     */
    private static Connection initConnection(final DataSource dataSource)
    {
        Connection conn;
        try
        {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
        }
        catch (final SQLException e)
        {
            throw new DbException(e);
        }
        return conn;
    }

    /**
     * close the database connection
     * 
     * @param connection
     *            the database connection to close
     */
    public static void close(final Connection connection)
    {
        if (connection != null)
        {
            try
            {
                connection.rollback();
            }
            catch (final SQLException e)
            {
                throw new DbException(e);
            }
            finally
            {
                closeConnection(connection);
            }
        }
    }

    private static void closeConnection(final Connection connection)
    {
        try
        {
            connection.close();
        }
        catch (final SQLException e)
        {
            throw new DbException(e);
        }
    }

    /**
     * start a new transaction using the datasource registered against the given
     * key
     * 
     * @param key
     *            the non-null datasource key.
     * @return a new {@link IDbTransaction}
     */
    public IDbTransaction startNewTransaction(final String key)
    {
        final IDbTransaction transaction = new DbTransaction(this, getNamedConnection(key));
        this.openTransactions.add(transaction);
        return transaction;
    }

    /**
     * start a new transaction using the default datasource
     * 
     * @return a new {@link IDbTransaction}
     */
    public IDbTransaction startNewTransaction()
    {
        return startNewTransaction(this.defaultDatasourceKey);
    }

    /**
     * close the prepared statement
     * 
     * @param statement
     *            the statement to close
     * @throws DbException
     */
    public static void close(final PreparedStatement statement) throws DbException
    {
        if (statement != null)
        {
            try
            {
                statement.close();
            }
            catch (final SQLException e)
            {
                throw new DbException(e);
            }
        }
    }

    /**
     * close the transaction
     * 
     * @param transaction
     *            the transaction to close
     */
    public void close(final IDbTransaction transaction)
    {
        if (transaction != null)
        {
            this.openTransactions.remove(transaction);
            if (transaction instanceof DbTransaction)
            {
                final DbTransaction databaseTransaction = (DbTransaction) transaction;
                databaseTransaction.closeInternal();
            }
        }
    }

    /**
     * convenience method for single-use queries to save the user having to
     * always run queries within a try/finally block
     * 
     * @param <T>
     *            the visitor type
     * @param visitor
     *            the visitor
     * @param querySql
     *            the query sql
     * @param params
     *            the parameters
     * @return the visitor
     */
    public <T extends IResultSetVisitor> T executeQueryInSingleTransaction(final T visitor, final String querySql,
            final Object... params)
    {
        IDbTransaction trans = null;

        try
        {
            trans = startNewTransaction();
            trans.executeQuery(visitor, querySql, params);
        }
        finally
        {
            close(trans);
        }
        return visitor;
    }
}
