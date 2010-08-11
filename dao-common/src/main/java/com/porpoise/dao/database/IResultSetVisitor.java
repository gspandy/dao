/**
 * IResultSetVisitor.java

 */
package com.porpoise.dao.database;



import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * IResultSetVisitor
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * a result set visitor can be used to visit each row ({@link ResultSet}) from a
 * query, allowing an {@link IDbTransaction} to increment the result set
 */
public interface IResultSetVisitor
{
    /**
     * <p>
     * static constant used to aid in readability in IResultSetVisitor implementations
     * </p>
     * <p>
     * For example, the latter is preferred to the former:
     * <pre>
     *  public boolean onResultSet(ResultSet resultSet) throws SQLException {
     *     ...
     *     return true;
     *  }
     * </pre>   
     * <pre>   
     *  public boolean onResultSet(ResultSet resultSet) throws SQLException {
     *     ...
     *     return CONTINUE;
     *  }
     * </pre>   
     * </p>
     */
    public static final boolean CONTINUE = true;
    
    /**
     * visitors should <Strong>not</strong> call resultSet.next(), but rather
     * simply use it to gather details
     * 
     * @param resultSet
     *            the resultSet to visit. It will be prepared for each row
     *            returned
     * @return true to continue, false otherwise
     * @throws SQLException
     */
    boolean onResultSet(final ResultSet resultSet) throws SQLException;

    /**
     * no results were returned from the query
     */
    void onNoResults();

}
