package a.b.c.model;

import com.porpoise.dao.database.metadata.Field;

public enum AaronMetadata
{
    ;// uninstantiable

    /**
     * Field ID
     */
    public static Field<Long> ID = new Field<Long>("ID", Long.class, true);

}
