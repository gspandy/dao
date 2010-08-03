package test.ing.model;

import com.google.common.collect.ImmutableList;
import com.porpoise.dao.database.metadata.Field;
import java.math.BigDecimal;
import java.util.List;

/**
 * BenjaminMetadata
 */
public enum BenjaminMetadata
{
    ;// uninstantiable

    /**
     * Field ID
     */
    public static Field<Long> ID = new Field<Long>("ID", Long.class, true);

    /**
     * Field AARON_ID
     */
    public static Field<Long> AARON_ID = new Field<Long>("AARON_ID", Long.class, true);

    /**
     * Field Name
     */
    public static Field<String> Name = new Field<String>("Name", String.class, false);


    /**
     * All fields
     */
    public static ImmutableList<? extends Field<?>> FIELDS = ImmutableList.of(ID, AARON_ID, Name);
}
