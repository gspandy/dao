

package test.pack.age;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import test.pack.age.model.TestTableDto;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.porpoise.dao.database.IDbTransaction;
import com.porpoise.dao.database.DbConnectionFactory;
import com.porpoise.dao.database.dao.AbstractDao;
import com.porpoise.dao.database.visitors.AbstractResultSetVisitor;

/**
 * Functional Data Access class for TestTableDto objects
 */
public class TestTableDao extends AbstractDao
{
    
    private static class Visitor extends AbstractResultSetVisitor {
        
        private final List<TestTableDto> dtoResults;

        public Visitor()
        {
            this.dtoResults = Lists.newArrayList();
        }
        
        @Override
        public boolean onResultSet(final ResultSet resultSet) throws SQLException
        {
            int columnIndex = 1;

            final Long id = getLong(resultSet, columnIndex++);
            final String lastname = getString(resultSet, columnIndex++);
            final String firstname = getString(resultSet, columnIndex++);

            final TestTableDto dto  = new TestTableDto(id, lastname, firstname);
            this.dtoResults.add(dto);
            return true;
        }

        /**
         * @return the dtoResults
         */
        public List<TestTableDto> getDtoResults()
        {
            return ImmutableList.copyOf(this.dtoResults);
        }

        public TestTableDto getSingleResult()
        {
            return Iterables.getOnlyElement(this.dtoResults);
        }
        
    }
    
    /**
     * @param transaction
     * @param id
     * @return
     */
    public TestTableDto findById(final IDbTransaction transaction, final Long id)
    {
        final String querySql = TestTableSql.byId();
        final Visitor visitor = transaction.executeQuery(new Visitor(), querySql, id);
        return visitor.getSingleResult();
    }
    
    /** 
     * @param transaction
     * @param dto
     */
    public void insert(final IDbTransaction transaction, final TestTableDto dto)
    {
        final String sql = TestTableSql.insert();
        transaction.executeUpdate(sql, dto.getId(), dto.getLastName(), dto.getFirstName()); 
    }    
    
    /** 
     * @param transaction
     * @param id the ID of the element to update
     * @param dto
     */
    public void <T> update(final IDbTransaction transaction, T id, final TestTableDto dto)
    {
        final String sql = TestTableSql.update() + " WHERE id=?";
        transaction.executeUpdate(sql, dto.getId(), dto.getLastName(), dto.getFirstName(), id); 
    }    
}
