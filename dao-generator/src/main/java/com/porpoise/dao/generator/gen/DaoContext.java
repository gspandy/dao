package com.porpoise.dao.generator.gen;

import com.porpoise.dao.generator.model.Table;

public class DaoContext
{

    private final Table  table;
    private final String packageName;

    public DaoContext(final String packageName, final Table t)
    {
        this.packageName = packageName;
        this.table = t;
    }

    /**
     * @return the table
     */
    public Table getTable()
    {
        return this.table;
    }

    /**
     * @return the packageName
     */
    public String getPackageName()
    {
        return this.packageName;
    }

    /**
     * @return the packageName
     */
    public String getPackageNameAsPath()
    {
        return this.packageName.replace('.', '/');
    }

    public String getName()
    {
        return this.table.getName();
    }
}
