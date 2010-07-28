package com.porpoise.dao.generator.templates;

import java.util.*;
import com.porpoise.dao.generator.gen.*;

public class DaoTemplate
{
  protected static String nl;
  public static synchronized DaoTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    DaoTemplate result = new DaoTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + NL + "package ";
  protected final String TEXT_3 = ";" + NL + "" + NL + "import java.math.BigDecimal;" + NL + "import java.sql.ResultSet;" + NL + "import java.sql.SQLException;" + NL + "import java.util.List;" + NL + "" + NL + "import ";
  protected final String TEXT_4 = ".model.";
  protected final String TEXT_5 = "Dto;" + NL + "" + NL + "import com.google.common.collect.ImmutableList;" + NL + "import com.google.common.collect.Iterables;" + NL + "import com.google.common.collect.Lists;" + NL + "import com.porpoise.dao.database.DbConnectionFactory;" + NL + "import com.porpoise.dao.database.dao.AbstractDao;" + NL + "import com.porpoise.dao.database.visitors.AbstractResultSetVisitor;" + NL + "" + NL + "/**" + NL + " * Functional Data Access class for AaronDto objects" + NL + " */" + NL + "public class ";
  protected final String TEXT_6 = "Dao extends AbstractDao" + NL + "{" + NL + "    ;// uninstantiable" + NL + "    " + NL + "    private static class Visitor extends AbstractResultSetVisitor {" + NL + "        " + NL + "        private final List<AaronDto> dtoResults;" + NL + "" + NL + "        public Visitor()" + NL + "        {" + NL + "            this.dtoResults = Lists.newArrayList();" + NL + "        }" + NL + "        " + NL + "        @Override" + NL + "        public boolean onResultSet(final ResultSet resultSet) throws SQLException" + NL + "        {" + NL + "            int columnIndex = 1;" + NL + "            final BigDecimal amount = getBigDecimal(resultSet, columnIndex++);" + NL + "            final String name = getString(resultSet, columnIndex++);" + NL + "            final AaronDto dto  = new AaronDto(name, amount);" + NL + "            this.dtoResults.add(dto);" + NL + "            return true;" + NL + "        }" + NL + "" + NL + "        /**" + NL + "         * @return the dtoResults" + NL + "         */" + NL + "        public List<AaronDto> getDtoResults()" + NL + "        {" + NL + "            return ImmutableList.copyOf(this.dtoResults);" + NL + "        }" + NL + "" + NL + "        public AaronDto getSingleResult()" + NL + "        {" + NL + "            return Iterables.getOnlyElement(this.dtoResults);" + NL + "        }" + NL + "        " + NL + "    }" + NL + "    " + NL + "    /**" + NL + "     * @param factory" + NL + "     * @param id" + NL + "     * @return" + NL + "     */" + NL + "    public AaronDto byId(final DbConnectionFactory factory, final Long id)" + NL + "    {" + NL + "        final String querySql = AaronSql.byId(id);" + NL + "        final Visitor visitor = factory.executeQueryInSingleTransaction(new Visitor(), querySql, id);" + NL + "        return visitor.getSingleResult();" + NL + "    }" + NL + "    " + NL + "}";
  protected final String TEXT_7 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
     final DaoContext ctxt = (DaoContext) argument; 
final String n = ctxt.getName();

    stringBuffer.append(TEXT_2);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_4);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    return stringBuffer.toString();
  }
}
