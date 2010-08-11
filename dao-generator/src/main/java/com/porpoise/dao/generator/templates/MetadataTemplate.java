package com.porpoise.dao.generator.templates;

import com.porpoise.generator.*;
import java.util.*;
import com.porpoise.dao.generator.gen.*;
import com.porpoise.dao.generator.model.*;

public class MetadataTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized MetadataTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    MetadataTemplate result = new MetadataTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ".model;" + NL + "" + NL + "import com.google.common.collect.ImmutableList;" + NL + "import com.porpoise.dao.database.metadata.Field;" + NL + "import java.math.BigDecimal;" + NL + "import java.util.List;" + NL + "import java.util.Date;" + NL + "" + NL + "/**" + NL + " * ";
  protected final String TEXT_3 = "Metadata" + NL + " */" + NL + "public enum ";
  protected final String TEXT_4 = "Metadata" + NL + "{" + NL + "    ;// uninstantiable" + NL + NL;
  protected final String TEXT_5 = NL + "    /**" + NL + "     * Field ";
  protected final String TEXT_6 = NL + "     */" + NL + "    public static Field<";
  protected final String TEXT_7 = "> ";
  protected final String TEXT_8 = " = new Field<";
  protected final String TEXT_9 = ">(\"";
  protected final String TEXT_10 = "\", ";
  protected final String TEXT_11 = ".class, ";
  protected final String TEXT_12 = ");" + NL;
  protected final String TEXT_13 = NL;
  protected final String TEXT_14 = NL + "    /** " + NL + "     * ID_FIELD" + NL + "     */" + NL + "    public static Field<";
  protected final String TEXT_15 = "> ID_FIELD = ";
  protected final String TEXT_16 = ";";
  protected final String TEXT_17 = NL + NL + "    /**" + NL + "     * All fields" + NL + "     */" + NL + "    public static ImmutableList<? extends Field<?>> FIELDS = ImmutableList.of(";
  protected final String TEXT_18 = ");" + NL + "}";
  protected final String TEXT_19 = NL;

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
    stringBuffer.append( n );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_4);
     for (final Column c : ctxt.getColumns()) { 
    stringBuffer.append(TEXT_5);
    stringBuffer.append( c.getName() );
    stringBuffer.append(TEXT_6);
    stringBuffer.append( c.getJavaTypeName() );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( c.getName() );
    stringBuffer.append(TEXT_8);
    stringBuffer.append( c.getJavaTypeName() );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( c.getName() );
    stringBuffer.append(TEXT_10);
    stringBuffer.append( c.getJavaTypeName() );
    stringBuffer.append(TEXT_11);
    stringBuffer.append( c.isRequired() );
    stringBuffer.append(TEXT_12);
     } // end for 
    stringBuffer.append(TEXT_13);
     if (ctxt.hasIdField()) { 
    stringBuffer.append(TEXT_14);
    stringBuffer.append( ctxt.getIdField().getJavaTypeName() );
    stringBuffer.append(TEXT_15);
    stringBuffer.append( ctxt.getIdField().getName() );
    stringBuffer.append(TEXT_16);
     } // end if 
    stringBuffer.append(TEXT_17);
    stringBuffer.append( ctxt.getColumnNames() );
    stringBuffer.append(TEXT_18);
    stringBuffer.append(TEXT_19);
    return stringBuffer.toString();
  }
}