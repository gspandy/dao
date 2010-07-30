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
  protected final String TEXT_5 = "Dto;" + NL + "" + NL + "import java.math.BigDecimal;" + NL + "import java.sql.ResultSet;" + NL + "import java.sql.SQLException;" + NL + "import java.util.List;" + NL + "" + NL + "" + NL + "/**" + NL + " */" + NL + "public class ";
  protected final String TEXT_6 = "Sql" + NL + "{" + NL + "    ;// uninstantiable" + NL + "" + NL + "    public static String byId(final Long id)" + NL + "    {" + NL + "        return \"\";" + NL + "    }" + NL + "" + NL + "    public static String selectLike(final ";
  protected final String TEXT_7 = "Dto dto)" + NL + "    {" + NL + "        return select() + like(\"dto\", dto);" + NL + "" + NL + "    }" + NL + "" + NL + "    private static String like(final String aliasPrefix, final ";
  protected final String TEXT_8 = "Dto dto)" + NL + "    {" + NL + "        final String alias = aliasPrefix.endsWith(\".\") ? aliasPrefix : aliasPrefix + \".\";" + NL + "        return alias;" + NL + "    }" + NL + "" + NL + "    public static String select()" + NL + "    {" + NL + "        return null;" + NL + "    }" + NL + "    " + NL + "}";
  protected final String TEXT_9 = NL;

   /* (non-javadoc)
    * @see IGenerator#generate(Object)
    */
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
    stringBuffer.append( n);
    stringBuffer.append(TEXT_5);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_6);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_8);
    stringBuffer.append(TEXT_9);
    return stringBuffer.toString();
  }
}