/**
 * DbScriptExecutor.java

 */
package com.porpoise.gen.database.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

import com.porpoise.gen.database.DbConfiguration;
import com.porpoise.gen.database.DbConnectionFactory;
import com.porpoise.gen.database.DbException;
import com.porpoise.gen.database.IDbTransaction;
import com.porpoise.gen.database.init.Databases;
import com.porpoise.gen.logging.AaronLog;
import com.porpoise.gen.util.Resources;

import org.apache.commons.io.IOUtils;

/**
 * DbScriptExecutor
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 *
 * <p>
 * The DbScriptExecutor is a tool used for executing script files.
 * </p>
 * <p>
 * It can be run as an application when given a path as an argument.
 * </p>
 * <p>
 * If the path is a directory, then all scripts in the directory will be run. If
 * the path is a file then the file will be read as text and the contents
 * executed.
 * </p>
 */
public enum DbScriptExecutor
{
    ;//uninstatiable

    
    /**
     * An exception handler
     */
    public static interface IExceptionHandler
    {
        /**
         * handle an exception
         * @param exception
         */
        void onError(DbException exception);
    }
    
    /**
     * Default exception handler
     */
    public static class DefaultExceptionHandler implements IExceptionHandler
    {
        private static DefaultExceptionHandler instance = new DefaultExceptionHandler();
        private DefaultExceptionHandler()
        {
            //singleton
        }
        
        /**
         * simply rethrow the error
         * @see DbScriptExecutor.IExceptionHandler#onError(DbException)
         */
        @Override
        public void onError(final DbException exception)
        {
            throw exception;
        }
        /**
         * @return the singleton instance
         */
        public static IExceptionHandler getInstance()
        {
            return instance;
        }
    }
    
    /**
     * This token will, if found within a script, separate a script file into
     * multiple statements.
     */
    public static final String MULTIPLE_SCRIPT_TOKEN = ";;";

    private static final AaronLog   LOG                   = AaronLog.getLog(DbScriptExecutor.class);

    /**
     * main entry point for the application. This application requires a single
     * argument, telling it the location of the script(s) to run. The location
     * may be a file or a directory.
     * 
     * @param args
     *            the command line arguments
     * @throws IOException 
     */
    public static void main(final String[] args) throws IOException
    {
        if (args.length != 1)
        {
            printUsage(System.out);
            return;
        }

        final DbConnectionFactory factory = Databases.initFromConfiguration();
        try
        {
            final File path = new File(args[0]);

            LOG.info("executing scripts found in " + path.getPath());
            final IExceptionHandler exceptionHandler = new IExceptionHandler() {

                @Override
                public void onError(final DbException exception)
                {
                    if (exception.getErrorCode().isTableAlreadyExistsException())
                    {
                        System.err.println("Ignoring as the table already exists: " + exception.getErrorCode());
                    }
                    else
                    {
                        throw exception;
                    }
                }};
            final IDbTransaction transaction = execute(path, factory.startNewTransaction(), exceptionHandler);
            LOG.info("committing...");
            transaction.commit();
        }
        finally
        {
            factory.closeAllConnections();
        }
    }

    private static void printUsage(final PrintStream out)
    {
        out.println("Usage DbScriptExecutor <path>");
        out.println("\tpath : The path to a directory or file");
        out.println("\t       If the path is a directory, then all *.sql files in that directory will be ");
        out.println("\t       run. If the path is a file then the file will be read as text and run");
        out.println("");
        out.println("The database connection details will be taken from 'database.properties' which should");
        out.println(String.format("be found on the classpath. Alternatively if the system property '%s' is set",
                DbConfiguration.SYSTEM_PROPERTY_CONFIG_LOCATION));
        out.println("then the property file denoted by the system property will be used.");

    }
    
