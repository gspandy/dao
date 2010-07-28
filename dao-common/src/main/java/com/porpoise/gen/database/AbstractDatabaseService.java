/**
 * AbstractDatabaseService.java

 */
package com.porpoise.gen.database;

import com.porpoise.gen.cache.ObjectCache;
import com.porpoise.gen.database.assembler.AssemblerContext;
import com.porpoise.gen.database.init.Databases;
import com.porpoise.gen.aaron.SessionInfo;
import com.porpoise.gen.aaron.ThreadData;

/**
 * AbstractDatabaseService
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 *
 * base class for database services, used primarily to provide convenience methods for 
 * commom database and cache operations
 */
public abstract class AbstractDatabaseService
{

    static
    {
        // ensure the database is initialised
        Databases.initFromConfiguration();
    }

    /**
     * @return the current session
     */
    protected SessionInfo getSession()
    {
        return ThreadData.getThreadData().getSession();
    }

    /**
     * @return a new object cache
     */
    protected ObjectCache newCache()
    {
        return getSession().newLocalCache();
    }
    
    /**
     * commit the work held in the context - flushing the cache and committing
     * the transaction
     *
     * @param context The context to commit
     */
    protected void commit(final AssemblerContext context)
    {
        //
        // first commit the transaction.
        // if this succeeds, update the cache
        //
        context.getTransaction().commit();

        //
        // update the local (request) cache, then the session-wide cache
        //
        context.getObjectCache().flushAll();
        getSession().getSessionCache().flushAll();
    }
}
