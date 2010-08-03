package com.porpoise.dao.generator.gen;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.porpoise.dao.database.DbConnectionDetails;
import com.porpoise.dao.database.DbConnectionFactory;
import com.porpoise.dao.database.IDbTransaction;
import com.porpoise.dao.database.init.Databases;
import com.porpoise.dao.database.tools.DbScriptExecutor;

public class DaoGeneratorTest
{
    private static DbConnectionFactory factory;
    private static File                derbyTestDir;
    private IDbTransaction             transaction;

    @BeforeClass
    public static void setupClass()
    {
        final DbConnectionDetails details = new DbConnectionDetails();
        final String userDir = System.getProperty("user.dir");
        derbyTestDir = new File(userDir, "dao-test");
        derbyTestDir.deleteOnExit();

        details.setDatabaseName(new File(derbyTestDir, "dao-gen-test").getAbsolutePath());
        factory = Databases.DERBY.init(details);
    }

    @Before
    public void setup()
    {
        this.transaction = this.factory.startNewTransaction();
    }

    @After
    public void tearDown()
    {
        this.transaction.close();
    }

    @AfterClass
    public static void tearDownClass()
    {
        factory.closeAllConnections();
        derbyTestDir.delete();
    }

    /**
     * Test we can generate a DAO for a simple table
     */
    @Test
    public void test_generateDao()
    {
        // create a table
        DbScriptExecutor.executeSQL(this.transaction,// 
                "CREATE TABLE TEST_TABLE (id int," + //
                        "LastName varchar(255)," + //
                        "FirstName varchar(255)," + //
                        "Address varchar(255)," + //
                        "City varchar(255))"//
        );

    }

}
