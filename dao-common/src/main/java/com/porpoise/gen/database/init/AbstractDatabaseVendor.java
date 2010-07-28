/**
 * AbstractDatabaseVendor.java

 */
package com.porpoise.gen.database.init;
import com.porpoise.gen.database.DbConfiguration;import com.porpoise.gen.database.DbConnectionFactory;

/**
 * AbstractDatabaseVendor
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
abstract class AbstractDatabaseVendor implements IDatabaseVendor
{
    public DbConnectionFactory init()
    {
        return init(DbConnectionFactory.getInstance(), IDatabaseVendor.DEFAULT_CONNECTION_NAME, DbConfiguration
                .getConnectionDetails());
    }

}
