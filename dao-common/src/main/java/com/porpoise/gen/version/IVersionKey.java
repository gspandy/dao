/**
 * IVersionKey.java

 */
package com.porpoise.gen.version;


/**
 * IVersionKey
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * A version key - most likely a combination of a class and unique Id
 */
public interface IVersionKey 
{
    /**
     * @return the hash code for the key
     */
    abstract int hashCode();

    /**
     * @param obj
     *            the object to test equality against
     * @return true of the object equals this version key
     */
    abstract boolean equals(Object obj);
}