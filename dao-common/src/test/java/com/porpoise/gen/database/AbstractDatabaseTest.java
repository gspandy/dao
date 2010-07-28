/**
 * AbstractDatabaseTest.java

 */
package com.porpoise.gen.database;

import com.porpoise.gen.test.DbTestConstants;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * AbstractDatabaseTest
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 *
 * Base class for any database tests. The database type is set programatically
 * in the static before method
 */
public abstract class AbstractDatabaseTest
{

    private static String oldSystemPropertyName;

    /**
     * setup the database test
     */
    @BeforeClass
    public static final void beforeDbConnectionFactoryTest()
    {
        //
        // the database vendor
        //
        final String dbVendorType = DbTestConstants.CONNECTION_PROPERTIES_DERBY;

        //
        // set the system property to control
        //
        oldSystemPropertyName = System.getProperty(DbConfiguration.SYSTEM_PROPERTY_CONFIG_LOCATION);
        System.setProperty(DbConfiguration.SYSTEM_PROPERTY_CONFIG_LOCATION, dbVendorType);
    }

    @AfterClass
    public static final void afterDbConnectionFactoryTest()
    {
        if (oldSystemPropertyName != null)
        {
            System.setProperty(DbConfiguration.SYSTEM_PROPERTY_CONFIG_LOCATION, oldSystemPropertyName);
        }
        else
        {
            System.clearProperty(DbConfiguration.SYSTEM_PROPERTY_CONFIG_LOCATION);
        }
    }

}
