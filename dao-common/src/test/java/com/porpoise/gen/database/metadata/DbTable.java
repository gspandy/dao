/**
 * DbTable.java

 */
package com.porpoise.gen.database.metadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.porpoise.gen.util.collections.ImmutableList;

/**
 * DbTable
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class DbTable implements Iterable<DbColumn>
{
    private String                     name;
    private final Collection<DbColumn> columns;

    static final String                NEW_LINE = String.format("%n");

    public DbTable()
    {
        this("");
    }

    public DbTable(final String tableName)
    {
        this.name = tableName;
        this.columns = new ArrayList<DbColumn>();
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(final String name)
    {
        this.name = name;
    }

    /**
     * @return the columns
     */
    public ImmutableList<DbColumn> getColumns()
    {
        return ImmutableList.copyOf(this.columns);
    }

    /**
     * @param e
     * @return
     * @see java.util.Collection#add(java.lang.Object)
     */
    public boolean add(final DbColumn e)
    {
        return this.columns.add(e);
    }

    /**
     * @see java.util.Collection#clear()
     */
    public void clear()
    {
        this.columns.clear();
    }

    /**
     * @param o
     * @return
     * @see java.util.Collection#remove(java.lang.Object)
     */
    public boolean remove(final Object o)
    {
        return this.columns.remove(o);
    }

    public Iterator<DbColumn> iterator()
    {
        return this.columns.iterator();
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value
     * format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    @Override
    public String toString()
    {
        final String TAB = "    ";

        String retValue = "";

        retValue = "DbTable ( " + super.toString() + TAB + "name = " + this.name + TAB + "columns = " + this.columns
                + TAB + " )";

        return retValue;
    }

    public String getCreateSql()
    {
        final StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE ").append(this.name).append(NEW_LINE);
        sqlBuilder.append(" ( ").append(NEW_LINE);

        for (final DbColumn col : this)
        {
            sqlBuilder.append(NEW_LINE);
            col.appendSqlTo(sqlBuilder);
            sqlBuilder.append(",");
        }

        //
        // remove the last ","
        //
        sqlBuilder.delete(sqlBuilder.length() - ",".length(), sqlBuilder.length());

        sqlBuilder.append(NEW_LINE);
        sqlBuilder.append(" ) ");
        return sqlBuilder.toString();
    }
}
