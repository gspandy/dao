package com.porpoise.dao.generator.templates;

import java.util.*;
import com.porpoise.dao.generator.model.*;
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
  protected final String TEXT_6 = "Dto" + NL + " */" + NL + "public final class ";
  protected final String TEXT_7 = "Dto" + NL + "{";
  protected final String TEXT_8 = NL + "    /**" + NL + "     * ";
  protected final String TEXT_9 = NL + "     */" + NL + "    private final ";
  protected final String TEXT_10 = " ";
  protected final String TEXT_11 = ";" + NL + "    ";
  protected final String TEXT_12 = NL + NL + "    public ";
  protected final String TEXT_13 = "Dto(";
  protected final String TEXT_14 = NL;
  protected final String TEXT_15 = "    " + NL + "    )" + NL + "    {" + NL + "        super();";
  protected final String TEXT_16 = NL + "        this.";
  protected final String TEXT_17 = " = ";
  protected final String TEXT_18 = "; ";
  protected final String TEXT_19 = NL + "    }" + NL;
  protected final String TEXT_20 = NL + "    /**" + NL + "     * ";
  protected final String TEXT_21 = NL + "     */" + NL + "    public ";
  protected final String TEXT_22 = " ";
  protected final String TEXT_23 = "()" + NL + "    {" + NL + "        return this.";
  protected final String TEXT_24 = ";" + NL + "    }" + NL + "    ";
  protected final String TEXT_25 = NL + NL + "}";
  protected final String TEXT_26 = NL;

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
     for (final Column col : ctxt.getColumns()) { 
    stringBuffer.append(TEXT_8);
    stringBuffer.append( col.getNameAsProperty() );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( col.getJavaName() );
    stringBuffer.append(TEXT_10);
    stringBuffer.append( col.getNameAsProperty() );
    stringBuffer.append(TEXT_11);
    }
    stringBuffer.append(TEXT_12);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_13);
    stringBuffer.append(TEXT_14);
    stringBuffer.append( ctxt.getColumnDeclarations() );
    stringBuffer.append(TEXT_15);
     for (final Column col : ctxt.getColumns()) { 
    stringBuffer.append(TEXT_16);
    stringBuffer.append( col.getNameAsProperty() );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( col.getNameAsProperty() );
    stringBuffer.append(TEXT_18);
    }
    stringBuffer.append(TEXT_19);
     for (final Column col : ctxt.getColumns()) { 
    stringBuffer.append(TEXT_20);
    stringBuffer.append( col.getNameAsProperty() );
    stringBuffer.append(TEXT_21);
    stringBuffer.append( col.getJavaName() );
    stringBuffer.append(TEXT_22);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( col.getNameAsProperty() );
    stringBuffer.append(TEXT_24);
    }
    stringBuffer.append(TEXT_25);
    stringBuffer.append(TEXT_26);
    return stringBuffer.toString();
  }
}