    /**
     * execute the script
     * 
     * @param scriptFile the directory or file to execute
     * @throws IOException
     */
    public static void execute(final File scriptFile) throws IOException
    {
        execute(scriptFile, DefaultExceptionHandler.getInstance());
    }
    
    /**
     * execute the script
     * 
     * @param scriptFile the directory or file to execute
     * @param exceptionHandler the exception handler 
     * @throws IOException
     */
    public static void execute(final File scriptFile, final IExceptionHandler exceptionHandler) throws IOException
    {
        final IDbTransaction transaction = DbConnectionFactory.getInstance().startNewTransaction();
        try
        {
            execute(scriptFile, transaction, exceptionHandler);
            transaction.commit();
        }
        finally
        {
            transaction.close();
        }
    }

    /**
     * execute the script file
     * 
     * @param scriptFile
     *            the script file to execute. If it is a directory then all
     *            scripts in the directory will be executed. If it is a file it
     *            will be read as text and the contents run against the
     *            transaction
     * @param transaction
     *            the transaction used to execute the script
     * @return the transaction so this method call may be chained, aiding
     *         ease-of-use
     * @throws IOException
     */
    public static IDbTransaction execute(final File scriptFile, final IDbTransaction transaction) throws IOException
    {
        return execute( scriptFile,  transaction, DefaultExceptionHandler.getInstance());
    }

    /**
     * execute the script file
     * 
     * @param scriptFile
     *            the script file to execute. If it is a directory then all
     *            scripts in the directory will be executed. If it is a file it
     *            will be read as text and the contents run against the
     *            transaction
     * @param transaction
     *            the transaction used to execute the script
     * @param handler The exception handler
     * @return the transaction so this method call may be chained, aiding
     *         ease-of-use
     * @throws IOException
     */
    public static IDbTransaction execute(final File scriptFile, final IDbTransaction transaction, final IExceptionHandler handler) throws IOException
    {
        final IExceptionHandler exceptionHandler = handler == null ? DefaultExceptionHandler.getInstance() : handler;
        
        if (scriptFile.isDirectory())
        {
            LOG.info("%s is a directory", scriptFile.getPath());
            for (final File script : scriptFile.listFiles())
            {
                execute(script, transaction, exceptionHandler);
            }
        }
        else if (scriptFile.isFile())
        {
            if (!scriptFile.getName().endsWith(".sql"))
            {
                LOG.info("Skipping %s as it doesn't end with '.sql'", scriptFile.getName());
                return transaction;
            }
            final String sql = IOUtils.toString(new FileInputStream(scriptFile));

            //
            // check if the multiple-script separator is found in the script
            // file
            //     
            if (sql.contains(MULTIPLE_SCRIPT_TOKEN))
            {
                final String[] scripts = sql.split(MULTIPLE_SCRIPT_TOKEN);
                for (final String script : scripts)
                {
                    if (script.trim().length() > 0)
                    {
                        LOG.info("Executing snippet: %s%n%s", scriptFile.getName(), script);
                    }
                    executeSQL(transaction, exceptionHandler, script);
                }
            }
            else
            {
                LOG.info("Executing: %s%n%s", scriptFile.getName(), sql);
                executeSQL(transaction, exceptionHandler, sql);
            }
        }
        else
        {
            LOG.info("Skipping %s as it is neither a file nor a directory", scriptFile.getName());
        }

        return transaction;
    }

    /**
     * execute some sql 
     * @param transaction
     * @param exceptionHandler
     * @param script
     */
    public static void executeSQL(final IDbTransaction transaction,
            final IExceptionHandler exceptionHandler,
            final String script)
    {
        if (script.trim().length() > 0)
        {
            try
            {
                transaction.executeUpdate(script);
            }
            catch (final DbException e)
            {
                exceptionHandler.onError(e);
            }
        }
        else
        {
            LOG.warn("Ignoring blank script");
        }
    }


    /**
     * create the database tables based on the configuration 
     */
    public static void createTables()
    {
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
        catch (final IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}