/**
 * MySqlInit.java

 */
package com.porpoise.gen.database.init;

import com.porpoise.gen.database.DbConnectionDetails;
import com.porpoise.gen.database.DbConnectionFactory;
import com.porpoise.gen.database.DbErrorCode;
import com.porpoise.gen.database.DbException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * MySqlInit
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * The MySqlInit class is kept separate from the {@link Databases} enumeration
 * so as to be more easily factored out in jars distributed without the MySQL
 * driver libraries
 *
 * <p>
 * typical connection URL=jdbc:/mysql://localhost
 * </p>
 */
enum MySqlInit
{
    ;
    private static final int ERROR_CODE_TABLE_ALREADY_EXISTS = 1050;

    static final DbConnectionFactory initialise(final DbConnectionFactory connectionFactory,
            final String connectionName, final DbConnectionDetails details)
    {
        final String databaseName = connectionName;

        final MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(details.getUser());
        dataSource.setCacheResultSetMetadata(true);
        dataSource.setCachePrepStmts(true);
        dataSource.setCachePreparedStatements(true);
        dataSource.setCacheCallableStatements(true);
        dataSource.setCacheCallableStmts(true);
        dataSource.setPassword(details.getPassword());
        dataSource.setDatabaseName(databaseName);
        dataSource.setUrl(details.getUrl());

        connectionFactory.registerDatasource(connectionName, dataSource);

        return connectionFactory;
    }


    /**
     * @param exp
     *            the exception for which to create a DbErrorCode
     * @return the db error code
     */
    public static DbErrorCode translateErrorCode(final DbException exp)
    {
        return new DbErrorCode(exp) {
            @Override
            public boolean isTableAlreadyExistsException()
            {
                final int errorCode = getDbException().getCause()
                        .getErrorCode();
                final String message = getDbException().getCause().getMessage();

                return ERROR_CODE_TABLE_ALREADY_EXISTS == errorCode
                        && message.contains("Table '")
                        && message.contains("' already exists");
            }

        };
    }

}
