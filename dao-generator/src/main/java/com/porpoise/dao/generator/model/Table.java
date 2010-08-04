package com.porpoise.dao.generator.model;

import java.util.List;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class Table
{
    private final String       name;
    private final List<Column> columns;
    private final List<Column> oneToMany = Lists.newArrayList();

    public Table(final String n)
    {
        this.name = n;
        this.columns = Lists.newArrayList();
    }

    private Column addColumn(final Column c)
    {
        if (getColumnByName(c.getName()) != null)
        {
            throw new IllegalArgumentException(String.format("A column with name '%s' is already defined", c.getName()));
        }

        this.columns.add(c);
        return c;
    }

    public Column getColumnByName(final String colName)
    {
        for (final Column c : this.columns)
        {
            if (c.getName().equals(colName))
            {
                return c;
            }
        }
        return null;
    }

    public Iterable<Column> getColumns()
    {
        return ImmutableList.copyOf(this.columns);
    }

    public String getJavaName()
    {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.name);
    }

    public String getTableName()
    {
        return this.name;
    }

    public void oneToMany(final Column fk)
    {
        this.oneToMany.add(fk);
    }

    public Column addColumn(final String colName, final boolean required, final ColType colType)
    {
        return addColumn(new Column(this, colName, required, colType));
    }
}
