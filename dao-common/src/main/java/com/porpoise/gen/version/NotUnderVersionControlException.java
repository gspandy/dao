/**
 * NotUnderVersionControlException.java

 */
package com.porpoise.gen.version;

/**
 * NotUnderVersionControlException
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * Exception thrown when no object for a given key
 */
public class NotUnderVersionControlException extends Exception
{

    /**
     * @return the key
     */
    public IVersionKey getKey()
    {
        return this.key;
    }

    /**
     * 
     */
    private static final long    serialVersionUID = 1L;
    private final IVersionKey key;

    NotUnderVersionControlException(final IVersionKey key)
    {
        this.key = key;
    }

}
