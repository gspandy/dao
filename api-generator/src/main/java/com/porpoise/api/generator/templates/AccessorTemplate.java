package com.porpoise.api.generator.templates;

import java.util.*;
import com.porpoise.generator.*;
import com.porpoise.api.generator.model.*;
import com.porpoise.api.generator.gen.*;

public class AccessorTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized AccessorTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    AccessorTemplate result = new AccessorTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ".api;" + NL + "" + NL + "import java.util.Collection;" + NL + "" + NL + "/**" + NL + " */" + NL + "public final interface I";
  protected final String TEXT_3 = NL + "{" + NL + "" + NL + "}";
  protected final String TEXT_4 = NL;

   /* (non-javadoc)
    * @see IGenerator#generate(Object)
    */
   public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     final ApiContext ctxt = (ApiContext) argument;
final String n = ctxt.getJavaName(); 

    stringBuffer.append(TEXT_1);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_2);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    return stringBuffer.toString();
  }
}