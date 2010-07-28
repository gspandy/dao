/**
 * IDbTransaction.java

 */
package com.porpoise.gen.database;

/**
 * IDbTransaction
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public interface IDbTransaction
{

    /**
     * execute the given query and parameters, using the result set visitor to
     * process the results. The same visitor passed in as a parameter should be
     * returned from the function for added ease of use.
     * 
     * @param <T>
     *            the visitor type
     * @param visitor
     *            the visitor used to process the results
     * @param sql
     *            the query sql to execute
     * @param params
     *            parameters passed to the query
     * @return the result set visitor parameter
     * @throws DbException
     */
    public <T extends IResultSetVisitor> T executeQuery(final T visitor, final String sql, final Object... params)
            throws DbException;

    /**
     * execute an update statement with the given sql and parameters
     * 
     * @param sql
     *            the update sql to execute
     * @param args
     *            the parameters
     * @throws DbException
     */
    public void executeUpdate(String sql, Object... args) throws DbException;

    /**
     * commit the transaction
     * 
     * @throws DbException
     */
    public void commit() throws DbException;

    /**
     * rollback the transaction
     * 
     * @throws DbException
     */
    public void rollback() throws DbException;

    /**
     * close the transaction
     */
    public void close();

}