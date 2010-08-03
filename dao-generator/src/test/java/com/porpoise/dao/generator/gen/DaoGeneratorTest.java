package com.porpoise.dao.generator.gen;

import java.io.File;
import java.io.IOException;

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
import com.porpoise.dao.generator.model.ColType;
import com.porpoise.dao.generator.model.Table;

public class DaoGeneratorTest
{
    private static DbConnectionFactory factory;
    private static File                derbyTestDir;
    private static File                srcDir;
    private static File                testDir;
    private IDbTransaction             transaction;

    @BeforeClass
    public static void setupClass()
    {
        final DbConnectionDetails details = new DbConnectionDetails();
        final String userDir = System.getProperty("user.dir");
        derbyTestDir = new File(userDir, "dao-test");
        derbyTestDir.deleteOnExit();

        srcDir = new File(userDir, "src/main/java");
        srcDir.deleteOnExit();

        testDir = new File(userDir, "src/test/java");
        testDir.deleteOnExit();

        details.setDatabaseName(new File(derbyTestDir, "dao-gen-test").getAbsolutePath());
        factory = Databases.DERBY.init(details);
    }

    @Before
    public void setup()
    {
        this.transaction = factory.startNewTransaction();
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
        srcDir.delete();
        testDir.delete();
        derbyTestDir.delete();
    }

    /**
     * Test we can generate a DAO for a simple table
     * 
     * @throws IOException
     */
    @Test
    public void test_generateDao() throws IOException
    {
        // create a table
        DbScriptExecutor.executeSQL(this.transaction,// 
                "CREATE TABLE TEST_TABLE (id int," + //
                        "LastName varchar(255)," + //
                        "FirstName varchar(255))" //
        );

        // represent the table in code:
        final Table table = new Table("TestTable");
        table.addColumn("id", false, ColType.Integer);
        table.addColumn("LastName", false, ColType.String);
        table.addColumn("FirstName", false, ColType.String);
        table.addColumn("LastName", false, ColType.Integer);

        // generate a DAO for the table:
        final DaoContext ctxt = new DaoContext("test.pack.age", table);
        DaoGenerator.generateMainJavaSource(srcDir, ctxt);
        DaoGenerator.generateTestJavaSource(testDir, ctxt);
        DaoGenerator.generatePom("dao.test", "dao-test", "1.0.0", derbyTestDir);
    }
}
