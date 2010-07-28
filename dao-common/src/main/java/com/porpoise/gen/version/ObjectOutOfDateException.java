/**
 * ObjectOutOfDateException.java

 */
package com.porpoise.gen.version;

/**
 * ObjectOutOfDateException
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * Thrown when an out-of-date object is attempted to be submitted to the
 * repository
 */
public class ObjectOutOfDateException extends Exception
{

    /**
     * 
     */
    private static final long    serialVersionUID = 1L;
    private final IVersionKey    key;
    private final int            latestVersion;
    private final int            currentVersion;

    /**
     * @param versionKey the key to the object under version control
     * @param currentVersionValue the out-of-date version
     * @param latestVersion the latest version
     */
    public ObjectOutOfDateException(final IVersionKey versionKey,
                             final int currentVersionValue,
                             final int latestVersion)
    {
        this.key = versionKey;
        this.currentVersion = currentVersionValue;
        this.latestVersion = latestVersion;
    }

    /**
     * @return the current, out-of-date version which was attempted to be
     *         submitted
     */
    public int getCurrentVersion()
    {
        return this.currentVersion;
    }

    /**
     * @return the key to the object under version control
     */
    public IVersionKey getKey()
    {
        return this.key;
    }

    /**
     * @return the actual latest version for the repository
     */
    public int getLatestVersion()
    {
        return this.latestVersion;
    }

}
