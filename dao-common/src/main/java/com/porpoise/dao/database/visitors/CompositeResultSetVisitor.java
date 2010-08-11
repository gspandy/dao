/**
 * CompositeResultSetVisitor.java
 */
package com.porpoise.dao.database.visitors;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.common.base.Preconditions;
import com.porpoise.dao.database.IResultSetVisitor;

/**
 * CompositeResultSetVisitor created: Jul 27, 2010
 * 
 * @author Aaron * An {@link IResultSetVisitor} which collates two implementations, delegating method calls to them in the order in which
 *         they were added to the CompositeResultSetVisitor
 */
public class CompositeResultSetVisitor implements IResultSetVisitor
{
    private final IResultSetVisitor visitor1;
    private final IResultSetVisitor visitor2;

    /**
     * @param v1
     *            the first visitor
     * @param v2
     *            the second visitor
     */
    public CompositeResultSetVisitor(final IResultSetVisitor v1, final IResultSetVisitor v2)
    {
        this.visitor1 = Preconditions.checkNotNull(v1);
        this.visitor2 = Preconditions.checkNotNull(v2);
    }

    /**
     * The first visitor will be called, followed by the second unless an exception is thrown by the first visitor, in which case it will be
     * propagated
     */
    public void onNoResults()
    {
        this.visitor1.onNoResults();
        this.visitor2.onNoResults();
    }

    /**
     * The first visitor will be called, followed by the second unless an exception is thrown by the first visitor, in which case it will be
     * propagated
     * 
     * @see com.porpoise.dao.database.IResultSetVisitor#onResultSet(java.sql.ResultSet)
     */
    public boolean onResultSet(final ResultSet resultSet) throws SQLException
    {
        boolean continueResult = true;
        continueResult |= this.visitor1.onResultSet(resultSet);
        continueResult |= this.visitor2.onResultSet(resultSet);
        return continueResult;
    }

}
