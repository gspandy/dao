/**
 * DbTransactionTest.java

 */
package com.porpoise.gen.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.porpoise.gen.database.metadata.DbTable;
import com.porpoise.gen.database.metadata.DbTables;
import com.porpoise.gen.database.visitors.AtLeastOneResultSetVisitor;
import com.porpoise.gen.database.visitors.CountingResultSetVisitor;
import com.porpoise.gen.database.visitors.Visitors;
import com.porpoise.gen.query.CustomQueryImpl;
import org.junit.AfterClass;
import com.porpoise.gen.test.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * DbTransactionTest
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * tests for the {@link DbTransaction} class
 */
public class DbTransactionTest extends AbstractTransactionalTest
{

    /**
     * prepare the environment for this test cases
     */
    @BeforeClass
    public final static void setupDbTransactionTest()
    {
        //
        // create test_addresses table
        //
        final DbTable addressTable = DbTables.address();
        final IDbTransaction trans = getConnectionFactory().startNewTransaction();
        try
        {
            trans.executeUpdate(addressTable.getCreateSql());
            trans.commit();
        }
        finally
        {
            getConnectionFactory().close(trans);
        }
    }

    /**
     * tear down the environment after all tests have run
     */
    @AfterClass
    public final static void tearDownDbTransactionTest()
    {
        //
        // drop test_addresses table
        //
        final IDbTransaction trans = getConnectionFactory().startNewTransaction();
        try
        {
            trans.executeUpdate("DROP TABLE TEST_ADDRESSES");
            trans.commit();
        }
        finally
        {
            getConnectionFactory().close(trans);
        }
    }

    /**
     * Test the transaction can execute an update. This test simply asserts no
     * exceptions are thrown
     */
    @Test
    public void test_executeUpdate()
    {
        final String street = "123 Main";
        final String town = "Oconto Falls";
        final String city = "Oconto";
        final String postCode = "12345";
        insertIntoAddressTable(street, town, city, postCode);

        Assert.assertTrue("An address update was executed successfully", true);
    }

    private void insertIntoAddressTable(final String street, final String town, final String city, final String postCode)
    {
        getTransaction().executeUpdate(
                //
                "INSERT INTO TEST_ADDRESSES (STREET, TOWN, CITY, POST_CODE) VALUES (?, ? ,?, ?)", street, town, city,
                postCode);
    }

    /**
     * assert the onNoResults method is called when a query returns no rows
     */
    @Test
    public void test_executeQueryResultSetVisitorNoResults()
    {
        final CustomQueryImpl query = new CustomQueryImpl(
                "SELECT * FROM TEST_ADDRESSES WHERE STREET=:invalid AND STREET != :invalid").setUserParameter(
                ":invalid", "any value");

        final AtomicBoolean onNoResultsCalled = new AtomicBoolean(false);

        final IResultSetVisitor noResultVisitor = new IResultSetVisitor()
        {
            public void onNoResults()
            {
                final boolean onNoResultsSetForTheFirstTime = onNoResultsCalled.compareAndSet(false, true);
                Assert.assertTrue(onNoResultsSetForTheFirstTime);
            }

            public boolean onResultSet(@SuppressWarnings("unused") final ResultSet resultSet) throws SQLException
            {
                Assert.fail("on result set should not be called");
                return true;
            }
        };
        final CountingResultSetVisitor maxResultVisitor = Visitors.maxResultsVisitor(0);
        final IResultSetVisitor compositeVisitor = Visitors.compositeVisitor(maxResultVisitor, noResultVisitor);

        query.executeQuery(getTransaction(), compositeVisitor);

        //
        // assert onNoResults was called and no rows were returned
        //
        final int count = maxResultVisitor.getCount();
        Assert.assertEquals(0, count);
        Assert.assertTrue(onNoResultsCalled.get());
    }

    /**
     * Test a DbTransaction can execute a result set visitor
     */
    @Test
    public void test_executeQueryResultSetVisitor()
    {
        //
        // create an entry which we can query
        //
        final String town = "Oconto Falls";
        final String city = "Oconto";
        final String postCode = "12345";
        final String street = "123 Main";

        insertIntoAddressTable(street, town, city, postCode);

        //
        // create a result set visitor to retrieve the inserted values
        //
        final IResultSetVisitor testVisitor = new AtLeastOneResultSetVisitor()
        {
            @Override
            public boolean onResultSet(final ResultSet resultSet) throws SQLException
            {
                int columnIndex = 1;
                final String rsStreet = resultSet.getString(columnIndex++);
                final String rsCity = resultSet.getString(columnIndex++);
                final String rsTown = resultSet.getString(columnIndex++);
                final String rsPostCode = resultSet.getString(columnIndex++);

                Assert.assertEquals(street, rsStreet);
                Assert.assertEquals(city, rsCity);
                Assert.assertEquals(town, rsTown);
                Assert.assertEquals(postCode, rsPostCode);
                return true;
            }
        };

        //
        // ensure only one row is returned
        //
        final CountingResultSetVisitor maxResultsVisitor = Visitors.maxResultsVisitor(1);
        final IResultSetVisitor visitor = Visitors.compositeVisitor(//
                maxResultsVisitor,// 
                testVisitor);
        final String sql = "SELECT STREET, CITY, TOWN, POST_CODE FROM TEST_ADDRESSES WHERE STREET=?";
        getTransaction().executeQuery(visitor, sql, street);

        Assert.assertEquals(1, maxResultsVisitor.getCount());
    }
}
