package com.porpoise.dao.database.metadata;

import com.google.common.base.Preconditions;

public class Field<T>
{
    private final String   name;
    private final Class<T> javaType;
    private final boolean  required;

    public Field(final String fieldName, final Class<T> type, final boolean isRequired)
    {
        this.name = Preconditions.checkNotNull(fieldName);
        this.javaType = Preconditions.checkNotNull(type);
        this.required = isRequired;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @return the javaType
     */
    public Class<T> getJavaType()
    {
        return this.javaType;
    }

    /**
     * @return the required
     */
    public boolean isRequired()
    {
        return this.required;
    }
}
