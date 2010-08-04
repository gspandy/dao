package com.porpoise.dao.generator.templates;

import java.util.*;
import com.porpoise.dao.generator.gen.*;
import com.porpoise.dao.generator.model.*;

public class DaoTemplate implements IGenerator
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
  protected final String TEXT_5 = "Dto;" + NL + "" + NL + "import com.google.common.collect.ImmutableList;" + NL + "import com.google.common.collect.Iterables;" + NL + "import com.google.common.collect.Lists;" + NL + "import com.porpoise.dao.database.DbConnectionFactory;" + NL + "import com.porpoise.dao.database.dao.AbstractDao;" + NL + "import com.porpoise.dao.database.visitors.AbstractResultSetVisitor;" + NL + "" + NL + "/**" + NL + " * Functional Data Access class for ";
  protected final String TEXT_6 = "Dto objects" + NL + " */" + NL + "public class ";
  protected final String TEXT_7 = "Dao extends AbstractDao" + NL + "{" + NL + "    ;// uninstantiable" + NL + "    " + NL + "    private static class Visitor extends AbstractResultSetVisitor {" + NL + "        " + NL + "        private final List<";
  protected final String TEXT_8 = "Dto> dtoResults;" + NL + "" + NL + "        public Visitor()" + NL + "        {" + NL + "            this.dtoResults = Lists.newArrayList();" + NL + "        }" + NL + "        " + NL + "        @Override" + NL + "        public boolean onResultSet(final ResultSet resultSet) throws SQLException" + NL + "        {" + NL + "            int columnIndex = 1;" + NL;
  protected final String TEXT_9 = NL + "            final ";
  protected final String TEXT_10 = " ";
  protected final String TEXT_11 = " = get";
  protected final String TEXT_12 = "(resultSet, columnIndex++);";
  protected final String TEXT_13 = NL + NL + "            final ";
  protected final String TEXT_14 = "Dto dto  = new ";
  protected final String TEXT_15 = "Dto(";
  protected final String TEXT_16 = ");" + NL + "            this.dtoResults.add(dto);" + NL + "            return true;" + NL + "        }" + NL + "" + NL + "        /**" + NL + "         * @return the dtoResults" + NL + "         */" + NL + "        public List<";
  protected final String TEXT_17 = "Dto> getDtoResults()" + NL + "        {" + NL + "            return ImmutableList.copyOf(this.dtoResults);" + NL + "        }" + NL + "" + NL + "        public ";
  protected final String TEXT_18 = "Dto getSingleResult()" + NL + "        {" + NL + "            return Iterables.getOnlyElement(this.dtoResults);" + NL + "        }" + NL + "        " + NL + "    }" + NL + "    " + NL + "    /**" + NL + "     * @param factory" + NL + "     * @param id" + NL + "     * @return" + NL + "     */" + NL + "    public ";
  protected final String TEXT_19 = "Dto findById(final DbConnectionFactory factory, final Long id)" + NL + "    {" + NL + "        final String querySql = ";
  protected final String TEXT_20 = "Sql.byId();" + NL + "        final Visitor visitor = factory.executeQueryInSingleTransaction(new Visitor(), querySql, id);" + NL + "        return visitor.getSingleResult();" + NL + "    }" + NL + "    " + NL + "}";
  protected final String TEXT_21 = NL;

   /* (non-javadoc)
    * @see IGenerator#generate(Object)
    */
   public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
     final DaoContext ctxt = (DaoContext) argument; 
final String n = ctxt.getJavaName();

    stringBuffer.append(TEXT_2);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_4);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_6);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_8);
     for (final Column c : ctxt.getColumns()) { 
    stringBuffer.append(TEXT_9);
    stringBuffer.append( c.getJavaTypeName() );
    stringBuffer.append(TEXT_10);
    stringBuffer.append( ctxt.asProperty(c.getName()) );
    stringBuffer.append(TEXT_11);
    stringBuffer.append( c.getJavaTypeName() );
    stringBuffer.append(TEXT_12);
    } // end for 
    stringBuffer.append(TEXT_13);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_14);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_15);
    stringBuffer.append(  ctxt.getColumnParameterList() );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_18);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_19);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_20);
    stringBuffer.append(TEXT_21);
    return stringBuffer.toString();
  }
}