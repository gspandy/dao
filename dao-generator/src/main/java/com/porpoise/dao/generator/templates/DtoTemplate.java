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
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ".model;" + NL + "" + NL + "import java.math.BigDecimal;" + NL + "import java.util.List;" + NL + "import java.util.Date;" + NL + "import java.util.Arrays;" + NL + "" + NL + "import ";
  protected final String TEXT_3 = ".model.";
  protected final String TEXT_4 = "Dto;" + NL + "" + NL + "/**" + NL + " * ";
  protected final String TEXT_5 = "Dto" + NL + " */" + NL + "public final class ";
  protected final String TEXT_6 = "Dto extends AbstractDto" + NL + "{";
  protected final String TEXT_7 = NL + "    /**" + NL + "     * ";
  protected final String TEXT_8 = NL + "     */" + NL + "    private final ";
  protected final String TEXT_9 = " ";
  protected final String TEXT_10 = ";" + NL + "    ";
  protected final String TEXT_11 = NL + NL + "    public ";
  protected final String TEXT_12 = "Dto(";
  protected final String TEXT_13 = NL;
  protected final String TEXT_14 = "    " + NL + "    )" + NL + "    {" + NL + "        super();";
  protected final String TEXT_15 = NL + "        this.";
  protected final String TEXT_16 = " = ";
  protected final String TEXT_17 = "; ";
  protected final String TEXT_18 = NL + "    }" + NL;
  protected final String TEXT_19 = NL + "    /**" + NL + "     * ";
  protected final String TEXT_20 = NL + "     */" + NL + "    public ";
  protected final String TEXT_21 = " ";
  protected final String TEXT_22 = "()" + NL + "    {" + NL + "        return this.";
  protected final String TEXT_23 = ";" + NL + "    }    ";
  protected final String TEXT_24 = NL + NL + "    /* (non-Javadoc)" + NL + "     * @see java.lang.Object#toString()" + NL + "     */" + NL + "    @Override" + NL + "    public String toString()" + NL + "    {" + NL + "        return String.format(\"";
  protected final String TEXT_25 = "Dto [";
  protected final String TEXT_26 = "]\", ";
  protected final String TEXT_27 = ");" + NL + "    }" + NL + "" + NL + "    /* (non-Javadoc)" + NL + "     * @see java.lang.Object#hashCode()" + NL + "     */" + NL + "    @Override" + NL + "    public int hashCode()" + NL + "    {" + NL + "        final int prime = 31;" + NL + "        int result = 1;";
  protected final String TEXT_28 = NL + "        result = prime * result + hashCode(";
  protected final String TEXT_29 = "());";
  protected final String TEXT_30 = NL + "        result = prime * result + hashCode(";
  protected final String TEXT_31 = "());";
  protected final String TEXT_32 = NL + "        result = prime * result + ((";
  protected final String TEXT_33 = "() == null) ? 0 : ";
  protected final String TEXT_34 = "().hashCode());";
  protected final String TEXT_35 = NL + "        return result;" + NL + "    }" + NL + "" + NL + "    /* (non-Javadoc)" + NL + "     * @see java.lang.Object#equals(java.lang.Object)" + NL + "     */" + NL + "    @Override" + NL + "    public boolean equals(Object obj)" + NL + "    {" + NL + "        if (this == obj)" + NL + "            return true;" + NL + "        if (obj == null)" + NL + "            return false;" + NL + "        if (getClass() != obj.getClass())" + NL + "            return false;";
  protected final String TEXT_36 = NL + "        ";
  protected final String TEXT_37 = "Dto other = (";
  protected final String TEXT_38 = "Dto) obj;" + NL;
  protected final String TEXT_39 = NL + NL + "        if (";
  protected final String TEXT_40 = "() == null)" + NL + "        {" + NL + "            if (other.";
  protected final String TEXT_41 = "() != null)" + NL + "            {" + NL + "                return false;" + NL + "            }" + NL + "        }";
  protected final String TEXT_42 = NL + "        else if (!equals(";
  protected final String TEXT_43 = "(), other.";
  protected final String TEXT_44 = "()))" + NL + "        {" + NL + "            return false;" + NL + "        }";
  protected final String TEXT_45 = NL + "        else if (";
  protected final String TEXT_46 = "().compareTo(other.";
  protected final String TEXT_47 = "()) != 0)" + NL + "        {" + NL + "            return false;" + NL + "        }";
  protected final String TEXT_48 = NL + "        else if (!Arrays.equals(";
  protected final String TEXT_49 = "(), other.";
  protected final String TEXT_50 = "()))" + NL + "        {" + NL + "            return false;" + NL + "        }";
  protected final String TEXT_51 = NL + "        else if (!";
  protected final String TEXT_52 = "().equals(other.";
  protected final String TEXT_53 = "()))" + NL + "        {" + NL + "            return false;" + NL + "        }";
  protected final String TEXT_54 = NL + "        return true;" + NL + "    }" + NL + "}";
  protected final String TEXT_55 = NL;

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
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_4);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_6);
     for (final Column col : ctxt.getColumns()) { 
    stringBuffer.append(TEXT_7);
    stringBuffer.append( col.getNameAsProperty() );
    stringBuffer.append(TEXT_8);
    stringBuffer.append( col.getJavaTypeName() );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( col.getNameAsProperty() );
    stringBuffer.append(TEXT_10);
    }
    stringBuffer.append(TEXT_11);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    stringBuffer.append( ctxt.getColumnDeclarations() );
    stringBuffer.append(TEXT_14);
     for (final Column col : ctxt.getColumns()) { 
    stringBuffer.append(TEXT_15);
    stringBuffer.append( col.getNameAsProperty() );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( col.getNameAsProperty() );
    stringBuffer.append(TEXT_17);
    }
    stringBuffer.append(TEXT_18);
     for (final Column col : ctxt.getColumns()) { 
    stringBuffer.append(TEXT_19);
    stringBuffer.append( col.getNameAsProperty() );
    stringBuffer.append(TEXT_20);
    stringBuffer.append( col.getJavaTypeName() );
    stringBuffer.append(TEXT_21);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_22);
    stringBuffer.append( col.getNameAsProperty() );
    stringBuffer.append(TEXT_23);
    }
    stringBuffer.append(TEXT_24);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_25);
    stringBuffer.append( ctxt.getColumnParameterListAsToString() );
    stringBuffer.append(TEXT_26);
    stringBuffer.append( ctxt.getColumnAccessorMethods("this") );
    stringBuffer.append(TEXT_27);
     for (final Column col : ctxt.getColumns()) { 
     if (col.isDate()) {
    stringBuffer.append(TEXT_28);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_29);
     } else if (col.isByteArray()) { 
    stringBuffer.append(TEXT_30);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_31);
     } else { 
    stringBuffer.append(TEXT_32);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_33);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_34);
     } 
    }
    stringBuffer.append(TEXT_35);
    stringBuffer.append(TEXT_36);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_37);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_38);
     for (final Column col : ctxt.getColumns()) { 
    stringBuffer.append(TEXT_39);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_40);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_41);
     if (col.isDate()) {
    stringBuffer.append(TEXT_42);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_43);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_44);
     } else if (col.isBigDecimal()) { 
    stringBuffer.append(TEXT_45);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_46);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_47);
     } else if (col.isByteArray()) { 
    stringBuffer.append(TEXT_48);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_49);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_50);
     } else { 
    stringBuffer.append(TEXT_51);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_52);
    stringBuffer.append( col.getNameAsAccessor() );
    stringBuffer.append(TEXT_53);
     }
    }
    stringBuffer.append(TEXT_54);
    stringBuffer.append(TEXT_55);
    return stringBuffer.toString();
  }
}