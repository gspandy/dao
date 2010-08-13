package com.porpoise.api.generator.templates;

import java.util.*;
import com.porpoise.generator.*;
import com.porpoise.dao.generator.model.api.*;
import com.porpoise.dao.generator.gen.*;
import com.porpoise.api.generator.gen.*;

public class AccessorServiceTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized AccessorServiceTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    AccessorServiceTemplate result = new AccessorServiceTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ".service;" + NL + "" + NL + "import java.util.Collection;" + NL + "import ";
  protected final String TEXT_3 = ".I";
  protected final String TEXT_4 = ";" + NL + "" + NL + "/**" + NL + " */" + NL + "public interface I";
  protected final String TEXT_5 = "AccessorService extends IAccessorService<";
  protected final String TEXT_6 = ", I";
  protected final String TEXT_7 = ">" + NL + "{" + NL + "" + NL + "}";
  protected final String TEXT_8 = NL;

   /* (non-javadoc)
    * @see IGenerator#generate(Object)
    */
   public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     final ApiContext ctxt = (ApiContext) argument;
final DomainObject obj = ctxt.getDomainObject(); 
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
    stringBuffer.append( ctxt.getIdType() );
    stringBuffer.append(TEXT_6);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_7);
    stringBuffer.append(TEXT_8);
    return stringBuffer.toString();
  }
}