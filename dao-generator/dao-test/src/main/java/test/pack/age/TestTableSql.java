

package test.pack.age;

import test.pack.age.model.TestTableDto;
import test.pack.age.model.TestTableMetadata;

import com.porpoise.dao.database.metadata.Field;
import com.google.common.base.Strings;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 */
public class TestTableSql
{
    ;// uninstantiable

    private static final String TABLE_NAME = "TEST_TABLE";

    public static String attributeListAsString(final String alias)
    {
        return Field.asAttributeList(alias, TestTableMetadata.FIELDS);
    }
    
    public static String attributeListAsUpdateString(final String alias)
    {
        return Field.asUpdateList(alias, TestTableMetadata.FIELDS);
    }

    public static String byId()
    {
        return by(TestTableMetadata.id);
    }

    public static String by(final Field<?> field)
    {
        return String.format("%s WHERE %s=?", select(), field.getName());
    }

    public static String insert()
    {
        final String propertyPlaceholders = Strings.repeat("?,", TestTableMetadata.FIELDS.size() - 1) + "?";
        return String.format("INSERT INTO %s (%s) VALUES (%s)", TABLE_NAME, attributeListAsString(null), propertyPlaceholders);
    }

    public static String update()
    {
        return String.format("UPDATE %s SET %s", TABLE_NAME, attributeListAsUpdateString(null));
    }

    public static String select()
    {
        return String.format("SELECT %s FROM %s", attributeListAsString(null), TABLE_NAME);
    }
    
}
