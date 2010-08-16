package com.porpoise.dao.generator.templates;

import com.porpoise.generator.*;
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
  protected final String TEXT_4 = "Dto;" + NL + "" + NL + "import com.google.common.collect.ImmutableList;" + NL + "import com.google.common.collect.Iterables;" + NL + "import com.google.common.collect.Lists;" + NL + "import com.porpoise.dao.database.IDbTransaction;" + NL + "import com.porpoise.dao.database.DbConnectionFactory;" + NL + "import com.porpoise.dao.database.visitors.*;";
  protected final String TEXT_5 = NL + "import com.porpoise.dao.database.dao.AbstractDao;";
  protected final String TEXT_6 = NL + NL + "/**" + NL + " * Functional Data Access class used to operate on ";
  protected final String TEXT_7 = "Dto objects";
  protected final String TEXT_8 = NL + " ";
  protected final String TEXT_9 = NL + " */" + NL + "public class ";
  protected final String TEXT_10 = "Dao extends";
  protected final String TEXT_11 = " AbstractSingleIdDao<";
  protected final String TEXT_12 = ", ";
  protected final String TEXT_13 = "Dto> ";
  protected final String TEXT_14 = " AbstractDao ";
  protected final String TEXT_15 = NL + "{" + NL + "    /**" + NL + "     * This 'visitor' is able to transform ResultSets into ";
  protected final String TEXT_16 = "Dto instances " + NL + "     */" + NL + "    private static class Visitor extends AbstractResultSetVisitor {" + NL + "        " + NL + "        private final List<";
  protected final String TEXT_17 = "Dto> dtoResults;" + NL + "" + NL + "        public Visitor()" + NL + "        {" + NL + "            this.dtoResults = Lists.newArrayList();" + NL + "        }" + NL + "        " + NL + "        @Override" + NL + "        public boolean onResultSet(final ResultSet resultSet) throws SQLException" + NL + "        {" + NL + "            int columnIndex = 1;" + NL;
  protected final String TEXT_18 = NL + "            final ";
  protected final String TEXT_19 = " ";
  protected final String TEXT_20 = " = ";
  protected final String TEXT_21 = "(resultSet, columnIndex++);";
  protected final String TEXT_22 = NL + NL + "            final ";
  protected final String TEXT_23 = "Dto dto  = new ";
  protected final String TEXT_24 = "Dto(";
  protected final String TEXT_25 = ");" + NL + "            this.dtoResults.add(dto);" + NL + "            return true;" + NL + "        }" + NL + "" + NL + "        /**" + NL + "         * @return the results as a DTO object" + NL + "         */" + NL + "        public List<";
  protected final String TEXT_26 = "Dto> getDtoResults()" + NL + "        {" + NL + "            return ImmutableList.copyOf(this.dtoResults);" + NL + "        }" + NL + "" + NL + "        /**" + NL + "         * convenience method used when only a single ";
  protected final String TEXT_27 = "Dto entry was expected" + NL + "         * @return the single ";
  protected final String TEXT_28 = "Dto instance " + NL + "         * @throws NoSuchElementException if no results were returned" + NL + "         * @throws IllegalArgumentException if multiple results were returned" + NL + "         */" + NL + "        public ";
  protected final String TEXT_29 = "Dto getSingleResult()" + NL + "        {" + NL + "            if (this.dtoResults.isEmpty())" + NL + "            {" + NL + "                return null;" + NL + "            }" + NL + "            return Iterables.getOnlyElement(this.dtoResults);" + NL + "        }" + NL + "    }" + NL + "    ";
  protected final String TEXT_30 = NL + "    /**" + NL + "     * @param transaction the transaction used to retrieve the object" + NL + "     * @param id the Id for the ";
  protected final String TEXT_31 = " object" + NL + "     * @return the ";
  protected final String TEXT_32 = " object with the given ID" + NL + "     */" + NL + "    public ";
  protected final String TEXT_33 = "Dto findById(final IDbTransaction transaction, final ";
  protected final String TEXT_34 = " id)" + NL + "    {" + NL + "        final String querySql = ";
  protected final String TEXT_35 = "Sql.byId();" + NL + "        final Visitor visitor = transaction.executeQuery(new Visitor(), querySql, id);" + NL + "        return visitor.getSingleResult();" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @return the next available ID" + NL + "     */" + NL + "\tpublic ";
  protected final String TEXT_36 = " nextId(final IDbTransaction transaction) {" + NL + "\t    final ";
  protected final String TEXT_37 = " max = transaction.executeQuery(new SingleScalarResultVisitor()," + NL + "\t\t\t\t";
  protected final String TEXT_38 = "Sql.selectMaxId()).get";
  protected final String TEXT_39 = "();" + NL + "\t    if (max == null)" + NL + "\t    {" + NL + "            return ";
  protected final String TEXT_40 = ".valueOf(1);" + NL + "\t    }" + NL + "\t\treturn ";
  protected final String TEXT_41 = ".valueOf(max + 1);" + NL + "\t}" + NL + "" + NL + "    /** " + NL + "     * @return the select count query" + NL + "     */" + NL + "    @Override" + NL + "    protected String getSelectCountQuery()" + NL + "    {" + NL + "        return ";
  protected final String TEXT_42 = "Sql.selectCount();" + NL + "    }" + NL + "" + NL + "    /** " + NL + "     * @return the select Id query" + NL + "     */" + NL + "    @Override" + NL + "    protected  String getSelectIdQuery()" + NL + "    {" + NL + "        return ";
  protected final String TEXT_43 = "Sql.selectId();" + NL + "    }" + NL + "\t";
  protected final String TEXT_44 = NL;
  protected final String TEXT_45 = NL + NL + "    /**" + NL + "     * ";
  protected final String TEXT_46 = " ==> ";
  protected final String TEXT_47 = NL + "     * PK is ";
  protected final String TEXT_48 = NL + "     *" + NL + "     * @param transaction the transaction used to retrieve the object" + NL + "     * @param id the Id for the ";
  protected final String TEXT_49 = " object" + NL + "     * @return the ";
  protected final String TEXT_50 = " object with the given ID" + NL + "     */" + NL + "    public Collection<";
  protected final String TEXT_51 = "Dto> ";
  protected final String TEXT_52 = "(" + NL + "    final IDbTransaction transaction, final ";
  protected final String TEXT_53 = " id)" + NL + "    {" + NL + "        final String sql = ";
  protected final String TEXT_54 = "Sql.";
  protected final String TEXT_55 = "();" + NL + "        final Visitor visitor = transaction.executeQuery(new Visitor(), sql, id);" + NL + "        return visitor.getDtoResults();" + NL + "    }" + NL;
  protected final String TEXT_56 = NL + NL + "    /**" + NL + "     * @param transaction the transaction used to retrieve the object" + NL + "     * @return all ";
  protected final String TEXT_57 = " objects" + NL + "     */" + NL + "    public Collection<";
  protected final String TEXT_58 = "Dto> listAll(final IDbTransaction transaction)" + NL + "    {" + NL + "        final String querySql = ";
  protected final String TEXT_59 = "Sql.select();" + NL + "        final Visitor visitor = transaction.executeQuery(new Visitor(), querySql);" + NL + "        return visitor.getDtoResults();" + NL + "    }" + NL + "" + NL + "    /** " + NL + "     * create a new entry for the given dto" + NL + "     * @param transaction" + NL + "     * @param dto the dto to insert" + NL + "     */" + NL + "    public void insert(final IDbTransaction transaction, final ";
  protected final String TEXT_60 = "Dto dto)" + NL + "    {" + NL + "        final String sql = ";
  protected final String TEXT_61 = "Sql.insert();" + NL + "        transaction.executeUpdate(sql, ";
  protected final String TEXT_62 = "); " + NL + "    }    " + NL;
  protected final String TEXT_63 = NL + "    /** " + NL + "     * @param transaction" + NL + "     * @param id the ID of the element to update" + NL + "     * @param dto" + NL + "     */" + NL + "    public void update(final IDbTransaction transaction, ";
  protected final String TEXT_64 = " id, final ";
  protected final String TEXT_65 = "Dto dto)" + NL + "    {" + NL + "        final String sql = ";
  protected final String TEXT_66 = "Sql.update() + \" WHERE id=?\";" + NL + "        transaction.executeUpdate(sql, ";
  protected final String TEXT_67 = ", id); " + NL + "    }    ";
  protected final String TEXT_68 = NL + "}";
  protected final String TEXT_69 = NL;

   /* (non-javadoc)
    * @see IGenerator#generate(Object)
    */
   public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     final DaoContext ctxt = (DaoContext) argument; 
