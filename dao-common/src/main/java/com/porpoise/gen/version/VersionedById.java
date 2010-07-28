/**
 * VersionedById.java

 */
package com.porpoise.gen.version;

/**
 * VersionedById
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * 
 * @param <T>
 */
public class VersionedById<T> implements IVersionSupplier<T>
{
    private T          obj;
    private final Long id;

    /**
     * @param objValue
     * @param idValue
     */
    public VersionedById(final T objValue, final Long idValue)
    {
        this.obj = objValue;
        this.id = idValue;
        if (objValue == null)
        {
            throw new NullPointerException();
        }
        if (this.id == null)
        {
            throw new NullPointerException();
        }
    }

    /**
     * @see org.aaron.version.IVersionSupplier#get()
     */
    @Override
    public T get()
    {
        return this.obj;
    }

    /**
     * @param value
     *            the new value to set
     * @return the old value
     */
    public T set(final T value)
    {
        final T oldValue = this.obj;
        this.obj = value;
        return oldValue;
    }

    /**
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 101;
        int result = 1;
        result = prime * result + this.id.hashCode();
        result = prime * result + this.obj.getClass().hashCode();
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (object == null)
        {
            return false;
        }
        if (getClass() != object.getClass())
        {
            return false;
        }
        final VersionedById<?> other = (VersionedById<?>) object;
        return object.getClass().equals(other.obj.getClass())
                && this.id.equals(other.id);
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value
     * format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    @Override
    public String toString()
    {
        final StringBuilder retValue = new StringBuilder();

        retValue.append("Key ( ")
                .append(this.id)
                .append("::")
                .append(this.obj)
                .append(" )");

        return retValue.toString();
    }

}
