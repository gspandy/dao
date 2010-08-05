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
  protected final String TEXT_24 = ";" + NL + "    }    ";
  protected final String TEXT_25 = NL + NL + "    /* (non-Javadoc)" + NL + "     * @see java.lang.Object#toString()" + NL + "     */" + NL + "    @Override" + NL + "    public String toString()" + NL + "    {" + NL + "        return \"";
  protected final String TEXT_26 = "Dto\";" + NL + "    }" + NL + "" + NL + "    /* (non-Javadoc)" + NL + "     * @see java.lang.Object#hashCode()" + NL + "     */" + NL + "    @Override" + NL + "    public int hashCode()" + NL + "    {" + NL + "        final int prime = 31;" + NL + "        int result = 1;";
  protected final String TEXT_27 = NL + "        result = prime * result + ((";
  protected final String TEXT_28 = "() == null) ? 0 : ";
  protected final String TEXT_29 = "().hashCode());";
  protected final String TEXT_30 = NL + "        return result;" + NL + "    }" + NL + "" + NL + "    /* (non-Javadoc)" + NL + "     * @see java.lang.Object#equals(java.lang.Object)" + NL + "     */" + NL + "    @Override" + NL + "    public boolean equals(Object obj)" + NL + "    {" + NL + "        if (this == obj)" + NL + "            return true;" + NL + "        if (obj == null)" + NL + "            return false;" + NL + "        if (getClass() != obj.getClass())" + NL + "            return false;";
  protected final String TEXT_31 = NL + "        ";
  protected final String TEXT_32 = "Dto other = (";
  protected final String TEXT_33 = "Dto) obj;" + NL;
  protected final String TEXT_34 = NL + NL + "        if (";
  protected final String TEXT_35 = "() == null)" + NL + "        {" + NL + "            if (other.";
  protected final String TEXT_36 = "() != null)" + NL + "            {" + NL + "                return false;" + NL + "            }" + NL + "        }" + NL + "        else if (!";
  protected final String TEXT_37 = "().equals(other.";
  protected final String TEXT_38 = "()))" + NL + "        {" + NL + "            return false;" + NL + "        }";
  protected final String TEXT_39 = NL + "        return true;" + NL + "    }" + NL + "}";
  protected final String TEXT_40 = NL;

   /* (non-javadoc)
    * @see IGenerator#generate(Object)
    */
   public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
     final DaoContext ctxt = (DaoContext) argument; 
final String n = ctxt.getJavaName();

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
    stringBuffer.append( col.getJavaTypeName() );
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
    stringBuffer.append( col.getJavaTypeName() );
    stringBuffer.append(TEXT_22);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( col.getNameAsProperty() );
    stringBuffer.append(TEXT_24);
    }
    stringBuffer.append(TEXT_25);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_26);
     for (final Column col : ctxt.getColumns()) { 
    stringBuffer.append(TEXT_27);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_28);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_29);
    }
    stringBuffer.append(TEXT_30);
    stringBuffer.append(TEXT_31);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_32);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_33);
     for (final Column col : ctxt.getColumns()) { 
    stringBuffer.append(TEXT_34);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_35);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_36);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_37);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_38);
    }
    stringBuffer.append(TEXT_39);
    stringBuffer.append(TEXT_40);
    return stringBuffer.toString();
  }
}