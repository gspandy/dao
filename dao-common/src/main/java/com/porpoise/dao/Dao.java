package com.porpoise.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.porpoise.dao.database.DbConnectionFactory;
import com.porpoise.dao.database.IResultSetVisitor;
import com.porpoise.dao.database.init.Databases;

public class Dao
{

    public <T> T byId(final String querySql, final Object... params)
    {
        final IResultSetVisitor visitor = new IResultSetVisitor() {

            @Override
            public boolean onResultSet(final ResultSet resultSet) throws SQLException
            {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void onNoResults()
            {
                // TODO Auto-generated method stub

            }
        };
        final DbConnectionFactory d = Databases.initFromConfiguration();
        d.executeQueryInSingleTransaction(visitor, querySql, params);
        DbConnectionFactory.INSTANCE.getDefaultDataSource();

        return null;
    }
}
