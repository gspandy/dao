/**
 * FirstResultToMapVisitor.java

 */
package com.porpoise.dao.database.visitors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * FirstResultToMapVisitor
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * Visitor which will only retrieve at most the first row of a result set, even
 * if multiple results are returned. To check for unexpected multiple results
 * this visitor should be used in conjunction with a test for multiple results,
 * such as a {@link CountingResultSetVisitor}
 */
public class FirstResultToMapVisitor extends AtLeastOneResultSetVisitor
{
    final MultipleResultToMapVisitor multiResultVisitor = new MultipleResultToMapVisitor();

    /**
     * @return true if the visitor should continue parsing result sets
     */
    @Override
    public boolean onResultSet(final ResultSet resultSet) throws SQLException
    {
        this.multiResultVisitor.onResultSet(resultSet);
        return false;
    }

    /**
     * return the first result row or null if no results were returned
     * 
     * @return the first result row or null if no results were returned
     */
    public Map<String, Object> getResultRow()
    {
        final List<Map<String, Object>> rows = this.multiResultVisitor.getRows();
        if (rows.isEmpty())
        {
            return null;
        }
        return rows.get(0);
    }

}
