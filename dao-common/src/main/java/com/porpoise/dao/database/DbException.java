/**
 * DbException.java
 */
package com.porpoise.dao.database;

import java.sql.SQLException;

import com.porpoise.dao.database.init.Databases;

/**
 * DbException created: Jul 27, 2010
 * 
 * @author Aaron
 */
public class DbException extends RuntimeException
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param message
     *            an error message
     * @param exp
     *            the root exception
     */
    public DbException(final String message, final SQLException exp)
    {
        super(message, exp);
    }

    /**
     * @param exp
     *            the root exception
     */
    public DbException(final SQLException exp)
    {
        super(exp);
    }

    /**
     * @return the underlying SQLException for this DbException
     */
    @Override
    public SQLException getCause()
    {
        return (SQLException) super.getCause();
    }

    /**
     * Return a database-agnostic representation of the database error
     * 
     * @return the DbErrorCode for this exception
     */
    public DbErrorCode getErrorCode()
    {
        return Databases.getVendor().translateErrorCode(this);
    }

}
