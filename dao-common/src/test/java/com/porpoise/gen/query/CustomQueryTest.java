/**
 * CustomQueryTest.java

 */
package com.porpoise.gen.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.porpoise.gen.database.DbException;
import com.porpoise.gen.database.IDbTransaction;
import com.porpoise.gen.database.IResultSetVisitor;
import com.porpoise.gen.database.visitors.AtLeastOneResultSetVisitor;
import com.porpoise.gen.test.Assert;
import org.junit.Test;

/**
 * CustomQueryTest
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * Tests for the {@link CustomQueryImpl} class
 */
public class CustomQueryTest
{
    private static class TestTransaction implements IDbTransaction
    {
        private final Object[] expectedParams;
        private final String   expectedQuery;

        public TestTransaction(final String query, final Object... params)
        {
            this.expectedQuery = query;
            this.expectedParams = params;
        }

        public <T extends IResultSetVisitor> T executeQuery(final T visitor, final String sql, final Object... params)
                throws DbException
        {
            Assert.assertEquals(this.expectedQuery, sql);
            Assert.assertArrayEquals(this.expectedParams, params);
            return visitor;
        }

        public void executeUpdate(@SuppressWarnings("unused") final String sql,
                @SuppressWarnings("unused") final Object... args)
        {
            throw new UnsupportedOperationException();
        }

        public void commit() throws DbException
        {
            throw new UnsupportedOperationException();
        }

        public void rollback() throws DbException
        {
            throw new UnsupportedOperationException();
        }

        public void close()
        {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * test the orderByKey method, assuring keys are put in the correct order by
     * occurrence in the query string
     */
    @Test
    public void test_orderByKey()
    {
        final String testQueryString = "select id from * where x != :age i > :id and name like :name and c != :id";
        final Collection<UserQueryParameter> sourceParams = new ArrayList<UserQueryParameter>();

        //
        // test parameters
        //
        final UserQueryParameter nameParam = new UserQueryParameter(":name", "name value");
        sourceParams.add(nameParam);
        final UserQueryParameter idParam = new UserQueryParameter(":id", "id value");
        sourceParams.add(idParam);
        final UserQueryParameter ageParam = new UserQueryParameter(":age", "age value");
        sourceParams.add(ageParam);

        //
        // call our method under test
        //
        final List<UserQueryParameter> ordered = CustomQueryImpl.orderByKey(testQueryString, sourceParams);

        //
        // now assert the expected order
        //
        Assert.assertEquals(4, ordered.size());
        Assert.assertEquals(ageParam, ordered.get(0));
        Assert.assertEquals(idParam, ordered.get(1));
        Assert.assertEquals(nameParam, ordered.get(2));
        Assert.assertEquals(idParam, ordered.get(3));
    }

    /**
     * test the query is properly replaced
     */
    @Test
    public void test_customQueryReplacesMultipleParameters()
    {
        Arrays.asList("id", "name");
        final CustomQueryImpl query = new CustomQueryImpl("i > :a and name like :b and a != :a");

        query.setUserParameter(":a", "one");
        query.setUserParameter(":b", "aaron");

        final TestTransaction transaction = new TestTransaction("i > ? and name like ? and a != ?", "one", "aaron",
                "one");

        final IResultSetVisitor paramValue = new AtLeastOneResultSetVisitor()
        {
            // ignore
        };
        final IResultSetVisitor returnedValue = query.executeQuery(transaction, paramValue);
        Assert.assertSame(paramValue, returnedValue);
    }
}
