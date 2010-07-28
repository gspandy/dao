/**
 * VersionRepository.java

 */
package com.porpoise.gen.version;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * VersionRepository
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * An object version repository. The objects kept here should be immutable,
 * otherwise versioning mutable objects is a false economy -- it doesn't add
 * much value.
 */
public final class VersionRepository
{
    private final Map<IVersionKey, Map<Integer, Object>> repositoryByClass;
    private final Map<IVersionKey, Integer>              latestVersionByClass;

    private final ReadWriteLock                             lock = new ReentrantReadWriteLock();

    /**
     * default constructor
     */
    public VersionRepository()
    {
        this.repositoryByClass = new HashMap<IVersionKey, Map<Integer, Object>>();
        this.latestVersionByClass = new HashMap<IVersionKey, Integer>();
    }

    /**
     * 
     * @param <T>
     * @param key
     *            the object type
     * @param version
     *            the version
     * @return the object for the given version
     */
    public <T> T getObject(final IVersionKey key, final int version)
    {
        T objectAtVersion;
        this.lock.readLock().lock();
        try
        {
            objectAtVersion = (T)getVersionSafe(key, Integer.valueOf(version));
        }
        finally
        {
            this.lock.readLock().unlock();
        }
        return objectAtVersion;
    }

    /**
     * 
     * @param <T>
     * @param key
     *            the object type
     * @return the object for the given version
     */
    public <T> T getLatestObject(final IVersionKey key)
    {
        T objectAtVersion;
        this.lock.readLock().lock();
        try
        {
            final Integer latestVersion = this.latestVersionByClass.get(key);
            if (latestVersion == null)
            {
                objectAtVersion = null;
            }
            else
            {
                objectAtVersion = (T)getVersionSafe(key, latestVersion);
            }
        }
        finally
        {
            this.lock.readLock().unlock();
        }
        return objectAtVersion;
    }

    /**
     * @param key
     *            the object type
     * @return the latest version for an object of the given class
     * @throws NotUnderVersionControlException
     */
    public int getLatestVersion(final IVersionKey key) throws NotUnderVersionControlException
    {
        final Integer latestVersion;
        this.lock.readLock().lock();
        try
        {
            latestVersion = this.latestVersionByClass.get(key);
            if (latestVersion == null)
            {
                throw new NotUnderVersionControlException(key);
            }
        }
        finally
        {
            this.lock.readLock().unlock();
        }
        return latestVersion.intValue();
    }

    /**
     * @param key
     *            the object type
     * @return true if the key contained in this repository
     */
    public boolean contains(final IVersionKey key)
    {
        boolean containsKey;
        this.lock.readLock().lock();
        try
        {
            containsKey = this.latestVersionByClass.containsKey(key);
        }
        finally
        {
            this.lock.readLock().unlock();
        }
        return containsKey;
    }

    /**
     * @param key
     * @return the latest version
     */
    public int submitObject(final IVersionSupplier<?> key)
    {
        return submitObject(key, key.get());
    }

    /**
     * @param key
     * @param obj
     *            the object to submit to the repository
     * @return the version of the submitted object
     */
    public int submitObject(final IVersionKey key, final Object obj)
    {
        if (obj == null)
        {
            return -1;
        }

        Integer latestVersion;
        this.lock.writeLock().lock();
        try
        {
            latestVersion = submitObjectSafe(key, obj);
        }
        finally
        {
            this.lock.writeLock().unlock();
        }
        return latestVersion.intValue();
    }

    /**
     * Submit a new object to the repository, updating the repository and the
     * latest version. This should only be called when safely locked by the
     * read/write lock.
     * 
     * @param obj
     *            the object to submit
     * @return the latest version.
     */
    private Integer submitObjectSafe(final IVersionKey key, final Object obj)
    {
        Integer latestVersion;
        Map<Integer, Object> objectByVersion;
        latestVersion = this.latestVersionByClass.get(key);
        if (latestVersion == null)
        {
            latestVersion = Integer.valueOf(1);
            objectByVersion = new HashMap<Integer, Object>();
            // no version exists, so no repository should either
            final Map<Integer, Object> oldRepoForClass = this.repositoryByClass.put(key,
                    objectByVersion);
            assert oldRepoForClass == null;
        }
        else
        {
            latestVersion = Integer.valueOf(latestVersion.intValue() + 1);
            objectByVersion = this.repositoryByClass.get(key);
            assert objectByVersion != null;
        }

        //
        // update the latest version AND the object
        //
        this.latestVersionByClass.put(key, latestVersion);
        objectByVersion.put(latestVersion, obj);
        return latestVersion;
    }

    /**
     * @param <T>
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T> T getVersionSafe(final IVersionKey key, final Integer version)
    {
        T objectAtVersion = null;
        final Map<Integer, ?> objectByVersion = this.repositoryByClass.get(key);
        if (objectByVersion != null)
        {
            objectAtVersion = (T) objectByVersion.get(version);
        }
        return objectAtVersion;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        this.lock.readLock().lock();
        try
        {
            return toStringSafe();
        }
        finally
        {
            this.lock.readLock().unlock();
        }
    }

    private String toStringSafe()
    {
        final String newLine = String.format("%n");
        final StringBuilder builder = new StringBuilder();
        for (final Entry<IVersionKey, Map<Integer, Object>> entry : this.repositoryByClass.entrySet())
        {
            builder.append("Key [").append(entry.getKey()).append("]");
            builder.append(newLine);
            appendEntry(builder, entry.getValue(), newLine);
            builder.append(newLine);
        }
        return builder.toString();
    }

    private void appendEntry(final StringBuilder builder,
            final Map<Integer, Object> entry,
            final String newLine)
    {
        for (final Entry<Integer, Object> version : entry.entrySet())
        {
            builder.append("\t\tVersion ")
                    .append(version.getKey())
                    .append(" => ")
                    .append(version.getValue());
            builder.append(newLine);
        }
    }
}
