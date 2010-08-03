package com.porpoise.dao.database.init;

import com.porpoise.dao.database.DbConfiguration;
import com.porpoise.dao.database.DbConnectionDetails;
import com.porpoise.dao.database.DbConnectionFactory;
import com.porpoise.dao.database.DbErrorCode;
import com.porpoise.dao.database.DbException;

public enum Databases implements IDatabaseVendor
{
    /**
     * MY SQL
     */
    MYSQL
    {
        @Override
        public DbConnectionFactory init(final DbConnectionFactory connectionFactory, final String connectionName,
                final DbConnectionDetails details)
        {
            return MySqlInit.initialise(connectionFactory, connectionName, details);
        }

        @Override
        public DbErrorCode translateErrorCode(final DbException exp)
        {
            return MySqlInit.translateErrorCode(exp);
        }
    },

    /**
     * DERBY
     */
    DERBY
    {
        @Override
        public DbConnectionFactory init(final DbConnectionFactory connectionFactory, final String connectionName,
                final DbConnectionDetails details)
        {
            return DerbyInit.initialise(connectionFactory, connectionName, details);
        }
    },

    /**
     * MS ACCESS
     */
    ACCESS
    {
        @Override
        public DbConnectionFactory init(final DbConnectionFactory connectionFactory, final String connectionName,
                final DbConnectionDetails details)
        {
            return AccessInit.initialise(connectionFactory, connectionName, details);
        }
    },

    /**
     * ORACLE
     */
    ORACLE
    {
        @Override
        public DbConnectionFactory init(final DbConnectionFactory connectionFactory, final String connectionName,
                final DbConnectionDetails details)
        {
            return OracleInit.initialise(connectionFactory, connectionName, details);
        }
    }

    ;//

    //
    // initialized instance to prevent reinitialisation
    //
    private static DbConnectionFactory initializedFactory;

    /**
     * initialise the connection factory
     * 
     * @return an initialised connection factory
     */
    protected DbConnectionFactory init()
    {
        final DbConnectionDetails connectionDetails = DbConfiguration.getConnectionDetails();
        return init(connectionDetails);
    }

    public DbConnectionFactory init(final DbConnectionDetails connectionDetails)
    {
        return init(DbConnectionFactory.getInstance(), IDatabaseVendor.DEFAULT_CONNECTION_NAME, connectionDetails);
    }

    /**
     * initialise a database connection factory based on the database configuration found on the classpath
     * 
     * @return the initialised database connection factory
     */
    public static synchronized DbConnectionFactory initFromConfiguration()
    {
        if (initializedFactory == null)
        {
            initializedFactory = getVendor().init();
        }
        return initializedFactory;
    }

    /**
     * convert the SQL exception into a database error code class, which is a vendor agnostic way to determine common database errors
     * 
     * @param exp
     *            the exception used to initialise the error code class
     * @return the DbErrorCode class for the given exception
     */
    public DbErrorCode translateErrorCode(final DbException exp)
    {
        return new DbErrorCode(exp);
    }

    /**
     * convenience method for accessing the database type
     * 
     * @return the Database vendor type as determined by the configuration
     */
    public static Databases getVendor()
    {
        return DbConfiguration.getConnectionDetails().getDatabaseVendor();
    }

    @Override
    public DbConnectionFactory init(final DbConnectionFactory connectionFactory, final String connectionName,
            final DbConnectionDetails details)
    {
        return DerbyInit.initialise(connectionFactory, connectionName, details);
    }
}