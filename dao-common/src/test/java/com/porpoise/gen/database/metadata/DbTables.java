/**
 * DbTables.java

 */
package com.porpoise.gen.database.metadata;

/**
 * DbTables
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class DbTables
{
    private DbTables()
    {
        // uninstantiable
    }

    /**
     * create an address table
     * 
     * @return
     */
    public static DbTable address()
    {
        final DbTable table = new DbTable("TEST_ADDRESSES");
        table.add(new DbColumn("street", DbType.String));
        table.add(new DbColumn("town", DbType.String));
        table.add(new DbColumn("city", DbType.String));
        table.add(new DbColumn("post_code", DbType.String, false));
        return table;
    }

}
