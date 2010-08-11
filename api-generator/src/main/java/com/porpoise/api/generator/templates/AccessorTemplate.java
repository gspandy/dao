package com.porpoise.api.generator.templates;

import java.util.*;
import com.porpoise.generator.model.*;
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
  protected final String TEXT_2 = ";" + NL + "" + NL + "import java.util.*;" + NL + "" + NL + "/**" + NL + " */" + NL + "public interface I";
  protected final String TEXT_3 = NL + "{";
  protected final String TEXT_4 = NL + "    /**" + NL + "     * @return ";
  protected final String TEXT_5 = NL + "     */";
  protected final String TEXT_6 = NL + "    public Collection<Byte> ";
  protected final String TEXT_7 = "();";
  protected final String TEXT_8 = NL + "    public ";
  protected final String TEXT_9 = " ";
  protected final String TEXT_10 = "();";
  protected final String TEXT_11 = NL;
  protected final String TEXT_12 = NL + "    /**" + NL + "     * @return ";
  protected final String TEXT_13 = NL + "     */" + NL + "    public ";
  protected final String TEXT_14 = " ";
  protected final String TEXT_15 = "Id();";
  protected final String TEXT_16 = NL;
  protected final String TEXT_17 = NL + "    /**" + NL + "     * @return ";
  protected final String TEXT_18 = NL + "     */" + NL + "    public Collection<";
  protected final String TEXT_19 = "> ";
  protected final String TEXT_20 = "Ids();";
  protected final String TEXT_21 = NL + "}";
  protected final String TEXT_22 = NL;

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
     for (final IField f : ctxt.getDomainObject().getPrimitiveFields()) { 
    stringBuffer.append(TEXT_4);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_5);
     if (f.isByteArray()) { 
    stringBuffer.append(TEXT_6);
    stringBuffer.append( f.getNameAsAccessor() );
    stringBuffer.append(TEXT_7);
     } else { 
    stringBuffer.append(TEXT_8);
    stringBuffer.append( f.getJavaTypeName() );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( f.getNameAsAccessor() );
    stringBuffer.append(TEXT_10);
     } 
    }  // end for 
    stringBuffer.append(TEXT_11);
     for (final DomainObjectField f : ctxt.getDomainObject().getObjectFields()) { 
if (!f.getType().hasIdField()) {
    continue;
}

    stringBuffer.append(TEXT_12);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_13);
    stringBuffer.append( ctxt.getJavaKeyTypeForField(f) );
    stringBuffer.append(TEXT_14);
    stringBuffer.append( f.getNameAsAccessor() );
    stringBuffer.append(TEXT_15);
    }  // end for 
    stringBuffer.append(TEXT_16);
     for (final IField f : ctxt.getListFields()) { 
DomainObjectField dof = (DomainObjectField)f;
if (!dof.getType().hasIdField())
{
    continue;
}

    stringBuffer.append(TEXT_17);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_18);
    stringBuffer.append( ctxt.getJavaKeyTypeForField(f) );
    stringBuffer.append(TEXT_19);
    stringBuffer.append( f.getNameAsAccessor() );
    stringBuffer.append(TEXT_20);
    } // end for 
    stringBuffer.append(TEXT_21);
    stringBuffer.append(TEXT_22);
    return stringBuffer.toString();
  }
}