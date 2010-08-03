

package test.ing;

import test.ing.model.AaronDto;
import test.ing.model.AaronMetadata;

import com.porpoise.dao.database.metadata.Field;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 */
public class AaronSql
{
    ;// uninstantiable

    public static String attributeListAsString(final String alias)
    {
        return Field.asAttributeList(alias, AaronMetadata.FIELDS);
    }

    public static String byId(final Long id)
    {
        return "";
    }

    public static String selectLike(final AaronDto dto)
    {
        return select() + like("dto", dto);

    }

    private static String like(final String aliasPrefix, final AaronDto dto)
    {
        final String alias = aliasPrefix.endsWith(".") ? aliasPrefix : aliasPrefix + ".";
        return String.format(" WHERE %s", likeClause(alias, dto));
    }
    private static String likeClause(final String aliasPrefix, final AaronDto dto)
    {
        return "";
    }

    public static String select()
    {
        return String.format("SELECT %s FROM Aaron", attributeListAsString(null));
    }
    
}
