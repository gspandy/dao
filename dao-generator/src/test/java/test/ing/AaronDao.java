

package test.ing;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import test.ing.model.AaronDto;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.porpoise.dao.database.DbConnectionFactory;
import com.porpoise.dao.database.dao.AbstractDao;
import com.porpoise.dao.database.visitors.AbstractResultSetVisitor;

/**
 * Functional Data Access class for AaronDto objects
 */
public class AaronDao extends AbstractDao
{
    ;// uninstantiable
    
    private static class Visitor extends AbstractResultSetVisitor {
        
        private final List<AaronDto> dtoResults;

        public Visitor()
        {
            this.dtoResults = Lists.newArrayList();
        }
        
        @Override
        public boolean onResultSet(final ResultSet resultSet) throws SQLException
        {
            int columnIndex = 1;

            final Long id = getLong(resultSet, columnIndex++);
            final String name = getString(resultSet, columnIndex++);

            final AaronDto dto  = new AaronDto(id, name);
            this.dtoResults.add(dto);
            return true;
        }

        /**
         * @return the dtoResults
         */
        public List<AaronDto> getDtoResults()
        {
            return ImmutableList.copyOf(this.dtoResults);
        }

        public AaronDto getSingleResult()
        {
            return Iterables.getOnlyElement(this.dtoResults);
        }
        
    }
    
    /**
     * @param factory
     * @param id
     * @return
     */
    public AaronDto byId(final DbConnectionFactory factory, final Long id)
    {
        final String querySql = AaronSql.byId(id);
        final Visitor visitor = factory.executeQueryInSingleTransaction(new Visitor(), querySql, id);
        return visitor.getSingleResult();
    }
    
}
