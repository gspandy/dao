/**
 * AbstractCustomQuery.java

 */
package com.porpoise.dao.query;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * AbstractCustomQuery
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 *
 */
public abstract class AbstractCustomQuery implements ICustomQuery
{
    private final Collection<UserQueryParameter> userParameters;
    private String                               description;
    private String                               querySQL;
    private String                               name;
    private Long                                 id;

    public AbstractCustomQuery()
    {
        this.userParameters = new LinkedList<UserQueryParameter>();
    }

    public String getDescription()
    {
        return this.description;
    }

    /**
     * @return the name of this query
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @param key The user parameter key place-holder
     * @param value the value for the key
     */
    @SuppressWarnings("unchecked")
    public AbstractCustomQuery setUserParameter(final String key, final String value)
    {
        final UserQueryParameter userParam = findUserParam(key);
        userParam.setValue(value);
        return this;
    }

    /**
     * get or create a user parameter for the given key. If the key doesn't
     * begin with a ':' character one will be prepended.
     *
     * @param keyValue they key 
     * @return the non-null UserQueryParameter for the given key
     */
    protected final UserQueryParameter findUserParam(final String keyValue)
    {
        final String key = keyValue.startsWith(":") ? keyValue : ":" + keyValue;

        UserQueryParameter userParam = null;
        for (final UserQueryParameter up : getUserParametersInternal())
        {
            if (up.getKey().equals(key))
            {
                userParam = up;
                break;
            }
        }
        if (userParam == null)
        {
            userParam = new UserQueryParameter(key);
            this.userParameters.add(userParam);
        }

        return userParam;
    }

    protected Collection<UserQueryParameter> getUserParametersInternal()
    {
        return this.userParameters;
    }

    protected String getUserParameter(final String key)
    {
        if (key == null)
        {
            return null;
        }
        return findUserParam(key).getValue();
    }

    protected void clearUserParameters()
    {
        getUserParametersInternal().clear();
    }

    /**
     * @return the query SQL string
     */
    public String getQuerySql()
    {
        return this.querySQL;
    }

    /**
     * set the query description, used for display purposes only
     * 
     * @param description
     *            the description to set
     */
    // setDescription is final as it may be called within a constructor
    public final void setDescription(final String description)
    {
        this.description = description == null ? "" : description;
    }

    /**
     * set the query string
     * 
     * @param sql
     *            the querySQL to set
     */
    // setDescription is final as it may be called within a constructor
    public final void setQuerySQL(final String sql)
    {
        this.querySQL = sql == null ? "" : sql;
    }

    /**
     * setName is final as it may be called within a constructor
     * 
     * @param nameValue
     *            the name to set
     */
    public final void setName(final String nameValue)
    {
        this.name = nameValue == null ? "" : nameValue;
    }

    /**
     * setId is final as it may be called within a constructor
     * 
     * @param idValue
     *            the id  to set
     */
    public final void setId(final Long idValue)
    {
        this.id = idValue;
    }
    
    /**
     * @return the id for the query
     */
    public Long getId()
    {
        return this.id;
    }

    /**
     * @return an iterator which can iterate over all the user parameters
     */
    public Iterator<UserQueryParameter> iterator()
    {
        return this.userParameters.iterator();
    }

    /**
     * @param params
     */
    public void setUserParameters(final List<UserQueryParameter> params)
    {
        this.userParameters.clear();
        for (final UserQueryParameter param : params)
        {
            this.userParameters.add(param);
        }
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
        String retValue = "";

        retValue = "CustomQuery ( name = " + this.name + ", description = " + this.description + ", querySQL = "
                + this.querySQL + ", params = " + this.userParameters + ")";

        return retValue;
    }

}
