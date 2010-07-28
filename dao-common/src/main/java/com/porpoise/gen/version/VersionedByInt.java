/**
 * VersionedByInt.java

 */
package com.porpoise.gen.version;

/**
 * VersionedByInt
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * @param <T>
 * 
 */
public class VersionedByInt<T> extends VersionedById<T>
{
    /**
     * @param objValue
     * @param idValue
     */
    public VersionedByInt(final T objValue, final int idValue)
    {
        super(objValue, Long.valueOf(idValue));
    }
}
