package com.porpoise.dao.generator.templates;

import com.porpoise.generator.*;
import java.util.*;
import com.porpoise.dao.generator.gen.*;
import com.porpoise.dao.generator.model.*;
import com.porpoise.generator.model.*;

public class SqlTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized SqlTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    SqlTemplate result = new SqlTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";" + NL + "" + NL + "import ";
  protected final String TEXT_3 = ".model.";
  protected final String TEXT_4 = "Dto;" + NL + "import ";
  protected final String TEXT_5 = ".model.";
  protected final String TEXT_6 = "Metadata;" + NL + "" + NL + "import com.porpoise.dao.database.metadata.Field;" + NL + "import static com.google.common.base.Strings.*;" + NL + "import com.google.common.base.Function;" + NL + "import com.google.common.collect.MapMaker;" + NL + "" + NL + "import java.math.BigDecimal;" + NL + "import java.sql.ResultSet;" + NL + "import java.sql.SQLException;" + NL + "import java.util.concurrent.ConcurrentMap;" + NL + "import java.util.List;" + NL + "import java.util.Map;" + NL + "" + NL + "" + NL + "/**" + NL + " * Class used to encapsulate the SQL required for interacting with an underlying relational database for ";
  protected final String TEXT_7 = " object" + NL + " */" + NL + "public class ";
  protected final String TEXT_8 = "Sql" + NL + "{" + NL + "    private static final String TABLE_NAME = \"";
  protected final String TEXT_9 = "\";" + NL + "" + NL + "    // cached (computing) maps for query strings" + NL + "    private static final ConcurrentMap<String, String> attributeListAsStringMap;" + NL + "    private static final ConcurrentMap<String, String> attributeListAsUpdateStringMap;" + NL + "    private static final ConcurrentMap<String, String> selectByAliasMap;" + NL + "" + NL + "\tstatic {" + NL + "\t\tattributeListAsStringMap = new MapMaker().initialCapacity(3)" + NL + "\t\t\t\t.makeComputingMap(new Function<String, String>() {" + NL + "\t\t\t\t\t@Override" + NL + "\t\t\t\t\tpublic String apply(final String alias) {" + NL + "\t\t\t\t\t\treturn Field.asAttributeList(alias," + NL + "\t\t\t\t\t\t\t\t";
  protected final String TEXT_10 = "Metadata.FIELDS);" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t});" + NL + "" + NL + "\t\tattributeListAsUpdateStringMap = new MapMaker().initialCapacity(3)" + NL + "\t\t\t\t.makeComputingMap(new Function<String, String>() {" + NL + "\t\t\t\t\t@Override" + NL + "\t\t\t\t\tpublic String apply(final String alias) {" + NL + "\t\t\t\t\t\treturn Field.asUpdateList(alias, ";
  protected final String TEXT_11 = "Metadata.FIELDS);" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t});" + NL + "\t\tselectByAliasMap = new MapMaker().initialCapacity(3).makeComputingMap(" + NL + "\t\t\t\tnew Function<String, String>() {" + NL + "\t\t\t\t\t@Override" + NL + "\t\t\t\t\tpublic String apply(final String alias) {" + NL + "\t\t\t\t\t\treturn String.format(\"SELECT %s FROM %s %s\"," + NL + "\t\t\t\t\t\t\t\tattributeListAsString(alias), TABLE_NAME," + NL + "\t\t\t\t\t\t\t\tnullToEmpty(alias));" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t});" + NL + "\t}" + NL + "" + NL + "    /**" + NL + "     * @return a comma-separated list of ";
  protected final String TEXT_12 = " attributes in string form " + NL + "     */" + NL + "    public static String attributeListAsString(final String alias)" + NL + "    {" + NL + "        return attributeListAsStringMap.get(nullToEmpty(alias));" + NL + "    }" + NL + "    " + NL + "    /**" + NL + "     * @return the ";
  protected final String TEXT_13 = " attributes in a comma-separated &quot;KEY-N=?&quot; form  " + NL + "     */" + NL + "    public static String attributeListAsUpdateString(final String alias)" + NL + "    {" + NL + "        return attributeListAsUpdateStringMap.get(nullToEmpty(alias));" + NL + "    }" + NL;
  protected final String TEXT_14 = NL + "    /**" + NL + "     * @return the 'WHERE' clause for retrieving a ";
  protected final String TEXT_15 = " object by ID" + NL + "     */" + NL + "    public static String byId()" + NL + "    {" + NL + "        return by(";
  protected final String TEXT_16 = "Metadata.ID_FIELD);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @return a query which will return the max id" + NL + "     */" + NL + "\tpublic static String selectMaxId() {" + NL + "\t\treturn String.format(\"SELECT MAX(";
  protected final String TEXT_17 = ") FROM %s\", TABLE_NAME);" + NL + "\t}" + NL + "\t" + NL + "    /**" + NL + "     * @return a query which will return the number of rows in the database for the given table" + NL + "     */" + NL + "\tpublic static String selectCount() {" + NL + "\t\treturn String.format(\"SELECT COUNT(";
  protected final String TEXT_18 = ") FROM %s\", TABLE_NAME);" + NL + "\t}" + NL + "\t" + NL + "    /**" + NL + "     * @return a query which will return the max id" + NL + "     */" + NL + "\tpublic static String selectId() {" + NL + "\t\treturn String.format(\"SELECT ";
  protected final String TEXT_19 = " FROM %s\", TABLE_NAME);" + NL + "\t}";
  protected final String TEXT_20 = NL;
  protected final String TEXT_21 = NL + NL + "    /**" + NL + "     * Relationship: ";
  protected final String TEXT_22 = NL + "     * ";
  protected final String TEXT_23 = " => ";
  protected final String TEXT_24 = NL + "     * pk is ";
  protected final String TEXT_25 = NL + "     * id is table (";
  protected final String TEXT_26 = ") + Id" + NL + "     */" + NL + "    public static String findBy";
  protected final String TEXT_27 = "()" + NL + "    {" + NL + "        final String select = select(\"a\");" + NL + "        final StringBuilder querySql = new StringBuilder(select);" + NL + "        querySql.append(\", ";
  protected final String TEXT_28 = " b\");" + NL + "        ";
  protected final String TEXT_29 = NL + "        querySql.append(\"WHERE b.";
  protected final String TEXT_30 = "=a.";
  protected final String TEXT_31 = " \");";
  protected final String TEXT_32 = " " + NL + "        querySql.append(\"WHERE b.";
  protected final String TEXT_33 = "=a.";
  protected final String TEXT_34 = " \");";
  protected final String TEXT_35 = NL + "        querySql.append(\"AND b.";
  protected final String TEXT_36 = "=?\");" + NL + "        final String sql = querySql.toString();" + NL + "        return sql;" + NL + "    }" + NL;
  protected final String TEXT_37 = NL + NL + NL + "    /**" + NL + "     * @return the 'WHERE' clause for retrieving a ";
  protected final String TEXT_38 = " object by its given field property" + NL + "     */" + NL + "    public static String by(final Field<?> field)" + NL + "    {" + NL + "        return String.format(\"%s WHERE %s=?\", select(), field.getName());" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @return the 'INSERT' SQL used to create a new ";
  protected final String TEXT_39 = " entry" + NL + "     */" + NL + "    public static String insert()" + NL + "    {" + NL + "        final String propertyPlaceholders = repeat(\"?,\", ";
  protected final String TEXT_40 = "Metadata.FIELDS.size() - 1) + \"?\";" + NL + "        return String.format(\"INSERT INTO %s (%s) VALUES (%s)\", TABLE_NAME, attributeListAsString(null), propertyPlaceholders);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @return the 'UPDATE' SQL used to create a new ";
  protected final String TEXT_41 = " entry" + NL + "     */" + NL + "    public static String update()" + NL + "    {" + NL + "        return String.format(\"UPDATE %s SET %s\", TABLE_NAME, attributeListAsUpdateString(null));" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @return the 'SELECT' SQL for querying the database" + NL + "     */" + NL + "    public static String select()" + NL + "    {" + NL + "        return select(null);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @return the 'SELECT' SQL for querying the database using the given alias for the table entry" + NL + "     */" + NL + "    public static String select(final String alias)" + NL + "    {" + NL + "        return selectByAliasMap.get(nullToEmpty(alias));" + NL + "    }" + NL + "    " + NL + "}";
  protected final String TEXT_42 = NL;

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
    stringBuffer.append( n);
    stringBuffer.append(TEXT_4);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( n);
    stringBuffer.append(TEXT_6);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_8);
    stringBuffer.append(ctxt.getTableName() );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_10);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_11);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_13);
     if (ctxt.hasIdField()) { 
    stringBuffer.append(TEXT_14);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_15);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( ctxt.getIdField().getName() );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( ctxt.getIdField().getName() );
    stringBuffer.append(TEXT_18);
    stringBuffer.append( ctxt.getIdField().getName() );
    stringBuffer.append(TEXT_19);
     } 
    stringBuffer.append(TEXT_20);
     for (final Reference r : ctxt.getReferencesToThisTable()) { 
final Column from = ctxt.resolveColumn(r.getFrom());
final Column pk = ctxt.resolvePrimaryKey(from);
final String id = pk.getNameAsJava();


    stringBuffer.append(TEXT_21);
    stringBuffer.append( r );
    stringBuffer.append(TEXT_22);
    stringBuffer.append( r.getFrom() );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( from );
    stringBuffer.append(TEXT_24);
    stringBuffer.append( pk );
    stringBuffer.append(TEXT_25);
    stringBuffer.append( pk.getTable() );
    stringBuffer.append(TEXT_26);
    stringBuffer.append( id );
    stringBuffer.append(TEXT_27);
    stringBuffer.append(  r.getFrom().getTable().getTableName() );
    stringBuffer.append(TEXT_28);
     if (r.getCardinality() == Cardinality.ManyToMany) { 
    stringBuffer.append(TEXT_29);
    stringBuffer.append( r.getFrom().getName() );
    stringBuffer.append(TEXT_30);
    stringBuffer.append( r.getTo().getName() );
    stringBuffer.append(TEXT_31);
     } else { 
    stringBuffer.append(TEXT_32);
    stringBuffer.append( from.getName() );
    stringBuffer.append(TEXT_33);
    stringBuffer.append( r.getTo().getName() );
    stringBuffer.append(TEXT_34);
     } // end if 
    stringBuffer.append(TEXT_35);
    stringBuffer.append( pk.getName() );
    stringBuffer.append(TEXT_36);
     } // end for 
    stringBuffer.append(TEXT_37);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_38);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_39);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_40);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_41);
    stringBuffer.append(TEXT_42);
    return stringBuffer.toString();
  }
}