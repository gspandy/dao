package com.porpoise.dao.generator.gen;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.io.CharStreams;
import com.google.common.io.Files;
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
        srcDir = new File(derbyTestDir, "src/main/java");
        testDir = new File(derbyTestDir, "src/test/java");

        // derbyTestDir.deleteOnExit();
        // srcDir.deleteOnExit();
        // testDir.deleteOnExit();

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
    public static void tearDownClass() throws IOException
    {
        factory.closeAllConnections();
        if (true)
        {
            return;
        }
        Files.deleteRecursively(derbyTestDir);
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

        // generate a DAO for the table:
        final DaoContext ctxt = new DaoContext("test.pack.age", table);
        DaoGenerator.generateMainJavaSource(srcDir, ctxt);
        DaoGenerator.generateTestJavaSource(testDir, ctxt);
        DaoGenerator.generatePom("dao.test", "dao-test", "1.0.0", derbyTestDir);

        // execute the pom for the generated code - have it run the generated tests
        final Process process = Runtime.getRuntime().exec("mvn test", new String[0], derbyTestDir);
        final Readable supplier = new InputStreamReader(process.getInputStream());
        final String output = CharStreams.toString(supplier);
        System.out.println(output);
        // Assert.assertTrue(output, output.contains("SUCCESS"));

    }
}
