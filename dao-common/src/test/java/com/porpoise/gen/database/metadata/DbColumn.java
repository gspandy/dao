/**
 * DbColumn.java

 */
package com.porpoise.gen.database.metadata;

/**
 * DbColumn
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class DbColumn
{
    private String  name;
    private boolean nullable;
    private DbType  colType;

    public DbColumn()
    {
        this("");
    }

    public DbColumn(final String colName)
    {
        this(colName, DbType.String, true);
    }

    public DbColumn(final String colName, final DbType type)
    {
        this(colName, type, true);
    }

    public DbColumn(final String colName, final DbType type, final boolean isNullable)
    {
        this.name = colName;
        this.colType = type;
        this.nullable = isNullable;
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
     * @return the nullable
     */
    public boolean isNullable()
    {
        return this.nullable;
    }

    /**
     * @param nullable
     *            the nullable to set
     */
    public void setNullable(final boolean nullable)
    {
        this.nullable = nullable;
    }

    /**
     * @return the type
     */
    public DbType getType()
    {
        return this.colType;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(final DbType type)
    {
        this.colType = type;
    }

    void appendSqlTo(final StringBuilder sqlBuilder)
    {
        //
        // TODO - this will become db-specific, but for now
        // sticking to standard sql should work
        //
        sqlBuilder.append(this.name)//
                .append(" ")//
                .append(this.colType.toSql())//
                .append(" ");

        if (false && !isNullable())
        {
            sqlBuilder.append("NOT NULL");
        }

    }

}
