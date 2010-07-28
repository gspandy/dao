/**
 * CountingResultSetVisitor.java

 */
package com.porpoise.gen.database.visitors;

import java.sql.ResultSet;

import com.porpoise.gen.database.IResultSetVisitor;
import com.porpoise.gen.util.Validation;

/**
 * CountingResultSetVisitor
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 *
 * A counting result set visitor ensures an expected number of rows
 * are returned.
 */
public class CountingResultSetVisitor implements IResultSetVisitor
{

    private final int    expectedCount;
    private volatile int count = 0;

    /**
     * @param expectedCount the number of expected results
     */
    public CountingResultSetVisitor(final int expectedCount)
    {
        this.expectedCount = Validation.nonNegative(expectedCount);
    }

    /**
     * @see IResultSetVisitor#onNoResults()
     */
    public void onNoResults()
    {
        if (this.expectedCount != 0)
        {
            throwException();
        }
    }

    private void throwException()
    {
        throw new IllegalStateException(String.format("%d results were expected, at least %d were received",
                Integer.valueOf(this.expectedCount),
                Integer.valueOf(this.count)));
    }

    /**
     * @see IResultSetVisitor#onResultSet(java.sql.ResultSet)
     */
    public boolean onResultSet(final ResultSet resultSet)
    {
        if (++this.count > this.expectedCount)
        {
            throwException();
        }
        return true;
    }

    /**
     * @return the count
     */
    public int getCount()
    {
        return this.count;
    }
}
