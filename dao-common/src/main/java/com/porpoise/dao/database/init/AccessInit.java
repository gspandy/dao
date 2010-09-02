package com.porpoise.dao.database.init;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.porpoise.dao.database.DbConnectionDetails;
import com.porpoise.dao.database.DbConnectionFactory;

enum AccessInit
{
	;// uninstantiable
	private static final String DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";

	public static DbConnectionFactory initialise(final DbConnectionFactory connectionFactory,
			final String connectionName, final DbConnectionDetails details)
	{

		final Connection connection = createConnection(details);

		final DataSource dataSource = new DataSource()
		{

			@Override
			public <T> T unwrap(final Class<T> arg0) throws SQLException
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isWrapperFor(final Class<?> arg0) throws SQLException
			{
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setLoginTimeout(final int arg0) throws SQLException
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void setLogWriter(final PrintWriter arg0) throws SQLException
			{
				// TODO Auto-generated method stub

			}

			@Override
			public int getLoginTimeout() throws SQLException
			{
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public PrintWriter getLogWriter() throws SQLException
			{
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Connection getConnection(final String username, final String password)
					throws SQLException
			{
				return connection;
			}

			@Override
			public Connection getConnection() throws SQLException
			{
				return connection;
			}
		};
		connectionFactory.registerDatasource(connectionName, dataSource);

		return connectionFactory;
	}

	private static Connection createConnection(final DbConnectionDetails details)
	{
		try
		{
			Class.forName(DRIVER);
			final String prefix = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
			final String suffix = ";DriverID=22;READONLY=false}";

			// Initialize the JdbcOdbc Bridge Driver

			final File database = new File(details.getUrl());
			final String databaseURL = prefix + database.getAbsolutePath() + suffix;
			// databaseURL = databaseURL + ";PWD=" + details.getPassword() +";";

			final Connection c = DriverManager.getConnection(databaseURL, details.getUser(),
					details.getPassword());
			return c;
		}
		catch(final Exception e)
		{
			throw new RuntimeException(e);
		}
	}

}
