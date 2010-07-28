/**
 * IDatabaseVendor.java

 */
package com.porpoise.dao.database.init;

import com.porpoise.dao.database.DbConnectionDetails;
import com.porpoise.dao.database.DbConnectionFactory;

/**
 * IDatabaseVendor
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * An {@link IDatabaseVendor} prodes a way for a database type (e.g. derby,
 * oracle, mysql) to initialise a {@link DbConnectionFactory}.
 */
public interface IDatabaseVendor
{
    /**
     * default connection name
     */
    public static String DEFAULT_CONNECTION_NAME = "default";

    /**
     * initialise the DbConnectionFactory with a default datasource registered
     * with the given connection name.
     * 
     * @param connectionFactory
     *            The non-null connection factory to initialise
     * @param connectionName
     *            the name used to register the default datasource against
     * @param details teh connection details used to intialized teh factory
     * @return The initialised connection factory
     */
    public abstract DbConnectionFactory init(final DbConnectionFactory connectionFactory, final String connectionName,
            DbConnectionDetails details);

}