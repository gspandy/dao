package test.pack.age.model;

import com.google.common.collect.ImmutableList;
import com.porpoise.dao.database.metadata.Field;
import java.math.BigDecimal;
import java.util.List;

/**
 * TestTableMetadata
 */
public enum TestTableMetadata
{
    ;// uninstantiable

    /**
     * Field id
     */
    public static Field<Long> id = new Field<Long>("id", Long.class, false);

    /**
     * Field LastName
     */
    public static Field<String> LastName = new Field<String>("LastName", String.class, false);

    /**
     * Field FirstName
     */
    public static Field<String> FirstName = new Field<String>("FirstName", String.class, false);


    /**
     * All fields
     */
    public static ImmutableList<? extends Field<?>> FIELDS = ImmutableList.of(id, LastName, FirstName);
}
