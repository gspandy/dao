package com.porpoise.dao.generator.gen;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
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
    private static File                codeGenDir;
    private static File                srcDir;
    private static File                testDir;
    private static File                derbyDir;
    private IDbTransaction             transaction;

    @BeforeClass
    public static void setupClass() throws IOException
    {
        final DbConnectionDetails details = new DbConnectionDetails();
        final String userDir = System.getProperty("user.dir");
        codeGenDir = new File(userDir, "dao-test");
        srcDir = new File(codeGenDir, "src/main/java");
        testDir = new File(codeGenDir, "src/test/java");

        derbyDir = new File(codeGenDir, "dao-gen-test");
        if (derbyDir.exists())
        {
            Files.deleteRecursively(derbyDir);
        }

        details.setDatabaseName(derbyDir.getAbsolutePath());
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
        // Files.deleteRecursively(codeGenDir);
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

        this.transaction.commit();

        // represent the table in code:
        final Table table = new Table("TEST_TABLE");
        table.addColumn("id", false, ColType.Long);
        table.addColumn("LastName", false, ColType.String);
        table.addColumn("FirstName", false, ColType.String);

        // generate a DAO for the table:
        final DaoContext ctxt = new DaoContext("test.pack.age", table);
        DaoGenerator.generateMainJavaSource(srcDir, ctxt);
        DaoGenerator.generateTestJavaSource(testDir, ctxt);
        DaoGenerator.generatePom("dao.test", "dao-test", "1.0.0", codeGenDir);

        // execute the pom for the generated code - have it run the generated tests
        final Process process = Runtime.getRuntime().exec("mvn test", new String[0], codeGenDir);
        final Readable supplier = new InputStreamReader(process.getInputStream());
        final String output = CharStreams.toString(supplier);
        System.out.println(output);
        Assert.assertTrue(output, output.contains("SUCCESS"));

    }
}
