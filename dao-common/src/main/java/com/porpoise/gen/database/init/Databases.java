/**
 * Databases.java

 */
package com.porpoise.gen.database.init;

import com.porpoise.gen.database.DbException;
import com.porpoise.gen.database.DbConfiguration;
import com.porpoise.gen.database.DbErrorCode;
import com.porpoise.gen.database.DbConnectionDetails;
import com.porpoise.gen.database.DbConnectionFactory;
import com.porpoise.gen.logging.AaronLog;


/**
 * Databases
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 *
 * Databases provides a way to access common {@link IDatabaseVendor}
 * implementations
 */
public enum Databases implements IDatabaseVendor
{
    /**
     * MY SQL
     */
    MYSQL
    {
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
        public DbConnectionFactory init(final DbConnectionFactory connectionFactory, final String connectionName,
                final DbConnectionDetails details)
        {
            return DerbyInit.initialise(connectionFactory, connectionName, details);
        }
    },

    /**
     * ORACLE
     */
    ORACLE
    {
        public DbConnectionFactory init(final DbConnectionFactory connectionFactory, final String connectionName,
                final DbConnectionDetails details)
        {
            return OracleInit.initialise(connectionFactory, connectionName, details);
        }
    };

    static final AaronLog LOG = AaronLog.getLog(Databases.class);

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
        LOG.info(String.format("Initialising %s database...", name()));
        return init(DbConnectionFactory.getInstance(), IDatabaseVendor.DEFAULT_CONNECTION_NAME, DbConfiguration
                .getConnectionDetails());
    }

    /**
     * initialise a database connection factory based on the database
     * configuration found on the classpath
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
     * convert the SQL exception into a database error code class, which is a
     * vendor agnostic way to determine common database errors
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
}
