package com.porpoise.dao.generator.templates;

import java.util.*;
import com.porpoise.dao.generator.gen.*;
import com.porpoise.dao.generator.model.*;

public class AbstractDtoTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized AbstractDtoTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    AbstractDtoTemplate result = new AbstractDtoTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ".model;" + NL + "" + NL + "import java.util.Date;" + NL + "" + NL + "/**" + NL + " */" + NL + "public abstract class AbstractDto" + NL + "{" + NL + "\tprotected final boolean equals(final Date d1, final Date d2) {" + NL + "\t\t// equals is assumed to be within 1 second" + NL + "\t\tfinal int marginForErrorInMillis = 1000;" + NL + "\t\treturn within(d1, d2, marginForErrorInMillis);" + NL + "\t}" + NL + "" + NL + "\tprivate boolean within(final Date d1, final Date d2," + NL + "\t\t\tfinal int marginForErrorInMillis) {" + NL + "\t\tif (d1 == null) {" + NL + "\t\t\treturn d2 == null;" + NL + "\t\t} else if (d2 != null) {" + NL + "\t\t\tfinal long diff = Math.abs(d1.getTime() - d2.getTime());" + NL + "\t\t\treturn diff < marginForErrorInMillis;" + NL + "\t\t}" + NL + "\t\treturn false;" + NL + "\t}" + NL + "}";

   /* (non-javadoc)
    * @see IGenerator#generate(Object)
    */
   public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     final DaoContext ctxt = (DaoContext) argument; 

    stringBuffer.append(TEXT_1);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}