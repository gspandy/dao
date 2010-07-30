package com.porpoise.dao.generator.templates;

import java.util.*;
import com.porpoise.dao.generator.gen.*;

public class DtoTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized DtoTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    DtoTemplate result = new DtoTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + NL + "package ";
  protected final String TEXT_3 = ".model;" + NL + "" + NL + "import java.math.BigDecimal;" + NL + "import java.util.List;" + NL + "" + NL + "import ";
  protected final String TEXT_4 = ".model.";
  protected final String TEXT_5 = "Dto;" + NL + "" + NL + "/**" + NL + " * ";
  protected final String TEXT_6 = "Dto" + NL + " */" + NL + "public class ";
  protected final String TEXT_7 = "Dto" + NL + "{" + NL + "    public ";
  protected final String TEXT_8 = "Dto(";
  protected final String TEXT_9 = NL;
  protected final String TEXT_10 = "    " + NL + "    )" + NL + "    {" + NL + "        super();" + NL + "    }" + NL + "}";
  protected final String TEXT_11 = NL;

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
    stringBuffer.append( n );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_6);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_8);
    stringBuffer.append(TEXT_9);
    stringBuffer.append( ctxt.getColumnDeclarations() );
    stringBuffer.append(TEXT_10);
    stringBuffer.append(TEXT_11);
    return stringBuffer.toString();
  }
}