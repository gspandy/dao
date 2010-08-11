/**
 * MultipleResultToMapVisitor.java

 */
package com.porpoise.dao.database.visitors;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.porpoise.dao.database.IResultSetVisitor;

/**
 * MultipleResultToMapVisitor
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 * <p>
 * A MultipleResultToMapVisitor will convert each result set row into a
 * {@link Map}, adding each row to a list. If no results are returned then the
 * list will simply be empty.
 * </p>
 * <p>
 * The lists and maps returned from a MultipleResultToMapVisitor are mutable, so
 * a MultipleResultToMapVisitor can be treated as a class which initialises a
 * list of maps. Instances should generally not be reused as their results will
 * not be cleared.
 * </p>
 */
public class MultipleResultToMapVisitor implements IResultSetVisitor
{
    private final List<Map<String, Object>> results;
    private Integer                         columnCount;
    private ResultSetMetaData               metaData;

    /**
     * Default constructor
     */
    public MultipleResultToMapVisitor()
    {
        this.results = new ArrayList<Map<String, Object>>();
    }

    /**
     * @see IResultSetVisitor#onNoResults()
     */
    public void onNoResults()
    {
        // noop
    }

    /**
     * Read the result set contents into a map, adding the map to a list.
     * 
     * @see com.porpoise.dao.database.IResultSetVisitor#onResultSet(java.sql.ResultSet)
     */
    public boolean onResultSet(final ResultSet resultSet) throws SQLException
    {
        if (this.metaData == null)
        {
            this.metaData = resultSet.getMetaData();
            this.columnCount = Integer.valueOf(this.metaData.getColumnCount());
        }

        final Map<String, Object> row = new HashMap<String, Object>((int) (this.columnCount.intValue() / 0.75));

        for (int colIndex = 1; colIndex <= this.columnCount.intValue(); colIndex++)
        {
            final String columnName = this.metaData.getColumnName(colIndex);
            final Object value = resultSet.getObject(colIndex);

            final Object oldValue = row.put(columnName, value);
            assert oldValue == null : "A value already existed for column " + columnName;
        }

        this.results.add(row);

        return true;
    }

    /**
     * return the result set rows as a list of maps which map between column
     * names and their values
     * 
     * @return a list of rows represented by a map of column names to column
     *         values
     */
    public List<Map<String, Object>> getRows()
    {
        return this.results;
    }
}
