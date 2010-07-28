package com.porpoise.dao.generator.model;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class Table
{
    private final String       name;
    private final List<Column> columns;

    public Table(final String n)
    {
        this.name = n;
        this.columns = Lists.newArrayList();
    }

    public void addColumn(final Column c)
    {
        this.columns.add(c);
    }

    public Iterable<Column> getColumns()
    {
        return ImmutableList.copyOf(this.columns);
    }

    public String getName()
    {
        return this.name;
    }
}
