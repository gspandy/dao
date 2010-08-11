/**
 * DelegateResultSetVisitor.java
 */
package com.porpoise.dao.database.visitors;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.common.base.Preconditions;
import com.porpoise.dao.database.IResultSetVisitor;

/**
 * DelegateResultSetVisitor created: Jul 27, 2010
 * 
 * @author Aaron
 */
public class DelegateResultSetVisitor implements IResultSetVisitor
{
    private final IResultSetVisitor delegate;

    public DelegateResultSetVisitor(final IResultSetVisitor delegate)
    {
        this.delegate = Preconditions.checkNotNull(delegate);
    }

    /**
     * @see IResultSetVisitor#onNoResults()
     */
    public void onNoResults()
    {
        this.delegate.onNoResults();
    }

    /**
     * @see IResultSetVisitor#onResultSet(java.sql.ResultSet)
     */
    public boolean onResultSet(final ResultSet resultSet) throws SQLException
    {
        return this.delegate.onResultSet(resultSet);
    }
}
