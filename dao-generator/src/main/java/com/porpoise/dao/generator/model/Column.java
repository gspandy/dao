package com.porpoise.dao.generator.model;

public class Column
{
    private final String  name;
    private final boolean required;
    private final Type    type;

    public Column(final String n, final boolean isRequired, final Type colType)
    {
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
    public Type getType()
    {
        return this.type;
    }

}
