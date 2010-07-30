package test.ing.model;

import com.porpoise.dao.database.metadata.Field;
import java.math.BigDecimal;
import java.util.List;

/**
 * AaronMetadata
 */
public enum AaronMetadata
{
    ;// uninstantiable

    /**
     * Field ID
     */
    public static Field<Long> ID = new Field<Long>("ID", Long.class, true);

    /**
     * Field Name
     */
    public static Field<String> Name = new Field<String>("Name", String.class, false);

}
