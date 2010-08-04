package com.porpoise.dao.generator.templates;

import java.util.*;
import com.porpoise.dao.generator.gen.*;

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
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + NL + "package ";
  protected final String TEXT_3 = ";" + NL + "" + NL + "import ";
  protected final String TEXT_4 = ".model.";
  protected final String TEXT_5 = "Dto;" + NL + "import ";
  protected final String TEXT_6 = ".model.";
  protected final String TEXT_7 = "Metadata;" + NL + "" + NL + "import com.porpoise.dao.database.metadata.Field;" + NL + "import com.google.common.base.Strings;" + NL + "" + NL + "import java.math.BigDecimal;" + NL + "import java.sql.ResultSet;" + NL + "import java.sql.SQLException;" + NL + "import java.util.List;" + NL + "" + NL + "" + NL + "/**" + NL + " */" + NL + "public class ";
  protected final String TEXT_8 = "Sql" + NL + "{" + NL + "    ;// uninstantiable" + NL + "" + NL + "    private static final String TABLE_NAME = \"";
  protected final String TEXT_9 = "\";" + NL + "" + NL + "    public static String attributeListAsString(final String alias)" + NL + "    {" + NL + "        return Field.asAttributeList(alias, ";
  protected final String TEXT_10 = "Metadata.FIELDS);" + NL + "    }" + NL + "    " + NL + "    public static String attributeListAsUpdateString(final String alias)" + NL + "    {" + NL + "        return Field.asUpdateList(alias, ";
  protected final String TEXT_11 = "Metadata.FIELDS);" + NL + "    }" + NL + "" + NL + "    public static String byId()" + NL + "    {" + NL + "        return by(";
  protected final String TEXT_12 = "Metadata.id);" + NL + "    }" + NL + "" + NL + "    public static String by(final Field<?> field)" + NL + "    {" + NL + "        return String.format(\"%s WHERE %s=?\", select(), field.getName());" + NL + "    }" + NL + "" + NL + "    public static String insert()" + NL + "    {" + NL + "        final String propertyPlaceholders = Strings.repeat(\"?,\", ";
  protected final String TEXT_13 = "Metadata.FIELDS.size() - 1) + \"?\";" + NL + "        return String.format(\"INSERT INTO %s (%s) VALUES (%s)\", TABLE_NAME, attributeListAsString(null), propertyPlaceholders);" + NL + "    }" + NL + "" + NL + "    public static String update()" + NL + "    {" + NL + "        return String.format(\"UPDATE %s SET %s\", TABLE_NAME, attributeListAsUpdateString(null));" + NL + "    }" + NL + "" + NL + "    public static String select()" + NL + "    {" + NL + "        return String.format(\"SELECT %s FROM %s\", attributeListAsString(null), TABLE_NAME);" + NL + "    }" + NL + "    " + NL + "}";
  protected final String TEXT_14 = NL;

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
    stringBuffer.append( n);
    stringBuffer.append(TEXT_5);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_6);
    stringBuffer.append( n);
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
    stringBuffer.append(TEXT_14);
    return stringBuffer.toString();
  }
}