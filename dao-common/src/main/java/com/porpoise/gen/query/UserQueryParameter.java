/**
 * UserQueryParameter.java

 */
package com.porpoise.gen.query;

/**
 * UserQueryParameter
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * A UserQueryParameter is used to hold custom query parameter values. The
 * UserQueryParameter holds both a query placeholder valuee (e.g. :name) as well
 * as a user friendly value ("Name") which can be displayed.
 */
public class UserQueryParameter
{
    private final String key;
    private String       friendlyName;
    private String       value;

    /**
     * @param k
     *            a parameter with a null value
     */
    public UserQueryParameter(final String k)
    {
        this(k, null);
    }

    /**
     * @param k
     *            the query key (e.g. ':name' or 'name')
     * @param v
     *            the query value (e.g. 'aaron')
     */
    public UserQueryParameter(final String k, final String v)
    {
        this.key = k;
        this.value = v;
        this.friendlyName = this.key;
    }

    /**
     * @return the key
     */
    public String getKey()
    {
        return this.key;
    }

    /**
     * @return the friendlyName
     */
    public String getFriendlyName()
    {
        return this.friendlyName;
    }

    /**
     * @param friendlyName
     *            the friendlyName to set
     */
    public void setFriendlyName(final String friendlyName)
    {
        this.friendlyName = friendlyName;
    }

    /**
     * @return the value
     */
    public String getValue()
    {
        return this.value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(final String value)
    {
        this.value = value;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.key == null ? 0 : this.key.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final UserQueryParameter other = (UserQueryParameter) obj;
        if (this.key == null)
        {
            if (other.key != null)
            {
                return false;
            }
        }
        else if (!this.key.equals(other.key))
        {
            return false;
        }
        return true;
    }
}