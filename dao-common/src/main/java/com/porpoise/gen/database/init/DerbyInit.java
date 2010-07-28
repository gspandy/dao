/**
 * DerbyInit.java

 */
package com.porpoise.gen.database.init;

import com.porpoise.gen.database.DbConnectionDetails;
import com.porpoise.gen.database.DbConnectionFactory;
import org.apache.derby.jdbc.EmbeddedDataSource;

/**
 * DerbyInit
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * The DerbyInit class is kept separate from the {@link Databases} enumeration
 * so as to be more easily factored out in jars distributed without the derby
 * libraries
 */
enum DerbyInit
{
    ;
    static final DbConnectionFactory initialise(final DbConnectionFactory connectionFactory,
            final String connectionName, final DbConnectionDetails details)
    {
        final String databaseName = details.getDatabaseName();
        // final Matcher matcher =
        // Pattern.compile(".*:derby:(.*);.*").matcher(details.getUrl());
        // if (matcher.matches())
        // {
        // final int groupCount = matcher.groupCount();
        // databaseName = matcher.group(groupCount);
        // }
        final EmbeddedDataSource dataSource = new EmbeddedDataSource();
        dataSource.setUser(details.getUser());
        dataSource.setCreateDatabase("create");
        dataSource.setPassword(details.getPassword());
        dataSource.setDataSourceName(connectionName);
        dataSource.setDatabaseName(databaseName);

        connectionFactory.registerDatasource(connectionName, dataSource);
        return connectionFactory;
    }

}
