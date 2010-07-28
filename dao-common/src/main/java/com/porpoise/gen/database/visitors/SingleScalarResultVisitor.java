/**
 * SingleScalarResultVisitor.java

 */
package com.porpoise.gen.database.visitors;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SingleScalarResultVisitor
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * A visitor implementation which only expects a single result (e.g. one row)
 * which consists of one column. The result value can then be accessed via one
 * of the many getXXX methods depending on the expected type.
 */
public class SingleScalarResultVisitor extends AtLeastOneResultSetVisitor
{
    private Object                         scalarResult;
    private final CountingResultSetVisitor onlyOneResultVisitor;

    /**
     * The default constructor
     */
    public SingleScalarResultVisitor()
    {
        this.onlyOneResultVisitor = new CountingResultSetVisitor(1);
    }

    /**
     * @see AbstractResultSetVisitor#onResultSet(java.sql.ResultSet)
     */
    public boolean onResultSet(final ResultSet resultSet) throws SQLException
    {
        // let the counting result set visitor assert we get only one result
        this.onlyOneResultVisitor.onResultSet(resultSet);

        this.scalarResult = resultSet.getObject(1);
        if (resultSet.wasNull())
        {
            this.scalarResult = null;
        }
        return true;
    }

    /**
     * @return return the scalar result as a nullable Long value
     */
    public Long getLong()
    {
        if (this.scalarResult == null)
        {
            return null;
        }
        if (this.scalarResult instanceof Long)
        {
            return (Long) this.scalarResult;
        }
        else if (this.scalarResult instanceof Number)
        {
            final Number number = (Number) this.scalarResult;
            return Long.valueOf(number.longValue());
        }
        return Long.valueOf(this.scalarResult.toString());
    }

    /**
     * @return return the scalar result as a nullable Integer value
     */
    public Integer getInteger()
    {
        if (this.scalarResult == null)
        {
            return null;
        }
        if (this.scalarResult instanceof Integer)
        {
            return (Integer) this.scalarResult;
        }
        else if (this.scalarResult instanceof Number)
        {
            final Number number = (Number) this.scalarResult;
            return Integer.valueOf(number.intValue());
        }
        return Integer.valueOf(this.scalarResult.toString());
    }

    /**
     * @return return the scalar result as a nullable String value
     */
    public String getString()
    {
        if (this.scalarResult == null)
        {
            return null;
        }
        return this.scalarResult.toString();
    }

    /**
     * @return return the scalar result as a nullable BigDecimal value
     */
    public BigDecimal getBigDecimal()
    {
        if (this.scalarResult == null)
        {
            return null;
        }
        if (this.scalarResult instanceof BigDecimal)
        {
            return (BigDecimal) this.scalarResult;
        }
        return new BigDecimal(this.scalarResult.toString());
    }

    /**
     * @return return the raw scalar value
     */
    public Object getObject()
    {
        return this.scalarResult;
    }

}
