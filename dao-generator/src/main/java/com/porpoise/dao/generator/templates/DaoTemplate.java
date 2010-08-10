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
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";" + NL + "" + NL + "import java.math.BigDecimal;" + NL + "import java.sql.ResultSet;" + NL + "import java.sql.SQLException;" + NL + "import java.util.Collection;" + NL + "import java.util.List;" + NL + "import java.util.Date;" + NL + "import java.util.Collection;" + NL + "" + NL + "import ";
  protected final String TEXT_3 = ".model.";
  protected final String TEXT_4 = "Dto;" + NL + "" + NL + "import com.google.common.collect.ImmutableList;" + NL + "import com.google.common.collect.Iterables;" + NL + "import com.google.common.collect.Lists;" + NL + "import com.porpoise.dao.database.IDbTransaction;" + NL + "import com.porpoise.dao.database.DbConnectionFactory;" + NL + "import com.porpoise.dao.database.dao.AbstractDao;" + NL + "import com.porpoise.dao.database.visitors.AbstractResultSetVisitor;" + NL + "import com.porpoise.dao.database.visitors.SingleScalarResultVisitor;" + NL + "" + NL + "/**" + NL + " * Functional Data Access class used to operate on ";
  protected final String TEXT_5 = "Dto objects" + NL + " */" + NL + "public class ";
  protected final String TEXT_6 = "Dao extends AbstractDao" + NL + "{" + NL + "    /**" + NL + "     * This 'visitor' is able to transform ResultSets into ";
  protected final String TEXT_7 = "Dto instances " + NL + "     */" + NL + "    private static class Visitor extends AbstractResultSetVisitor {" + NL + "        " + NL + "        private final List<";
  protected final String TEXT_8 = "Dto> dtoResults;" + NL + "" + NL + "        public Visitor()" + NL + "        {" + NL + "            this.dtoResults = Lists.newArrayList();" + NL + "        }" + NL + "        " + NL + "        @Override" + NL + "        public boolean onResultSet(final ResultSet resultSet) throws SQLException" + NL + "        {" + NL + "            int columnIndex = 1;" + NL;
  protected final String TEXT_9 = NL + "            final ";
  protected final String TEXT_10 = " ";
  protected final String TEXT_11 = " = ";
  protected final String TEXT_12 = "(resultSet, columnIndex++);";
  protected final String TEXT_13 = NL + NL + "            final ";
  protected final String TEXT_14 = "Dto dto  = new ";
  protected final String TEXT_15 = "Dto(";
  protected final String TEXT_16 = ");" + NL + "            this.dtoResults.add(dto);" + NL + "            return true;" + NL + "        }" + NL + "" + NL + "        /**" + NL + "         * @return the results as a DTO object" + NL + "         */" + NL + "        public List<";
  protected final String TEXT_17 = "Dto> getDtoResults()" + NL + "        {" + NL + "            return ImmutableList.copyOf(this.dtoResults);" + NL + "        }" + NL + "" + NL + "        /**" + NL + "         * convenience method used when only a single ";
  protected final String TEXT_18 = "Dto entry was expected" + NL + "         * @return the single ";
  protected final String TEXT_19 = "Dto instance " + NL + "         * @throws NoSuchElementException if no results were returned" + NL + "         * @throws IllegalArgumentException if multiple results were returned" + NL + "         */" + NL + "        public ";
  protected final String TEXT_20 = "Dto getSingleResult()" + NL + "        {" + NL + "            return Iterables.getOnlyElement(this.dtoResults);" + NL + "        }" + NL + "    }" + NL + "    ";
  protected final String TEXT_21 = NL + "    /**" + NL + "     * @param transaction the transaction used to retrieve the object" + NL + "     * @param id the Id for the ";
  protected final String TEXT_22 = " object" + NL + "     * @return the ";
  protected final String TEXT_23 = " object with the given ID" + NL + "     */" + NL + "    public ";
  protected final String TEXT_24 = "Dto findById(final IDbTransaction transaction, final ";
  protected final String TEXT_25 = " id)" + NL + "    {" + NL + "        final String querySql = ";
  protected final String TEXT_26 = "Sql.byId();" + NL + "        final Visitor visitor = transaction.executeQuery(new Visitor(), querySql, id);" + NL + "        return visitor.getSingleResult();" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @return the next available ID" + NL + "     */" + NL + "\tpublic ";
  protected final String TEXT_27 = " nextId(final IDbTransaction transaction) {" + NL + "\t    final ";
  protected final String TEXT_28 = " max = transaction.executeQuery(new SingleScalarResultVisitor()," + NL + "\t\t\t\t";
  protected final String TEXT_29 = "Sql.selectMaxId()).get";
  protected final String TEXT_30 = "();" + NL + "\t    if (max == null)" + NL + "\t    {" + NL + "            return ";
  protected final String TEXT_31 = ".valueOf(1);" + NL + "\t    }" + NL + "\t\treturn ";
  protected final String TEXT_32 = ".valueOf(max + 1);" + NL + "\t}" + NL + "\t";
  protected final String TEXT_33 = NL;
  protected final String TEXT_34 = NL + NL + "    /**" + NL + "     * @param transaction the transaction used to retrieve the object" + NL + "     * @param id the Id for the ";
  protected final String TEXT_35 = " object" + NL + "     * @return the ";
  protected final String TEXT_36 = " object with the given ID" + NL + "     */" + NL + "    public Collection<";
  protected final String TEXT_37 = "Dto> findBy";
  protected final String TEXT_38 = "(" + NL + "    final IDbTransaction transaction, final ";
  protected final String TEXT_39 = " id)" + NL + "    {" + NL + "        final String sql = ";
  protected final String TEXT_40 = "Sql.findBy";
  protected final String TEXT_41 = "();" + NL + "        final Visitor visitor = transaction.executeQuery(new Visitor(), sql, id);" + NL + "        return visitor.getDtoResults();" + NL + "    }" + NL;
  protected final String TEXT_42 = NL + "    " + NL + "" + NL + "    /**" + NL + "     * @param transaction the transaction used to retrieve the object" + NL + "     * @return all ";
  protected final String TEXT_43 = " objects" + NL + "     */" + NL + "    public Collection<";
  protected final String TEXT_44 = "Dto> listAll(final IDbTransaction transaction)" + NL + "    {" + NL + "        final String querySql = ";
  protected final String TEXT_45 = "Sql.select();" + NL + "        final Visitor visitor = transaction.executeQuery(new Visitor(), querySql);" + NL + "        return visitor.getDtoResults();" + NL + "    }" + NL + "" + NL + "    /** " + NL + "     * create a new entry for the given dto" + NL + "     * @param transaction" + NL + "     * @param dto the dto to insert" + NL + "     */" + NL + "    public void insert(final IDbTransaction transaction, final ";
  protected final String TEXT_46 = "Dto dto)" + NL + "    {" + NL + "        final String sql = ";
  protected final String TEXT_47 = "Sql.insert();" + NL + "        transaction.executeUpdate(sql, ";
  protected final String TEXT_48 = "); " + NL + "    }    " + NL;
  protected final String TEXT_49 = NL + "    /** " + NL + "     * @param transaction" + NL + "     * @param id the ID of the element to update" + NL + "     * @param dto" + NL + "     */" + NL + "    public <T> void update(final IDbTransaction transaction, T id, final ";
  protected final String TEXT_50 = "Dto dto)" + NL + "    {" + NL + "        final String sql = ";
  protected final String TEXT_51 = "Sql.update() + \" WHERE id=?\";" + NL + "        transaction.executeUpdate(sql, ";
  protected final String TEXT_52 = ", id); " + NL + "    }    ";
  protected final String TEXT_53 = NL + "}";
  protected final String TEXT_54 = NL;

   /* (non-javadoc)
    * @see IGenerator#generate(Object)
    */
   public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     final DaoContext ctxt = (DaoContext) argument; 
