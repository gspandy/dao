package com.porpoise.dao.database.init;

import javax.sql.DataSource;

import com.porpoise.dao.database.DbConnectionDetails;
import com.porpoise.dao.database.DbConnectionFactory;

enum OracleInit
{
    ;

    static final DbConnectionFactory initialise(final DbConnectionFactory connectionFactory, final String connectionName,
            final DbConnectionDetails details)
    {
        DataSource dataSource;
        try
        {
            // OracleDataSource oracleDataSource;
            DataSource oracleDataSource;
            details.getDatabaseName();
            oracleDataSource = null;// new OracleDataSource();
            // oracleDataSource.setUser(details.getUser());
            //
            // oracleDataSource.setPassword(details.getPassword());
            // oracleDataSource.setDataSourceName(connectionName);
            // oracleDataSource.setDatabaseName(databaseName);
            // oracleDataSource.setURL(details.getUrl());

            // OracleConnectionCacheManager.getConnectionCacheManagerInstance();

            // final Properties driverProps = new Properties();
            // dataSource.setConnectionProperties(driverProps);

            // Enable the Oracle connection pool
            // oracleDataSource.setConnectionCachingEnabled(true);
            // oracleDataSource.setConnectionCacheName(connectionName);
            dataSource = oracleDataSource;

            // final Properties connectionCacheProps = new Properties();
            // dataSource.setConnectionCacheProperties(connectionCacheProps);
        }
        // catch (final SQLException e)
        // {
        // throw new DbException(e);
        // }

        finally
        {
        }
        connectionFactory.registerDatasource(connectionName, dataSource);
        return connectionFactory;
    }
}