/**
 * DelegateResultSetVisitor.java

 */
package com.porpoise.gen.database.visitors;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.porpoise.gen.database.IResultSetVisitor;
import com.porpoise.gen.util.Validation;

/**
 * DelegateResultSetVisitor
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class DelegateResultSetVisitor implements IResultSetVisitor
{
    private final IResultSetVisitor delegate;

    public DelegateResultSetVisitor(final IResultSetVisitor delegate)
    {
        this.delegate = Validation.notNull(delegate);
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