final String n = ctxt.getJavaName();

    stringBuffer.append(TEXT_1);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_2);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( n );
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
    stringBuffer.append( c.getResultSetAccessorName() );
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
     if (ctxt.hasIdField()) { 
    stringBuffer.append(TEXT_21);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_22);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_24);
    stringBuffer.append( ctxt.getIdField().getJavaTypeName() );
    stringBuffer.append(TEXT_25);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_26);
    stringBuffer.append( ctxt.getIdField().getJavaTypeName() );
    stringBuffer.append(TEXT_27);
    stringBuffer.append( ctxt.getIdField().getJavaTypeName() );
    stringBuffer.append(TEXT_28);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_29);
    stringBuffer.append( ctxt.getIdField().getJavaTypeName() );
    stringBuffer.append(TEXT_30);
    stringBuffer.append( ctxt.getIdField().getJavaTypeName() );
    stringBuffer.append(TEXT_31);
    stringBuffer.append( ctxt.getIdField().getJavaTypeName() );
    stringBuffer.append(TEXT_32);
     } 
    stringBuffer.append(TEXT_33);
     for (final Reference r : ctxt.getReferencesToThisTable()) { 
    stringBuffer.append(TEXT_34);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_35);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_36);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_37);
    stringBuffer.append( ctxt.getReferenceName(r) );
    stringBuffer.append(TEXT_38);
    stringBuffer.append( r.getFromTablePrimaryKey().getJavaTypeName() );
    stringBuffer.append(TEXT_39);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_40);
    stringBuffer.append( ctxt.getReferenceName(r) );
    stringBuffer.append(TEXT_41);
     } 
    stringBuffer.append(TEXT_42);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_43);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_44);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_45);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_46);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_47);
    stringBuffer.append( ctxt.getColumnAccessorMethods("dto") );
    stringBuffer.append(TEXT_48);
     if (ctxt.hasIdField()) { 
    stringBuffer.append(TEXT_49);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_50);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_51);
    stringBuffer.append( ctxt.getColumnAccessorMethods("dto") );
    stringBuffer.append(TEXT_52);
     } 
    stringBuffer.append(TEXT_53);
    stringBuffer.append(TEXT_54);
    return stringBuffer.toString();
  }
}