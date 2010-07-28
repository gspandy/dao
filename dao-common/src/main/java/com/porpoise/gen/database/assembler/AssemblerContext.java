/**
 * AssemblerContext.java

 */
package com.porpoise.gen.database.assembler;

import java.util.Map;
import java.util.HashMap;

import com.porpoise.gen.cache.ObjectCache;
import com.porpoise.gen.database.IDbTransaction;
import com.porpoise.gen.util.Validation;
import com.porpoise.gen.metadata.IMetaDataField;

/**
 * AssemblerContext
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * 
 * <p>
 * A context containing relevant data for persisting objects.
 * </p>
 */
public class AssemblerContext
{
    /**
     * reference key
     */
    private class Key
    {
        private final int hashCode;

        public Key(final IMetaDataField field, final Object referencedItem)
        {
            final int identityHashCode = System.identityHashCode(referencedItem);
            final int fieldHashCode = field.hashCode();
            this.hashCode = identityHashCode ^ 17 + fieldHashCode * 31;
        }

        @Override
        public int hashCode()
        {
            return this.hashCode;
        }

        @Override
        public boolean equals(final Object other)
        {
            if (this == other)
            {
                return true;
            }
            if (other == null)
            {
                return false;
            }
            return other instanceof Key && other.hashCode() == this.hashCode;
        }
    }

    private final Map<Key, Object> referenceMap;
    private final IDbTransaction transaction;
    private final ObjectCache    objectCache;

    /**
     * @param txn
     *            the database transaction
     * @param cache
     *            an object cache
     */
    public AssemblerContext(final IDbTransaction txn, final ObjectCache cache)
    {
        this.transaction = Validation.notNull(txn);
        this.objectCache = Validation.notNull(cache);
        this.referenceMap = new HashMap<Key, Object>();
    }

    /**
     * @return the transaction for this context
     */
    public IDbTransaction getTransaction()
    {
        return this.transaction;
    }

    /**
     * @return the object cache for this context
     */
    public ObjectCache getObjectCache()
    {
        return this.objectCache;
    }

    /**
     * @param <T>
     * @param field
     *            the field which references the referenced item
     * @param referencedItem
     * @return the object referring to this object
     */
    @SuppressWarnings("unchecked")
    public <T> T getReference(final IMetaDataField field,
            final Object referencedItem)
    {
        final Object referencedObject = this.referenceMap.get(new Key(field,
                referencedItem));
        return (T) referencedObject;
    }

    /**
     * @param field
     * @param referencedItem
     * @param referencingObject
     */
    public void registerReference(final IMetaDataField field,
            final Object referencedItem,
            final Object referencingObject)
    {
        this.referenceMap.put(new Key(field, referencedItem), referencingObject);
    }

}
