/**
 * DbConnectionFactoryTest.java

 */
package com.porpoise.gen.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.porpoise.gen.database.init.Databases;
import com.porpoise.gen.database.init.IDatabaseVendor;
import com.porpoise.gen.logging.AaronLog;

import com.porpoise.gen.test.Assert;
import org.junit.Test;

/**
 * DbConnectionFactoryTest
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class DbConnectionFactoryTest extends AbstractDatabaseTest
{
    private static final AaronLog LOG = AaronLog.getLog(DbConnectionFactoryTest.class);

    /**
     * Test a derby connection can be made and used
     * 
     * @throws SQLException
     */
    @Test
    public void test_newDerbyConnection() throws SQLException
    {
        final IDatabaseVendor databaseInitialiser = Databases.DERBY;

        databaseInitialiser.init(DbConnectionFactory.getInstance(), "test", DbConfiguration
                .getConnectionDetails());
        Connection conn = null;
        PreparedStatement createTableStatement = null;
        try
        {
            LOG.info("getting default connection");

            conn = DbConnectionFactory.getDefaultConnection();
            Assert.assertNotNull(conn);

            final String sql = String.format("CREATE TABLE customer " + // 
                    "(First_Name char(50), " + //
                    "Last_Name char(50), " + //
                    "Address varchar(100), " + //
                    "City char(50), " + //
                    "Country char(25), " + //
                    "Birth_Date date)",// 
                    getClass().getSimpleName());

            LOG.info(String.format("Creating customer table:%n%s", sql));

            createTableStatement = conn.prepareStatement(sql);
            createTableStatement.execute();

            LOG.info("success!");
        }
        finally
        {
            try
            {
                DbConnectionFactory.close(createTableStatement);
            }
            finally
            {
                DbConnectionFactory.close(conn);
            }
        }
    }
}
