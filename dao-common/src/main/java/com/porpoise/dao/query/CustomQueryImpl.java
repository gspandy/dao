/**
 * CustomQueryImpl.java
 */
package com.porpoise.dao.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Objects;
import com.porpoise.dao.database.IDbTransaction;
import com.porpoise.dao.database.IResultSetVisitor;

/**
 * CustomQueryImpl created: Jul 27, 2010
 * 
 * @author Aaron An implementation of a custom query -- a query with place-holders instead of JDBC-style questionmarks
 */
public class CustomQueryImpl extends AbstractCustomQuery implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    public CustomQueryImpl()
    {
        this("");
    }

    /**
     * @param create
     *            an unnamed custom query with the given SQL
     */
    public CustomQueryImpl(final String sql)
    {
        this("", sql);
    }

    /**
     * constructor
     * 
     * @param nm
     *            the query name
     * @param sql
     *            The query SQL
     */
    public CustomQueryImpl(final String nm, final String sql)
    {
        this(nm, nm, sql);
    }

    /**
     * Constructor
     * 
     * @param nm
     *            the query name
     * @param desc
     *            a user-friendly description which may describe this query
     * @param sql
     *            the query sql
     */
    public CustomQueryImpl(final String nm, final String desc, final String sql)
    {
        this(null, nm, nm, sql);
    }

    /**
     * Constructor
     * 
     * @param id
     *            the query id
     * @param nm
     *            the query name
     * @param desc
     *            a user-friendly description which may describe this query
     * @param sql
     *            the query sql
     */
    public CustomQueryImpl(final Long id, final String nm, final String desc, final String sql)
    {
        setId(id);
        setName(nm);
        setDescription(desc);
        setQuerySQL(sql);
    }

    @Override
    @SuppressWarnings("unchecked")
    public CustomQueryImpl setUserParameter(final String key, final String value)
    {
        return (CustomQueryImpl) super.setUserParameter(key, value);
    }

    /**
     * execute the custom query with the given transaction and query visitor
     * 
     * @see com.porpoise.dao.query.ICustomQuery#executeQuery(com.porpoise.dao.database.IDbTransaction,
     *      com.porpoise.dao.database.IResultSetVisitor)
     */
    public <T extends IResultSetVisitor> T executeQuery(final IDbTransaction transaction, final T visitor)
    {
        final List<UserQueryParameter> userParamsByOccurrence = getParameters();

        //
        // replaced query will hold our working query, replacing our
        // :placeholders with question marks
        //
        String workingReplacedQuery = getQuerySql();

        //
        // create an array of parameters to pass to the query
        //
        final Object[] parameterList = new Object[userParamsByOccurrence.size()];

        //
        // loop over the ordered parameters, building our parameterList
        //
        int index = 0;
        for (final UserQueryParameter param : userParamsByOccurrence)
        {
            workingReplacedQuery = workingReplacedQuery.replaceFirst(param.getKey(), "?");
            parameterList[index++] = param.getValue();
        }

        transaction.executeQuery(visitor, workingReplacedQuery, parameterList);

        return visitor;
    }

    /**
     * {@link #executeQuery(IDbTransaction, IResultSetVisitor)} is the preferable way to execute a custom query, though this method may be
     * used to return the calculated JDBC parameters
     * 
     * @return the JDBC parameters to be used for question-mark place-holders in a JDBC query
     */
    public List<Object> getJdbcParameters()
    {
        final List<UserQueryParameter> userParamsByOccurrence = getParameters();

        final List<Object> parameters = new ArrayList<Object>();

        for (final UserQueryParameter param : userParamsByOccurrence)
        {
            parameters.add(param.getValue());
        }

        return parameters;
    }

    /**
     * {@link #executeQuery(IDbTransaction, IResultSetVisitor)} is the preferable way to execute a custom query, though this method may be
     * used to return a JDBC query using questionmarks instead of &quot;:placeholder&quot; style tokens
     * 
     * @return the JDBC query with question-marks for place-holders
     */
    public String getJdbcSql()
    {
        final List<UserQueryParameter> userParamsByOccurrence = getParameters();

        //
        // replaced query will hold our working query, replacing our
        // :placeholders with question marks
        //
        String workingReplacedQuery = getQuerySql();

        for (final UserQueryParameter param : userParamsByOccurrence)
        {
            workingReplacedQuery = workingReplacedQuery.replaceFirst(param.getKey(), "?");
        }

        return workingReplacedQuery;
    }

    private List<UserQueryParameter> getParameters()
    {
        return orderByKey(getQuerySql(), getUserParametersInternal());
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        int hash = 37;
        hash = Objects.hashCode(getName(), getDescription(), getQuerySql());
        return hash;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (obj == this)
        {
            return true;
        }
        if (!CustomQueryImpl.class.equals(obj.getClass()))
        {
            return false;
        }
        final CustomQueryImpl other = (CustomQueryImpl) obj;
        boolean equal = Objects.equal(getId(), other.getId());
        equal = equal && Objects.equal(getName(), other.getName());
        equal = equal && Objects.equal(getQuerySql(), other.getQuerySql());
        equal = equal && Objects.equal(getDescription(), other.getDescription());
        equal = equal && getUserParametersInternal().equals(other.getUserParametersInternal());

        return equal;
    }

    /**
     * <p>
     * given the query string and user parameters, transform into a JDBC-style query with question-mark place-holders. As the place-holders
     * may be used multiple times in the query, the size of the list returned may not be the same size as the collection parameter.
     * </p>
     * <p>
     * For example, given the query:
     * 
     * <pre>
     * &quot;select * from users where age &lt; :age or age &gt; :age&quot;
     * </pre>
     * 
     * And the place-holder value:
     * 
     * <pre>
     * :age = 15
     * </pre>
     * 
     * The return query and parameter list would be:
     * 
     * <pre>
     * &quot;select * from users where age &lt; ? or age &gt; ?&quot;
     * </pre>
     * 
     * <pre>
     * [ {:age, 15},  
     *   {:age, 15}]
     * </pre>
     * 
     * </p>
     * 
     * @param query
     *            the query string with place-holders to convert into a JDBC-style query
     * @param sourceParams
     *            the user parameters
     * @return a list of user query parameters in the order they are used
     */
    static final List<UserQueryParameter> orderByKey(final String query, final Collection<UserQueryParameter> sourceParams)
    {
        final Map<Integer, UserQueryParameter> workingParamsByIndex = new HashMap<Integer, UserQueryParameter>(sourceParams.size());
        int lastIndex = -1;
        for (final UserQueryParameter p : sourceParams)
        {
            int indexOf = query.indexOf(p.getKey(), lastIndex + 1);
            while (indexOf >= 0)
            {
                final UserQueryParameter replaced = workingParamsByIndex.put(Integer.valueOf(indexOf), p);
                assert replaced == null;
                lastIndex = indexOf;
                indexOf = query.indexOf(p.getKey(), lastIndex + 1);
            }
            lastIndex = -1;
        }

        final List<Integer> indices = new ArrayList<Integer>(workingParamsByIndex.keySet());
        Collections.sort(indices);

        final List<UserQueryParameter> ordered = new ArrayList<UserQueryParameter>(workingParamsByIndex.size());
        for (final Integer indexKey : indices)
        {
            final UserQueryParameter userParam = workingParamsByIndex.get(indexKey);
            ordered.add(userParam);
        }

        return ordered;
    }

}
