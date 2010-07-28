/**
 * VersionControl.java

 */
package com.porpoise.gen.version;

import java.util.HashMap;
import java.util.Map;

/**
 * VersionControl
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * <p>
 * VersionControl keeps track of versioned instances. Old versions are removed
 * when they are no longer referenced, either by being submitted or reverted.
 * </p>
 * <p>
 * The idea is that all mutable objects are checked against the version control.
 * If an object is checked out at a given version but submitted at anything
 * other than the top revision, an ObjectOutOfDateException is thrown
 * </p>
 * 
 */
public class VersionControl
{
    private final VersionRepository                          versionRespository;

    private final Map<IVersionKey, Map<Integer, Integer>> versionToCountByRevisionNumber;

    /**
     * default constructor
     */
    public VersionControl()
    {
        this(new VersionRepository());
    }

    /**
     * @param repo
     */
    public VersionControl(final VersionRepository repo)
    {
        this.versionRespository = repo;
        this.versionToCountByRevisionNumber = new HashMap<IVersionKey, Map<Integer, Integer>>();
    }

    /**
     * submit the object to the repository, incrementing the version number
     * 
     * @param <T>
     * @param key
     * @param object
     * @param revision
     * @return the latest version after the submit
     * @throws ObjectOutOfDateException
     */
    public <T> int submit(final IVersionKey key,
            final Object object,
            final int revision) throws ObjectOutOfDateException
    {
        int latestVersion;
        if (this.versionRespository.contains(key))
        {
            try
            {
                latestVersion = this.versionRespository.getLatestVersion(key);
            }
            catch (final NotUnderVersionControlException e)
            {
                throw new RuntimeException(e);
            }
        }
        else
        {
            latestVersion = 0;
        }

        if (revision != latestVersion)
        {
            throw new ObjectOutOfDateException(key, revision, latestVersion);
        }

        final int newVersion = this.versionRespository.submitObject(key, object);
        assert newVersion == latestVersion + 1;

        //
        // special case - the first object added will not have been checked out
        //
        if (latestVersion != 0)
        {
            try
            {
                decRevisionCount(key, latestVersion);
            }
            catch (final UnknownRevisionException e)
            {
                // this should never happen
                throw new RuntimeException(e);
            }
        }
        return newVersion;
    }

    /**
     * submit the object to the repository, incrementing the version number
     * 
     * @param <T>
     * @param version
     * @param revision
     * @return the latest version after the submit
     * @throws ObjectOutOfDateException
     */
    public <T> int submit(final IVersionSupplier<T> version, final int revision) throws ObjectOutOfDateException
    {
        return submit(version, version.get(), revision);
    }

    /**
     * This method is intended to be called in pairs. For each
     * {@link #checkOut(IVersionKey)} of an object, either a
     * {@link #submit(IVersionSupplier, int)},
     * {@link #submit(IVersionKey, Object, int)} or
     * {@link #revert(IVersionKey, int)} call must be made.
     * 
     * @param <T>
     * @param key
     *            the object identifying the object to revert
     * @param revision
     *            the revision of the file to revert
     * @return the number of checked out objects remaining for the given
     *         revision
     * @throws UnknownRevisionException
     */
    public <T> int revert(final IVersionKey key, final int revision) throws UnknownRevisionException
    {
        return decRevisionCount(key, revision);
    }

    /**
     * @param <T>
     * @param key
     * @param revision
     * @return
     * @throws UnknownRevisionException
     */
    private <T> int decRevisionCount(final IVersionKey key,
            final int revision) throws UnknownRevisionException
    {
        final Map<Integer, Integer> countByRevision = this.versionToCountByRevisionNumber.get(key);
        if (countByRevision == null)
        {
            throw new UnknownRevisionException(key, revision);
        }

        final Integer revisionKey = Integer.valueOf(revision);
        final Integer count = countByRevision.remove(revisionKey);
        if (count == null)
        {
            throw new UnknownRevisionException(key, revision);
        }

        final int intCount = count.intValue() - 1;
        assert intCount >= 0;
        if (intCount == 0)
        {
            if (countByRevision.isEmpty())
            {
                //
                // clear out maps for this revision
                //
                this.versionToCountByRevisionNumber.remove(key);
            }
        }
        else
        {
            countByRevision.put(revisionKey, Integer.valueOf(intCount));
        }
        return intCount;
    }

    /**
     * @param key
     *            the object key
     * @return the latest version for the given object represented by the key
     * @throws NotUnderVersionControlException
     */
    public int getLatestVersion(final IVersionKey key) throws NotUnderVersionControlException
    {
        return this.versionRespository.getLatestVersion(key);
    }

    /**
     * @param key
     * @return true if the version key is under version control
     */
    public boolean isUnderVersionControl(final IVersionKey key)
    {
        return this.versionRespository.contains(key);
    }

    /**
     * @param <T>
     * @param key
     *            the object key
     * @return the latest object for the given key
     * @throws NotUnderVersionControlException
     */
    @SuppressWarnings("unchecked")
    public <T> T checkOut(final IVersionKey key) throws NotUnderVersionControlException
    {
        final T latestObject = (T)this.versionRespository.getLatestObject(key);

        incRevisionCount(key, this.versionRespository.getLatestVersion(key));

        return latestObject;
    }

    /**
     * @param <T>
     * @param key
     */
    private <T> void incRevisionCount(final IVersionKey key,
            final int latestVersion)
    {
        Map<Integer, Integer> countByRevision = this.versionToCountByRevisionNumber.get(key);
        if (countByRevision == null)
        {
            countByRevision = new HashMap<Integer, Integer>();
            this.versionToCountByRevisionNumber.put(key, countByRevision);
        }

        final Integer latestVersionKey = Integer.valueOf(latestVersion);
        final Integer count = countByRevision.get(latestVersionKey);
        if (count == null)
        {
            countByRevision.put(latestVersionKey, Integer.valueOf(1));
        }
        else
        {
            countByRevision.put(latestVersionKey,
                    Integer.valueOf(count.intValue() + 1));
        }
    }

}
