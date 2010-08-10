package com.porpoise.dao.generator.templates;

import java.util.*;
import com.porpoise.dao.generator.gen.*;
import com.porpoise.dao.generator.model.*;

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
  protected final String TEXT_6 = "Metadata;" + NL + "" + NL + "import com.porpoise.dao.database.metadata.Field;" + NL + "import com.google.common.base.Strings;" + NL + "" + NL + "import java.math.BigDecimal;" + NL + "import java.sql.ResultSet;" + NL + "import java.sql.SQLException;" + NL + "import java.util.List;" + NL + "" + NL + "" + NL + "/**" + NL + " * Class used to encapsulate the SQL required for interacting with an underlying relational database for ";
  protected final String TEXT_7 = " object" + NL + " */" + NL + "public class ";
  protected final String TEXT_8 = "Sql" + NL + "{" + NL + "    ;// uninstantiable" + NL + "" + NL + "    private static final String TABLE_NAME = \"";
  protected final String TEXT_9 = "\";" + NL + "" + NL + "    /**" + NL + "     * @return a comma-separated list of ";
  protected final String TEXT_10 = " attributes in string form " + NL + "     */" + NL + "    public static String attributeListAsString(final String alias)" + NL + "    {" + NL + "        return Field.asAttributeList(alias, ";
  protected final String TEXT_11 = "Metadata.FIELDS);" + NL + "    }" + NL + "    " + NL + "    /**" + NL + "     * @return the ";
  protected final String TEXT_12 = " attributes in a comma-separated &quot;KEY-N=?&quot; form  " + NL + "     */" + NL + "    public static String attributeListAsUpdateString(final String alias)" + NL + "    {" + NL + "        return Field.asUpdateList(alias, ";
  protected final String TEXT_13 = "Metadata.FIELDS);" + NL + "    }" + NL;
  protected final String TEXT_14 = NL + "    /**" + NL + "     * @return the 'WHERE' clause for retrieving a ";
  protected final String TEXT_15 = " object by ID" + NL + "     */" + NL + "    public static String byId()" + NL + "    {" + NL + "        return by(";
  protected final String TEXT_16 = "Metadata.ID_FIELD);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @return a query which will return the max id" + NL + "     */" + NL + "\tpublic static String selectMaxId() {" + NL + "\t\treturn String.format(\"SELECT MAX(";
  protected final String TEXT_17 = ") FROM %s\", TABLE_NAME);" + NL + "\t}";
  protected final String TEXT_18 = NL;
  protected final String TEXT_19 = NL + NL + "    /**" + NL + "     */" + NL + "    public static String findBy";
  protected final String TEXT_20 = "()" + NL + "    {" + NL + "        final String select = select(\"a\");" + NL + "        final StringBuilder querySql = new StringBuilder(select);" + NL + "        querySql.append(\", ";
  protected final String TEXT_21 = " b \");" + NL + "        querySql.append(\"WHERE b.";
  protected final String TEXT_22 = "=a.";
  protected final String TEXT_23 = " \");" + NL + "        querySql.append(\"AND b.";
  protected final String TEXT_24 = "=?\");" + NL + "        final String sql = querySql.toString();" + NL + "        return sql;" + NL + "    }" + NL;
  protected final String TEXT_25 = NL + NL + NL + "    /**" + NL + "     * @return the 'WHERE' clause for retrieving a ";
  protected final String TEXT_26 = " object by its given field property" + NL + "     */" + NL + "    public static String by(final Field<?> field)" + NL + "    {" + NL + "        return String.format(\"%s WHERE %s=?\", select(), field.getName());" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @return the 'INSERT' SQL used to create a new ";
  protected final String TEXT_27 = " entry" + NL + "     */" + NL + "    public static String insert()" + NL + "    {" + NL + "        final String propertyPlaceholders = Strings.repeat(\"?,\", ";
  protected final String TEXT_28 = "Metadata.FIELDS.size() - 1) + \"?\";" + NL + "        return String.format(\"INSERT INTO %s (%s) VALUES (%s)\", TABLE_NAME, attributeListAsString(null), propertyPlaceholders);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @return the 'UPDATE' SQL used to create a new ";
  protected final String TEXT_29 = " entry" + NL + "     */" + NL + "    public static String update()" + NL + "    {" + NL + "        return String.format(\"UPDATE %s SET %s\", TABLE_NAME, attributeListAsUpdateString(null));" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @return the 'SELECT' SQL for querying the database" + NL + "     */" + NL + "    public static String select()" + NL + "    {" + NL + "        return select(null);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @return the 'SELECT' SQL for querying the database using the given alias for the table entry" + NL + "     */" + NL + "    public static String select(final String alias)" + NL + "    {" + NL + "        return String.format(\"SELECT %s FROM %s %s\", attributeListAsString(alias), TABLE_NAME, Strings.nullToEmpty(alias));" + NL + "    }" + NL + "    " + NL + "}";
  protected final String TEXT_30 = NL;

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
     } 
    stringBuffer.append(TEXT_18);
     for (final Reference r : ctxt.getReferencesToThisTable()) { 
    stringBuffer.append(TEXT_19);
    stringBuffer.append( ctxt.getReferenceName(r) );
    stringBuffer.append(TEXT_20);
    stringBuffer.append(  r.getFrom().getTable().getTableName() );
    stringBuffer.append(TEXT_21);
    stringBuffer.append( r.getFrom().getName() );
    stringBuffer.append(TEXT_22);
    stringBuffer.append( r.getTo().getName() );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( r.getFromTablePrimaryKey().getName() );
    stringBuffer.append(TEXT_24);
     } 
    stringBuffer.append(TEXT_25);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_26);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_27);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_28);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_29);
    stringBuffer.append(TEXT_30);
    return stringBuffer.toString();
  }
}