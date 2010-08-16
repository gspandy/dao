package com.porpoise.dao.generator.templates;

import com.porpoise.generator.*;
import java.util.*;
import com.porpoise.dao.generator.gen.*;
import com.porpoise.dao.generator.model.*;

public class DaoApiImplTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized DaoApiImplTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    DaoApiImplTemplate result = new DaoApiImplTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ".impl;" + NL + "" + NL + "import ";
  protected final String TEXT_3 = ".*;" + NL + "import ";
  protected final String TEXT_4 = ".service.*;" + NL + "import ";
  protected final String TEXT_5 = ".domain.*;" + NL + "import ";
  protected final String TEXT_6 = ".model.*;" + NL + "import java.util.*;" + NL + "import com.google.common.collect.Lists;" + NL + "import com.porpoise.dao.database.DbConnectionFactory;" + NL + "import com.porpoise.dao.database.IDbTransaction;" + NL + "" + NL + "/**" + NL + " * " + NL + " */" + NL + "public class ";
  protected final String TEXT_7 = "ServiceImpl extends AbstractDaoService<";
  protected final String TEXT_8 = ", I";
  protected final String TEXT_9 = ", ";
  protected final String TEXT_10 = "Dao> implements I";
  protected final String TEXT_11 = "AccessorService" + NL + "{" + NL + "    /**" + NL + "     *" + NL + "     */" + NL + "    public ";
  protected final String TEXT_12 = "ServiceImpl(final DbConnectionFactory factory)" + NL + "    {" + NL + "        super(factory, new ";
  protected final String TEXT_13 = "Dao());" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @return an object for the given key" + NL + "     */" + NL + "    protected I";
  protected final String TEXT_14 = " getInternal(final ";
  protected final String TEXT_15 = " id, final IDbTransaction tx)" + NL + "    {" + NL + "        return getDomainObjects().get";
  protected final String TEXT_16 = "(id, tx);" + NL + "\t}" + NL + "}";
  protected final String TEXT_17 = NL;

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
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_4);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_6);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( ctxt.getIdField().getJavaTypeName() );
    stringBuffer.append(TEXT_8);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_10);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_11);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_13);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_14);
    stringBuffer.append( ctxt.getIdField().getJavaTypeName() );
    stringBuffer.append(TEXT_15);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_16);
    stringBuffer.append(TEXT_17);
    return stringBuffer.toString();
  }
}