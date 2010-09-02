package com.porpoise.dao.database.init;

import org.apache.derby.jdbc.EmbeddedDataSource;

import com.porpoise.dao.database.DbConnectionDetails;
import com.porpoise.dao.database.DbConnectionFactory;

enum DerbyInit
{
	;// uninstantiable
	static final DbConnectionFactory initialise(final DbConnectionFactory connectionFactory,
			final String connectionName, final DbConnectionDetails details)
	{
		final String databaseName = details.getDatabaseName();
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