

package test.ing;

import test.ing.model.BenjaminDto;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 */
public class BenjaminSql
{
    ;// uninstantiable

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
        return alias;
    }

    public static String select()
    {
        return null;
    }
    
}
