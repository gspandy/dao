/**
 * DbScriptExecutorTest.java

 */
package com.porpoise.gen.database.tools;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import com.porpoise.gen.database.AbstractTransactionalTest;
import com.porpoise.gen.database.DbConnectionFactory;
import com.porpoise.gen.database.IDbTransaction;
import com.porpoise.gen.database.visitors.Visitors;
import com.porpoise.gen.util.Resources;
import org.junit.Test;

/**
 * DbScriptExecutorTest
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class DbScriptExecutorTest extends AbstractTransactionalTest
{
    /**
     * test that all the scripts in a folder are executed using the
     * DbScriptExecutor
     * 
     * @throws IOException
     */
    @Test
    public void test_execScript() throws IOException
    {
        final File testScriptDir = getDatabaseTestScriptDir();
        
        Assert.assertNotNull(testScriptDir);

        //
        // execute all scripts under the given directory
        //

        final IDbTransaction transaction = DbConnectionFactory.getInstance().startNewTransaction();
        try
        {
            DbScriptExecutor.execute(testScriptDir, transaction);


            //
            // assert the preferences insert can be returned
            //
            {
                final Map<String, Object> row = transaction.executeQuery(Visitors.singleResultVisitor(),
                        "SELECT * FROM TEST_PREFERENCES").getResultRow();
                Object object = row.get("PREF_KEY");
                Assert.assertEquals("A", object);
                object = row.get("PREF_VALUE");
                Assert.assertEquals("B", object);
            }

            //
            // assert the people insert can be returned
            //
            {
                final List<Map<String, Object>> rows = transaction.executeQuery(Visitors.multiResultVisitor(),
                        "SELECT * FROM TEST_PEOPLE ORDER BY FIRST_NAME").getRows();
                Assert.assertEquals(2, rows.size());
                assertPerson(rows.get(0), "amy", "murphy");
                assertPerson(rows.get(1), "doug", "nelson");
            }
        }
        finally
        {
            transaction.close();
        }
    }

    private void assertPerson(final Map<String, Object> row, final String firstName, final String lastName)
    {
        Object object = row.get("FIRST_NAME");
        Assert.assertEquals(firstName, object);
        object = row.get("LAST_NAME");
        Assert.assertEquals(lastName, object);
    }

    private File getDatabaseTestScriptDir()
    {
        final String resourceName = "database/DbScriptExecutorTest/create-person.sql";
        return Resources.findFileOnClasspath(resourceName).getParentFile();
    }

}
