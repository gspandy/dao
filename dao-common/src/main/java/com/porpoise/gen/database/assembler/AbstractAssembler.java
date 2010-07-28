/**
 * AbstractAssembler.java

 */
package com.porpoise.gen.database.assembler;

import com.porpoise.gen.cache.ObjectCache;
import com.porpoise.gen.database.IDbTransaction;
import com.porpoise.gen.metadata.IAssembler;
import com.porpoise.gen.util.Validation;

/**
 * AbstractAssembler
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public abstract class AbstractAssembler<K, T> implements IAssembler<K, T>
{
    private final IDbTransaction transaction;
    private final ObjectCache    cache;

    public AbstractAssembler(final IDbTransaction transaction, final ObjectCache cache)
    {
        this.transaction = Validation.notNull(transaction);
        this.cache = Validation.notNull(cache);
    }

    /**
     * @return the transaction
     */
    protected IDbTransaction getTransaction()
    {
        return this.transaction;
    }

    /**
     * @return the cache
     */
    protected ObjectCache getCache()
    {
        return this.cache;
    }

}