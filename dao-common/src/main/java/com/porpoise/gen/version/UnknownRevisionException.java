/**
 * UnknownRevisionException.java

 */
package com.porpoise.gen.version;

/**
 * UnknownRevisionException
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * exception thrown when an object key is operated on which is not under version
 * control
 */
public class UnknownRevisionException extends Exception
{
    /**
     * 
     */
    private static final long    serialVersionUID = 1L;
    private final IVersionKey key;
    private final int            revision;

    /**
     * @param key
     * @param revision
     */
    UnknownRevisionException(final IVersionKey key, final int revision)
    {
        this.key = key;
        this.revision = revision;
    }

    /**
     * @return the key which is not under the repository
     */
    public IVersionKey getKey()
    {
        return this.key;
    }

    /**
     * @return the revision attempted to be retrieved
     */
    public int getRevision()
    {
        return this.revision;
    }
}
