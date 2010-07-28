/**
 * DefaultCustomQueryServiceTest.java

 */
package com.porpoise.gen.query.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.AfterClass;
import com.porpoise.gen.test.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.porpoise.gen.database.AbstractTransactionalTest;
import com.porpoise.gen.database.DbConnectionFactory;
import com.porpoise.gen.database.DbException;
import com.porpoise.gen.database.init.Databases;
import com.porpoise.gen.database.tools.DbScriptExecutor;
import com.porpoise.gen.logging.AaronLog;
import com.porpoise.gen.query.CustomQueryImpl;
import com.porpoise.gen.query.ICustomQuery;
import com.porpoise.gen.query.UserQueryParameter;
import com.porpoise.gen.util.Resources;

/**
 * DefaultCustomQueryServiceTest
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 *
 * Test the custom query service
 */
public class DefaultCustomQueryServiceTest extends AbstractTransactionalTest
{
    private static final AaronLog          LOG = AaronLog.getLog(DefaultCustomQueryServiceTest.class);

        private DefaultCustomQueryService  service;

    /**
     * prepare the test
     * 
     * @throws IOException
     */
    @BeforeClass
    public static final void setupTests() throws IOException
    {
        final Databases databaseVendor = Databases.getVendor();
        LOG.info("setting up tests for " + databaseVendor);
        if (Databases.DERBY.equals(databaseVendor))
        {
            LOG.info("creating the database");

            try
            {
                final File createScript = Resources.findFileOnClasspath("database/derby/create/tables/01_createCustomQueries.sql");
                final File createTablesDir = createScript.getParentFile();
                DbScriptExecutor.execute(createTablesDir);
            }
            catch (final DbException e)
            {
                if (!e.getErrorCode().isTableAlreadyExistsException())
                {
                    throw e;
                }
            }
        }
        else
        {
            throw new IllegalArgumentException("Database not supported :" + databaseVendor);
        }
    }

    /**
     * clean up the test
     */
    @AfterClass
    public static final void tearDownClass()
    {
        final String dir = System.getProperty("user.dir");
        final File f = new File(dir, "db-test");
        if (f.exists())
        {
            LOG.info(String.format("deleting %s on exit", f.getPath()));
            f.delete();
            f.deleteOnExit();
        }
        else
        {
            LOG.info(String.format("Couldn't find %s to delete", f.getPath()));
        }
    }

    /**
     * setup the test
     */
    @Before
    public void before()
    {
        this.service = new DefaultCustomQueryService(DbConnectionFactory.getInstance());
    }

    /**
     * test saving a custom query service with no parameters
     */
    @Test
    public void test_saveAndReadBackNoParamQuery()
    {
        final ICustomQuery customQuery = new CustomQueryImpl("test save new",
                "test description",
                "SELECT * FROM CUSTOM_QUERIES");
        try
        {
            this.service.save(customQuery);

            final ICustomQuery reloaded = this.service.get(customQuery.getId());
            Assert.assertEquals(customQuery, reloaded);
        }
        finally
        {
            //
            // clean up after ourselves
            //
            this.service.delete(customQuery);
        }
    }

    /**
     * test saving a custom query service with parameters.
     */
    @Test
    public void test_saveAndReadBackListedQueries()
    {
        final ICustomQuery q1 = new CustomQueryImpl("test save new",
                "test description",
                "SELECT ID FROM SOMEWHERE WITH NAME>:name AND GAMBLE=:g").setUserParameter(":name",
                "some name")
                .setUserParameter(":g", "office");

        final ICustomQuery q2 = new CustomQueryImpl("q2",
                "",
                "SELECT count(*) FROM SOMEWHERE WITH NAME>:name ").setUserParameter(":name",
                "some name");

        final CustomQueryImpl q3 = new CustomQueryImpl("q3 name",
                "double desc",
                "SELECT one=:one two>=:two three != :three one < :one").setUserParameter(":one",
                "won")
                .setUserParameter(":two", "too")
                .setUserParameter(":three", "3");

        final ICustomQuery[] queries = new ICustomQuery[] { q1, q2, q3 };

        try
        {
            for (final ICustomQuery q : queries)
            {
                this.service.save(q);
            }

            final Collection<ICustomQuery> savedQueriesList = this.service.list();

            Assert.assertEquals(3, savedQueriesList.size());

            LOG.info(String.format("making assertions in:%n%s%n",
                    savedQueriesList));
            for (final ICustomQuery q : queries)
            {
                LOG.info("checking for query " + q);
                Assert.assertTrue(savedQueriesList.contains(q));
            }
        }
        finally
        {
            //
            // clean up after ourselves
            //
            for (final ICustomQuery q : queries)
            {
                this.service.delete(q);
            }
        }
    }

    /**
     * test updating a custom query
     */
    @SuppressWarnings("serial")
    @Test
    public void test_update()
    {
        final CustomQueryImpl query = new CustomQueryImpl("update name",
                "update description",
                "bla bla bla this is actually a query!")//
        .setUserParameter("a", "one")
                //
                .setUserParameter("b", "two")
                //
                .setUserParameter("c", "three");

        Assert.assertNull(query.getId());
        //
        // save our query
        //
        this.service.save(query);
        Assert.assertNotNull(query.getId());

        // 
        // now update it
        //
        query.setName("an updated name");
        query.setDescription("an updated description");
        final List<UserQueryParameter> params = new ArrayList<UserQueryParameter>() {
            {//
                add(new UserQueryParameter("new", "old"));
                add(new UserQueryParameter("x", "y"));
                add(new UserQueryParameter("z", "zed"));
            }
        };

        query.setUserParameters(params);
        this.service.save(query);

        //
        // read it back
        //
        final ICustomQuery readBack = this.service.get(query.getId());
        Assert.assertEquals("an updated name", readBack.getName());
        Assert.assertEquals("an updated description", readBack.getDescription());
        for (final UserQueryParameter param : readBack)
        {
            final boolean remove = params.remove(param);
            Assert.assertTrue(String.format("Not expected: %s", param), remove);
        }
        Assert.assertTrue("No all parameters were read back", params.isEmpty());
    }


    /**
     * test the list method
     */
    @Test
    public void test_list()
    {
        final Collection<ICustomQuery> list = this.service.list();
        Assert.assertNotNull(list);
    }
}
