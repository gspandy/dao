package com.porpoise.api.generator.templates;

import java.util.*;
import com.porpoise.generator.*;
import com.porpoise.api.generator.model.*;
import com.porpoise.api.generator.gen.*;

public class AbstractDomainObjectTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized AbstractDomainObjectTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    AbstractDomainObjectTemplate result = new AbstractDomainObjectTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ".domain;" + NL + "import ";
  protected final String TEXT_3 = ".AbstractObject;" + NL + "" + NL + "import java.io.Serializable;" + NL + "" + NL + "/**" + NL + " */" + NL + "abstract class AbstractDomainObject extends AbstractObject implements Serializable" + NL + "{" + NL + "    /**" + NL + "     */" + NL + "    private static final long         serialVersionUID = 1L;" + NL + "" + NL + "}";
  protected final String TEXT_4 = NL;

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
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    return stringBuffer.toString();
  }
}