package a.b.c;

import a.b.c.model.AaronDto;

public enum AaronSql
{
    ;// uninstantiable

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
        return alias;
    }

    public static String select()
    {
        return null;
    }
}
