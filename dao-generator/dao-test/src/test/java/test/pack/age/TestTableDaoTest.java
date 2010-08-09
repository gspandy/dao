
package test.pack.age;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import test.pack.age.model.TestTableDto;

import org.junit.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.porpoise.dao.database.DbConnectionDetails;
import com.porpoise.dao.database.DbConnectionFactory;
import com.porpoise.dao.database.IDbTransaction;
import com.porpoise.dao.database.init.Databases;


/**
 * Tests for the TestTableDao class
 */
public class TestTableDaoTest
{
    private static DbConnectionFactory factory;
    private IDbTransaction      transaction;

    @BeforeClass
    public static void setupClass()
    {
        final DbConnectionDetails config = new DbConnectionDetails();

        final File testDir = new File(System.getProperty("user.dir"));
        final String url = new File(testDir, "dao-gen-test").getAbsolutePath();
        config.setDatabaseName(url);
        factory = Databases.DERBY.init(config);
    }
    
    @Before
    public void setup()
    {
        transaction = factory.startNewTransaction();
    }
    
    @After
    public void tearDown()
    {
        transaction.rollback();
    }

    @AfterClass
    public static void tearDownClass()
    {
        factory.closeAllConnections();
    }

    /**
     * test the DAO can create and find an entry 
     */
    @Test
    public void test_createAndFindById()
    {
        // create an entry to find
        final TestTableDto dto = new TestTableDto(Long.valueOf(1), "LastName", "FirstName");
 
        final TestTableDao dao = new TestTableDao(); 
        dao.insert(transaction, dto);

        // find our new entry        
        final TestTableDto read = dao.findById(transaction, Long.valueOf(1));
        Assert.assertEquals(dto, read);
    }

    /**
     * test the DAO can update an entry 
     */
    @Test
    public void test_update()
    {
        // create an entry to find
        final TestTableDto dto = new TestTableDto(Long.valueOf(1), "LastName", "FirstName");
 
        final TestTableDao dao = new TestTableDao(); 
        dao.insert(transaction, dto);

        // find our new entry        
        final TestTableDto read = dao.findById(transaction, Long.valueOf(1));
        Assert.assertEquals(dto, read);
    }

}