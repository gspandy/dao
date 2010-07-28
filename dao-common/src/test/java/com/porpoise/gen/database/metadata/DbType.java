/**
 * DbType.java

 */
package com.porpoise.gen.database.metadata;

import java.sql.Types;

/**
 * DbType
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public enum DbType
{
    /** */
    String(Types.VARCHAR, "varchar(200)"),
    /** */
    Integer(Types.INTEGER, "integer"),
    /** */
    Long(Types.BIGINT, "bigint"),
    /** */
    Boolean(Types.BOOLEAN, "boolean"),
    /** */
    Date(Types.DATE, "date"),
    /** */
    TimeStamp(Types.TIMESTAMP, "timestamp"),
    /** */
    Decimal(Types.DECIMAL, "decimal"),
    /** */
    Blob(Types.BLOB, "blob");

    @SuppressWarnings("unused")
    private final int    sqlType;
    private final String sqlString;

    DbType(final int type, final String sql)
    {
        this.sqlType = type;
        this.sqlString = sql;
    }

    public String toSql()
    {
        return this.sqlString;
    }

}
