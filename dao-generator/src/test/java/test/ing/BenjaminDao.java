

package test.ing;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import test.ing.model.BenjaminDto;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.porpoise.dao.database.DbConnectionFactory;
import com.porpoise.dao.database.dao.AbstractDao;
import com.porpoise.dao.database.visitors.AbstractResultSetVisitor;

/**
 * Functional Data Access class for BenjaminDto objects
 */
public class BenjaminDao extends AbstractDao
{
    ;// uninstantiable
    
    private static class Visitor extends AbstractResultSetVisitor {
        
        private final List<BenjaminDto> dtoResults;

        public Visitor()
        {
            this.dtoResults = Lists.newArrayList();
        }
        
        @Override
        public boolean onResultSet(final ResultSet resultSet) throws SQLException
        {
            int columnIndex = 1;

//            final BigDecimal amount = getBigDecimal(resultSet, columnIndex++);
//            final String name = getString(resultSet, columnIndex++);
            final Long id = getLong(resultSet, columnIndex++);
            final Long aaronId = getLong(resultSet, columnIndex++);
            final String name = getString(resultSet, columnIndex++);
            final BenjaminDto dto  = new BenjaminDto(id, aaronId, name);
            this.dtoResults.add(dto);
            return true;
        }

        /**
         * @return the dtoResults
         */
        public List<BenjaminDto> getDtoResults()
        {
            return ImmutableList.copyOf(this.dtoResults);
        }

        public BenjaminDto getSingleResult()
        {
            return Iterables.getOnlyElement(this.dtoResults);
        }
        
    }
    
    /**
     * @param factory
     * @param id
     * @return
     */
    public BenjaminDto byId(final DbConnectionFactory factory, final Long id)
    {
        final String querySql = BenjaminSql.byId(id);
        final Visitor visitor = factory.executeQueryInSingleTransaction(new Visitor(), querySql, id);
        return visitor.getSingleResult();
    }
    
}
