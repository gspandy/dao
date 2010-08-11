/**
 * AbstractDao.java

 */
package com.porpoise.dao.database.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.porpoise.dao.database.Null;

/**
 * AbstractDao
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public abstract class AbstractDao
{
    protected Object nullable(final Date date)
    {
        return date == null ? Null.TIMESTAMP : date;
    }

    protected Object nullableDate(final Date date)
    {
        return date == null ? Null.DATE : date;
    }

    protected Object nullableTime(final Date date)
    {
        return date == null ? Null.TIME : date;
    }

    protected Object nullable(final BigDecimal value)
    {
        return value == null ? Null.DECIMAL : value;
    }

    protected Object nullable(final Long value)
    {
        return value == null ? Null.NUMERIC : value;
    }

    protected Object nullable(final Integer value)
    {
        return value == null ? Null.NUMERIC : value;
    }

    protected Object nullable(final Double value)
    {
        return value == null ? Null.DOUBLE : value;
    }

    protected Object nullable(final Boolean value)
    {
        return value == null ? Null.BOOLEAN : value;
    }

    protected Object nullable(final String value)
    {
        return value == null ? Null.VARCHAR : value;
    }

    protected Object nullable(final Object value)
    {
        return value == null ? Null.OTHER : value;
    }

    /**
     * prepend if necessary the "WHERE" text
     * 
     * @param whereClause
     * @return the prepared where SQL
     */
    protected String prepareWhereClause(final String whereClause)
    {
        final String trimmedWhere = whereClause.trim();
        final boolean prependWhere = trimmedWhere.length() > 0
                && !trimmedWhere.toLowerCase().startsWith("where");
        final String whereSQL = prependWhere
                ? "WHERE " + whereClause
                : whereClause;
        return whereSQL;
    }

}
