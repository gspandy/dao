/**
 * OracleInit.java

 */
package com.porpoise.gen.database.init;

import java.sql.SQLException;
import javax.sql.DataSource;

import oracle.jdbc.pool.OracleConnectionCacheManager;
import oracle.jdbc.pool.OracleDataSource;
import com.porpoise.gen.database.DbConnectionDetails;
import com.porpoise.gen.database.DbConnectionFactory;
import com.porpoise.gen.database.DbException;

/**
 * OracleInit
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 *
 * The OracleInit class is kept separate from the {@link Databases} enumeration
 * so as to be more easily factored out in jars distributed without the oracle
 * libraries
 */
enum OracleInit {
    ;
    static final DbConnectionFactory initialise(final DbConnectionFactory connectionFactory,
            final String connectionName,
            final DbConnectionDetails details)
    {
        DataSource dataSource;
        try
        {
            OracleDataSource oracleDataSource;
            //
            // see http://www.rakeshv.org/docs/oracle/jdbc3.0/oracle/jdbc/pool/
            // OracleConnectionCacheManager.html
            //
            final String databaseName = details.getDatabaseName();
            oracleDataSource = new OracleDataSource();
            oracleDataSource.setUser(details.getUser());

            oracleDataSource.setPassword(details.getPassword());
            oracleDataSource.setDataSourceName(connectionName);
            oracleDataSource.setDatabaseName(databaseName);
            oracleDataSource.setURL(details.getUrl());

            OracleConnectionCacheManager.getConnectionCacheManagerInstance();

            // final Properties driverProps = new Properties();
            // dataSource.setConnectionProperties(driverProps);

            // Enable the Oracle connection pool
            oracleDataSource.setConnectionCachingEnabled(true);
            oracleDataSource.setConnectionCacheName(connectionName);
            dataSource = oracleDataSource;

            // final Properties connectionCacheProps = new Properties();
            // dataSource.setConnectionCacheProperties(connectionCacheProps);
        }
        catch (final SQLException e)
        {
            throw new DbException(e);
        }

        connectionFactory.registerDatasource(connectionName, dataSource);
        return connectionFactory;
    }
}