final String n = ctxt.getJavaName();

final String key;
if (ctxt.hasIdField()) {
    key = ctxt.getIdField().getJavaTypeName();
}
else
{
    key = "Long";
}

    stringBuffer.append(TEXT_1);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_2);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_4);
     if (!ctxt.hasIdField()) { 
    stringBuffer.append(TEXT_5);
     } 
    stringBuffer.append(TEXT_6);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_7);
    stringBuffer.append(TEXT_8);
    stringBuffer.append( ctxt.getReferencesToThisTable() );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_10);
     if (ctxt.hasIdField()) { 
    stringBuffer.append(TEXT_11);
    stringBuffer.append( key );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_13);
    } else {
    stringBuffer.append(TEXT_14);
    }
    stringBuffer.append(TEXT_15);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_17);
     for (final Column c : ctxt.getColumns()) { 
    stringBuffer.append(TEXT_18);
    stringBuffer.append( c.getJavaTypeName() );
    stringBuffer.append(TEXT_19);
    stringBuffer.append( ctxt.asProperty(c.getName()) );
    stringBuffer.append(TEXT_20);
    stringBuffer.append( c.getResultSetAccessorName() );
    stringBuffer.append(TEXT_21);
    } // end for 
    stringBuffer.append(TEXT_22);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_24);
    stringBuffer.append(  ctxt.getParameterList() );
    stringBuffer.append(TEXT_25);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_26);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_27);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_28);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_29);
     if (ctxt.hasIdField()) { 
    stringBuffer.append(TEXT_30);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_31);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_32);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_33);
    stringBuffer.append( key );
    stringBuffer.append(TEXT_34);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_35);
    stringBuffer.append( key );
    stringBuffer.append(TEXT_36);
    stringBuffer.append( key );
    stringBuffer.append(TEXT_37);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_38);
    stringBuffer.append( key );
    stringBuffer.append(TEXT_39);
    stringBuffer.append( key );
    stringBuffer.append(TEXT_40);
    stringBuffer.append( key );
    stringBuffer.append(TEXT_41);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_42);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_43);
     } // end if has Id 
    stringBuffer.append(TEXT_44);
     for (final Reference r : ctxt.getReferencesToThisTable()) { 

final Column col = ctxt.resolveColumn(r.getFrom());
final Column pk = ctxt.resolvePrimaryKey(col);
final String id = pk.getTable().getJavaName() + "Id";
final String methodName = "findBy" + pk.getNameAsJava();

    stringBuffer.append(TEXT_45);
    stringBuffer.append( r.getFrom() );
    stringBuffer.append(TEXT_46);
    stringBuffer.append( col );
    stringBuffer.append(TEXT_47);
    stringBuffer.append( pk );
    stringBuffer.append(TEXT_48);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_49);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_50);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_51);
    stringBuffer.append( methodName );
    stringBuffer.append(TEXT_52);
    stringBuffer.append( pk.getJavaTypeName() );
    stringBuffer.append(TEXT_53);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_54);
    stringBuffer.append( methodName );
    stringBuffer.append(TEXT_55);
     } 
    stringBuffer.append(TEXT_56);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_57);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_58);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_59);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_60);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_61);
    stringBuffer.append( ctxt.getAccessorMethods("dto") );
    stringBuffer.append(TEXT_62);
     if (ctxt.hasIdField()) { 
    stringBuffer.append(TEXT_63);
    stringBuffer.append( key );
    stringBuffer.append(TEXT_64);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_65);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_66);
    stringBuffer.append( ctxt.getAccessorMethods("dto") );
    stringBuffer.append(TEXT_67);
     } 
    stringBuffer.append(TEXT_68);
    stringBuffer.append(TEXT_69);
    return stringBuffer.toString();
  }
}