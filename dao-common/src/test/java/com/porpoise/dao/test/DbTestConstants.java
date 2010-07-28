/**
 * DbTestConstants.java

 */
package com.porpoise.dao.test;

import org.junit.Ignore;

/**
 * DbTestConstants
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * class to hold test constants uses by various unit tests
 * 
 */
@Ignore
public final class DbTestConstants
{
    // uninstantiable
    private DbTestConstants()
    {
        super();
    }

    /** */
    public static final String CONNECTION_PROPERTIES_DERBY = "derby-test.properties";
    /** */
    public static final String CONNECTION_PROPERTIES_MYSQL = "mysql-test.properties";
}
