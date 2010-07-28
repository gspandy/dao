/**
 * DbErrorCode.java
 */
package com.porpoise.dao.database;

import java.sql.SQLException;

import com.google.common.base.Preconditions;

/**
 * DbErrorCode created: Jul 27, 2010
 * 
 * @author Aaron *
 *         <p>
 *         This class provides methods to check for common SQL errors. It may be subclassed by other vendor-specific classes so as to work
 *         with various error codes, providing clients of this class with a consistent, vendor-agnostic view.
 *         </p>
 */
public class DbErrorCode
{
    private static final int  GENERIC_ERROR_CODE = 20000;
    private final DbException exp;

    /**
     * Constructor which takes a non-null exception
     * 
     * @param exception
     *            the exception used to initialise this error code
     */
    public DbErrorCode(final DbException exception)
    {
        this.exp = Preconditions.checkNotNull(exception);
    }

    /**
     * @return the originating exception
     */
    protected DbException getDbException()
    {
        return this.exp;
    }

    /**
     * @return true if the error was a table-not-found exception
     */
    public boolean isTableAlreadyExistsException()
    {
        final SQLException cause = this.exp.getCause();
        if (GENERIC_ERROR_CODE == cause.getErrorCode())
        {
            return cause.getMessage().contains("already exists in Schema");
        }
        return false;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return this.exp.toString();
    }
}
