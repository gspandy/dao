/**
 * SingleResultToMapVisitor.java

 */
package com.porpoise.dao.database.visitors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.porpoise.dao.database.IResultSetVisitor;

/**
 * SingleResultToMapVisitor
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * Visitor which will only retrieve at most the first row of a result set, even
 * if multiple results are returned. To check for unexpected multiple results
 * this visitor should be used in conjunction with a test for multiple results,
 * such as a {@link CountingResultSetVisitor}
 */
public class SingleResultToMapVisitor implements IResultSetVisitor
{
    final MultipleResultToMapVisitor multiResultVisitor = new MultipleResultToMapVisitor();

    /**
     * @see IResultSetVisitor#onResultSet(java.sql.ResultSet)
     */
    public boolean onResultSet(final ResultSet resultSet) throws SQLException
    {
        if (this.multiResultVisitor.getRows().size() > 1)
        {
            throw new IllegalStateException("only a single result was expected");
        }
        this.multiResultVisitor.onResultSet(resultSet);
        return true;
    }

    /**
     * return the first result row or null if no results were returned
     * 
     * @return the first result row or null if no results were returned
     */
    public Map<String, Object> getResultRow()
    {
        if (this.multiResultVisitor.getRows().size() != 1)
        {
            final Integer resultCount = Integer.valueOf(this.multiResultVisitor.getRows().size());
            throw new IllegalStateException(String.format("A single result was expected, but rather %d were returned",
                    resultCount));
        }
        return this.multiResultVisitor.getRows().get(0);
    }

    /**
     * @see IResultSetVisitor#onNoResults()
     */
    public void onNoResults()
    {
        throw new IllegalStateException("A single result was expected, but none were returned");
    }

}
