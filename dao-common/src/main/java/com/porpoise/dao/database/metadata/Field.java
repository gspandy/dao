package com.porpoise.dao.database.metadata;

import java.util.Iterator;

import com.google.common.base.Preconditions;

public class Field<T>
{
    private final String   name;
    private final Class<T> javaType;
    private final boolean  required;

    public static String asAttributeList(final String alias, final Iterable<? extends Field<?>> fields)
    {
        final boolean appendEquals = false;
        return asSqlString(alias, fields, appendEquals);
    }

    public static String asUpdateList(final String alias, final Iterable<? extends Field<?>> fields)
    {
        final boolean appendEquals = true;
        return asSqlString(alias, fields, appendEquals);
    }

    private static String asSqlString(final String alias, final Iterable<? extends Field<?>> fields, final boolean appendEquals)
    {
        final StringBuilder b = new StringBuilder();
        final Iterator<? extends Field<?>> iter = fields.iterator();
        while (iter.hasNext())
        {
            if (alias != null)
            {
                b.append(alias).append(".");
            }
            b.append(iter.next().getName());
            if (appendEquals)
            {
                b.append("=?");
            }
            if (iter.hasNext())
            {
                b.append(", ");
            }
        }
        return b.toString();
    }

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
