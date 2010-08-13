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
  protected final String TEXT_7 = "ServiceImpl extends AbstractDaoService implements I";
  protected final String TEXT_8 = "AccessorService" + NL + "{" + NL + "    private final ";
  protected final String TEXT_9 = "Dao dao;" + NL + "    /**" + NL + "     *" + NL + "     */" + NL + "    public ";
  protected final String TEXT_10 = "ServiceImpl(final DbConnectionFactory factory)" + NL + "    {" + NL + "        super(factory);" + NL + "        this.dao = new ";
  protected final String TEXT_11 = "Dao();" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @return an iterable of all IDs for the given type" + NL + "     */" + NL + "    public Iterable<";
  protected final String TEXT_12 = "> listAllKeys()" + NL + "    {" + NL + "\t\tfinal IDbTransaction tx = getFactory().startNewTransaction();" + NL + "\t\tIterable<Integer> result;" + NL + "\t\ttry {" + NL + "\t\t\tresult = this.dao.listAllKeys(tx);" + NL + "\t\t} finally {" + NL + "\t\t\ttx.close();" + NL + "\t\t}" + NL + "\t\treturn result;" + NL + "    }" + NL + "    " + NL + "    /**" + NL + "     * @return an object for the given key" + NL + "     */" + NL + "    public I";
  protected final String TEXT_13 = " get(final ";
  protected final String TEXT_14 = " id)" + NL + "    {" + NL + "    \t\tfinal IDbTransaction tx = getFactory().startNewTransaction();" + NL + "\t\tI";
  protected final String TEXT_15 = " value = null;" + NL + "\t\ttry {" + NL + "\t\t\tfinal ";
  protected final String TEXT_16 = "Dto dto = this.dao.findById(tx, id);" + NL + "\t\t\tvalue = new ";
  protected final String TEXT_17 = "(" + NL + "\t\t\t";
  protected final String TEXT_18 = NL + "\t\t\t);" + NL + "\t\t} finally {" + NL + "\t\t\ttx.close();" + NL + "\t\t}" + NL + "\t\treturn value;" + NL + "\t}" + NL + "    " + NL + "    /**" + NL + "     * @return an object for the given key" + NL + "     */" + NL + "    public Collection<I";
  protected final String TEXT_19 = "> getAll(final Collection<";
  protected final String TEXT_20 = "> keys)" + NL + "    {" + NL + "        return Lists.newArrayList();" + NL + "    }" + NL + "}";
  protected final String TEXT_21 = NL;

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
    stringBuffer.append( n );
    stringBuffer.append(TEXT_8);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_10);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_11);
    stringBuffer.append( ctxt.getIdField().getJavaTypeName() );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_13);
    stringBuffer.append( ctxt.getIdField().getJavaTypeName() );
    stringBuffer.append(TEXT_14);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_15);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( ctxt.getAccessorMethods("dto") );
    stringBuffer.append(TEXT_18);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_19);
    stringBuffer.append( ctxt.getIdField().getJavaTypeName() );
    stringBuffer.append(TEXT_20);
    stringBuffer.append(TEXT_21);
    return stringBuffer.toString();
  }
}