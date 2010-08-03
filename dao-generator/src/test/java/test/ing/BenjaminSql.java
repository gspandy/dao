

package test.ing;

import test.ing.model.BenjaminDto;
import test.ing.model.BenjaminMetadata;

import com.porpoise.dao.database.metadata.Field;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 */
public class BenjaminSql
{
    ;// uninstantiable

    public static String attributeListAsString(final String alias)
    {
        return Field.asAttributeList(alias, BenjaminMetadata.FIELDS);
    }

    public static String byId(final Long id)
    {
        return "";
    }

    public static String selectLike(final BenjaminDto dto)
    {
        return select() + like("dto", dto);

    }

    private static String like(final String aliasPrefix, final BenjaminDto dto)
    {
        final String alias = aliasPrefix.endsWith(".") ? aliasPrefix : aliasPrefix + ".";
        return String.format(" WHERE %s", likeClause(alias, dto));
    }
    private static String likeClause(final String aliasPrefix, final BenjaminDto dto)
    {
        return "";
    }

    public static String select()
    {
        return String.format("SELECT %s FROM Benjamin", attributeListAsString(null));
    }
    
}
