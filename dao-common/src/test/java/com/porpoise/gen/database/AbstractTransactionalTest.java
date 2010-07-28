/**
 * AbstractTransactionalTest.java

 */
package com.porpoise.gen.database;

import java.io.File;
import java.io.IOException;

import com.porpoise.gen.database.init.Databases;
import com.porpoise.gen.database.tools.DbScriptExecutor;
import com.porpoise.gen.util.Resources;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * AbstractTransactionalTest
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 *
 * <p>
 * abstract test class for any test requiring either a generic database
 * connection or, more specifically, a derby connection
 * </p>
 * <p>
 * Generally tests should not commit any data, but rather simply use the
 * connection available via {@link #getTransaction()} an allow it to rollback
 * after each test.
 * </p>
 */
public abstract class AbstractTransactionalTest extends AbstractDatabaseTest
{
    private static DbConnectionFactory connectionFactory;
    private IDbTransaction       transaction;

    /**
     * set up the database if required
     * 
     * @throws IOException
     */
    @BeforeClass
    public final static void setupDatabaseTransactionTest() throws IOException
    {
        connectionFactory = Databases.initFromConfiguration();

        // TODO - don't always regenerate the database
        try
        {
            final String path = "database/" + DbConfiguration.VENDOR.getValue();
            final File tablesDir = Resources.findFileOnClasspath(path);
            final File createTablesDir = new File(tablesDir, "create");
            DbScriptExecutor.execute(createTablesDir);
        }
        catch (final DbException exp)
        {
            if (!exp.getErrorCode().isTableAlreadyExistsException())
            {
                throw exp;
            }
        }
    }

    @Before
    public final void setupTest()
    {
        this.transaction = connectionFactory.startNewTransaction();
    }

    @After
    public final void afterTest()
    {
        this.transaction.close();
    }

    /**
     * tear down the connection factory
     */
    @AfterClass
    public static final void afterAbstractTransactionalTest()
    {
        connectionFactory.closeAllConnections();
    }

    /**
     * @return the connectionFactory
     */
    protected static DbConnectionFactory getConnectionFactory()
    {
        return connectionFactory;
    }

    /**
     * @return the transaction
     */
    protected IDbTransaction getTransaction()
    {
        return this.transaction;
    }
}
