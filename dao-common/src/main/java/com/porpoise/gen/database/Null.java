/**
 * Null.java

 */
package com.porpoise.gen.database;

import java.sql.Types;

/**
 * Null
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 * <p>
 * Enum to represent null database values. Instead of passing an actual null value to an IDbTransaction
 * update method, an instance of {@code Null} may be used with the given {@link Types} parameter
 * to ensure the SQL update statement is prepared correctly.
 * </p>
 */
public enum Null
{
    /**
     * ARRAY
     */
    ARRAY(Types.ARRAY),
    /**
     * BIGINT
     */
    BIGINT(Types.BIGINT),
    /**
     * BINARY
     */
    BINARY(Types.BINARY),
    /**
     * BIT
     */
    BIT(Types.BIT),
    /**
     * BLOB
     */
    BLOB(Types.BLOB),
    /**
     * BOOLEAN
     */
    BOOLEAN(Types.BOOLEAN),
    /**
     * CHAR
     */
    CHAR(Types.CHAR),
    /**
     * CLOB
     */
    CLOB(Types.CLOB),
    /**
     * DATALINK
     */
    DATALINK(Types.DATALINK),
    /**
     * DATE
     */
    DATE(Types.DATE),
    /**
     * DECIMAL
     */
    DECIMAL(Types.DECIMAL),
    /**
     * DISTINCT
     */
    DISTINCT(Types.DISTINCT),
    /**
     * DOUBLE
     */
    DOUBLE(Types.DOUBLE),
    /**
     * FLOAT
     */
    FLOAT(Types.FLOAT),
    /**
     * INTEGER
     */
    INTEGER(Types.INTEGER),
    /**
     * JAVA_OBJECT
     */
    JAVA_OBJECT(Types.JAVA_OBJECT),
    /**
     * LONGVARBINARY
     */
    LONGVARBINARY(Types.LONGVARBINARY),
    /**
     * LONGVARCHAR
     */
    LONGVARCHAR(Types.LONGVARCHAR),
    /**
     * NULL
     */
    NULL(Types.NULL),
    /**
     * NUMERIC
     */
    NUMERIC(Types.NUMERIC),
    /**
     * OTHER
     */
    OTHER(Types.OTHER),
    /**
     * REAL
     */
    REAL(Types.REAL),
    /**
     * REF
     */
    REF(Types.REF),
    /**
     * SMALLINT
     */
    SMALLINT(Types.SMALLINT),
    /**
     * STRUCT
     */
    STRUCT(Types.STRUCT),
    /**
     * TIME
     */
    TIME(Types.TIME),
    /**
     * TIMESTAMP
     */
    TIMESTAMP(Types.TIMESTAMP),
    /**
     * TINYINT
     */
    TINYINT(Types.TINYINT),
    /**
     * VARBINARY
     */
    VARBINARY(Types.VARBINARY),
    /**
     * VARCHAR
     */
    VARCHAR(Types.VARCHAR);

    private final int type;

    /**
     * @param type the {@link Types} SQL type  
     */
    Null(final int type)
    {
        this.type = type;
    }

    /**
     * @return the SQL type
     */
    public int getType()
    {
        return this.type;
    }
}
