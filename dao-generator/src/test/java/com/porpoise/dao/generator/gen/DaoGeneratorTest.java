package com.porpoise.dao.generator.gen;

import org.junit.Test;

import com.porpoise.dao.database.DbConnectionDetails;
import com.porpoise.dao.database.DbConnectionFactory;
import com.porpoise.dao.database.init.Databases;
import com.porpoise.dao.database.tools.DbScriptExecutor;

public class DaoGeneratorTest
{

    /**
     * Test we can generate a DAO for a simple table
     */
    @Test
    public void test_generateDao()
    {
        final DbConnectionDetails details = new DbConnectionDetails();
        final DbConnectionFactory factory = Databases.DERBY.init(DbConnectionFactory.getInstance(), "DEFAULT", details);

        DbScriptExecutor.executeSQL();

    }

}
