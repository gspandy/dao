package com.porpoise.api.generator.templates;

import java.util.*;
import com.porpoise.generator.*;
import com.porpoise.api.generator.model.*;
import com.porpoise.api.generator.gen.*;

public class DomainTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized DomainTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    DomainTemplate result = new DomainTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ".api;" + NL + "" + NL + "import java.math.BigDecimal;" + NL + "import java.io.Serializable;" + NL + "import java.util.List;" + NL + "import java.util.Date;" + NL + "import java.util.Arrays;" + NL + "import javax.xml.bind.annotation.*;" + NL + "import javax.persistence.*;" + NL + "import ";
  protected final String TEXT_3 = ".model.";
  protected final String TEXT_4 = "Dto;" + NL + "" + NL + "/**" + NL + " * ";
  protected final String TEXT_5 = NL + " */" + NL + "@XmlType(name=\"${form.PropertyName}Type\")" + NL + "@XmlRootElement(name=\"${form.PropertyName}\")" + NL + "@Entity(name=\"${fileName.toLowerCase()}\")" + NL + "@Table(name=\"${tablePrefix}${form.TableName}\")" + NL + "public final class ";
  protected final String TEXT_6 = " extends DomainObject implements Serializable" + NL + "{" + NL + "    /**" + NL + "     */" + NL + "    private static final long         serialVersionUID = 1L;" + NL + "    " + NL + "}";
  protected final String TEXT_7 = NL;

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
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_4);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    return stringBuffer.toString();
  }
}