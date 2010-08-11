package com.porpoise.api.generator.templates;

import java.util.*;
import com.porpoise.generator.*;
import com.porpoise.api.generator.model.*;
import com.porpoise.api.generator.gen.*;

public class RepositoryTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized RepositoryTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    RepositoryTemplate result = new RepositoryTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";" + NL + "" + NL + "import java.util.Collection;" + NL + "" + NL + "/**" + NL + " */" + NL + "public class Repository" + NL + "{" + NL + "" + NL + "}";
  protected final String TEXT_3 = NL;

   /* (non-javadoc)
    * @see IGenerator#generate(Object)
    */
   public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     final ApiContext ctxt = (ApiContext) argument;

    stringBuffer.append(TEXT_1);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_2);
    stringBuffer.append(TEXT_3);
    return stringBuffer.toString();
  }
}