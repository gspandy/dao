/**
 * ICustomQuery.java

 */
package com.porpoise.dao.query;

import com.porpoise.dao.database.IDbTransaction;
import com.porpoise.dao.database.IResultSetVisitor;

/**
 * ICustomQuery
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 *
 * A custom query is a user-defined query which they've saved. It has a name and a description which may 
 * be used for display purposes.
 */
public interface ICustomQuery extends Iterable<UserQueryParameter>
{
    /**
     * @return id for this query
     */
    Long getId();

    /**
     * @return the name of this query
     */
    String getName();

    /**
     * @return a description for this query
     */
    String getDescription();

    /**
     * set the parameter value for the given user parameter key
     * 
     * @param <T>
     *            The query type
     * 
     * @param key
     *            the user parameter key
     * @param value
     *            the value to set for the given key
     * @return the custom query (typically 'this') so calls may be chained
     */
    <T extends ICustomQuery> T setUserParameter(String key, String value);

    /**
     * @param <T>
     *            The visitor type
     * @param transaction
     *            the database transaction used to execute teh query
     * @param visitor
     *            the visitor used to visit the result set
     * @return the visitor passed to the executeUpate function for convenience
     */
    <T extends IResultSetVisitor> T executeQuery(final IDbTransaction transaction,
            final T visitor);

    /**
     * get the query SQL
     * 
     * @return the custom query SQL
     */
    String getQuerySql();

    /**
     * set the ID for the custom query
     * 
     * @param id
     */
    void setId(Long id);
}
