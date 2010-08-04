package com.porpoise.dao.generator.model;

import com.google.common.base.CaseFormat;

public class Column
{
    private final String  name;
    private final boolean required;
    private final ColType type;
    private final Table   owningTable;

    Column(final Table table, final String n, final boolean isRequired, final ColType colType)
    {
        this.owningTable = table;
        this.name = n;
        this.required = isRequired;
        this.type = colType;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @return the required
     */
    public boolean isRequired()
    {
        return this.required;
    }

    /**
     * @return the type
     */
    public ColType getType()
    {
        return this.type;
    }

    public String getJavaTypeName()
    {
        return this.type.getJavaName();
    }

    public String getNameAsProperty()
    {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, getName());
    }

    public String getNameAsAccessor()
    {
        return "get" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, getName());
    }

}
