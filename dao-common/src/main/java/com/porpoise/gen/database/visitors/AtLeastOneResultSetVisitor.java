/**
 * AtLeastOneResultSetVisitor.java

 */
package com.porpoise.gen.database.visitors;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.porpoise.gen.database.IResultSetVisitor;

/**
 * AtLeastOneResultSetVisitor
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * A base class for result set visitors which require at least one result
 */
public abstract class AtLeastOneResultSetVisitor extends AbstractResultSetVisitor
{
    /**
     * @see AbstractResultSetVisitor#onNoResults()
     */
    @Override
    public void onNoResults()
    {
        throw new IllegalStateException("No results were returned for the query");
    }
}
