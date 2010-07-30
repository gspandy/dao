package com.porpoise.dao.generator.model;

import java.util.List;

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
        this.columns.add(c);
        return c;
    }

    public Iterable<Column> getColumns()
    {
        return ImmutableList.copyOf(this.columns);
    }

    public String getName()
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
