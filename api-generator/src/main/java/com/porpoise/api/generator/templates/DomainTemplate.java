package com.porpoise.api.generator.templates;

import com.porpoise.generator.model.*;
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
  protected final String TEXT_2 = ".domain;" + NL + "" + NL + "import java.math.BigDecimal;" + NL + "import java.io.Serializable;" + NL + "import java.util.*;" + NL + "import javax.xml.bind.annotation.*;" + NL + "import com.google.common.collect.*;" + NL + "import com.google.common.primitives.*;" + NL + "import ";
  protected final String TEXT_3 = ".I";
  protected final String TEXT_4 = ";" + NL + "" + NL + "/**" + NL + " * ";
  protected final String TEXT_5 = NL + " */" + NL + "@XmlType(name=\"";
  protected final String TEXT_6 = "\")" + NL + "@XmlRootElement(name=\"";
  protected final String TEXT_7 = "\")" + NL + "//@Entity(name=\"${fileName.toLowerCase()}\")" + NL + "//@Table(name=\"${tablePrefix}${form.TableName}\")" + NL + "public final class ";
  protected final String TEXT_8 = " extends AbstractDomainObject implements I";
  protected final String TEXT_9 = NL + "{" + NL + "    /**" + NL + "     */" + NL + "    private static final long         serialVersionUID = 1L;" + NL;
  protected final String TEXT_10 = NL + "    /**" + NL + "     * ";
  protected final String TEXT_11 = NL + "     */" + NL + "    private final ";
  protected final String TEXT_12 = " ";
  protected final String TEXT_13 = ";";
  protected final String TEXT_14 = NL;
  protected final String TEXT_15 = NL + "    /**" + NL + "     * ";
  protected final String TEXT_16 = NL + "     */" + NL + "    private final Collection<";
  protected final String TEXT_17 = "> ";
  protected final String TEXT_18 = ";";
  protected final String TEXT_19 = NL + NL + "    /**" + NL + "     */" + NL + "    public ";
  protected final String TEXT_20 = "(";
  protected final String TEXT_21 = ") {";
  protected final String TEXT_22 = NL + "        this.";
  protected final String TEXT_23 = " = ";
  protected final String TEXT_24 = " == null ? null : new Date(";
  protected final String TEXT_25 = ".getTime()); ";
  protected final String TEXT_26 = NL + "        this.";
  protected final String TEXT_27 = " = ";
  protected final String TEXT_28 = " == null ? null : ImmutableList.copyOf(Bytes.asList(";
  protected final String TEXT_29 = ")); ";
  protected final String TEXT_30 = NL + "        this.";
  protected final String TEXT_31 = " = ";
  protected final String TEXT_32 = "; ";
  protected final String TEXT_33 = NL;
  protected final String TEXT_34 = NL + "        this.";
  protected final String TEXT_35 = " = ImmutableList.copyOf(";
  protected final String TEXT_36 = ");";
  protected final String TEXT_37 = NL + "    }" + NL;
  protected final String TEXT_38 = NL + "    /**" + NL + "     * @return ";
  protected final String TEXT_39 = NL + "     */" + NL + "    public ";
  protected final String TEXT_40 = " ";
  protected final String TEXT_41 = "()" + NL + "    {" + NL + "        return this.";
  protected final String TEXT_42 = ";" + NL + "    }";
  protected final String TEXT_43 = NL;
  protected final String TEXT_44 = NL + "    /**" + NL + "     * @return ";
  protected final String TEXT_45 = NL + "     */" + NL + "    public Collection<";
  protected final String TEXT_46 = "> ";
  protected final String TEXT_47 = "()" + NL + "    {" + NL + "        return this.";
  protected final String TEXT_48 = ";" + NL + "    }";
  protected final String TEXT_49 = NL + NL + NL + "    /* (non-Javadoc)" + NL + "     * @see java.lang.Object#toString()" + NL + "     */" + NL + "    @Override" + NL + "    public String toString()" + NL + "    {" + NL + "        return String.format(\"";
  protected final String TEXT_50 = "Dto [";
  protected final String TEXT_51 = "]\", ";
  protected final String TEXT_52 = ");" + NL + "    }" + NL + "" + NL + "    /* (non-Javadoc)" + NL + "     * @see java.lang.Object#hashCode()" + NL + "     */" + NL + "    @Override" + NL + "    public int hashCode()" + NL + "    {" + NL + "        final int prime = 31;" + NL + "        int result = 1;";
  protected final String TEXT_53 = NL + "        result = prime * result + hashCode(";
  protected final String TEXT_54 = "());";
  protected final String TEXT_55 = NL + "        result = prime * result + ((";
  protected final String TEXT_56 = "() == null) ? 0 : ";
  protected final String TEXT_57 = "().hashCode());";
  protected final String TEXT_58 = NL + "        return result;" + NL + "    }" + NL + "" + NL + "    /* (non-Javadoc)" + NL + "     * @see java.lang.Object#equals(java.lang.Object)" + NL + "     */" + NL + "    @Override" + NL + "    public boolean equals(Object obj)" + NL + "    {" + NL + "        if (this == obj)" + NL + "            return true;" + NL + "        if (obj == null)" + NL + "            return false;" + NL + "        if (getClass() != obj.getClass())" + NL + "            return false;" + NL + "        final ";
  protected final String TEXT_59 = " other = (";
  protected final String TEXT_60 = ") obj;" + NL;
  protected final String TEXT_61 = NL + NL + "        if (";
  protected final String TEXT_62 = "() == null)" + NL + "        {" + NL + "            if (other.";
  protected final String TEXT_63 = "() != null)" + NL + "            {" + NL + "                return false;" + NL + "            }" + NL + "        }";
  protected final String TEXT_64 = NL + "        else if (!equals(";
  protected final String TEXT_65 = "(), other.";
  protected final String TEXT_66 = "()))" + NL + "        {" + NL + "            return false;" + NL + "        }";
  protected final String TEXT_67 = NL + "        else if (";
  protected final String TEXT_68 = "().compareTo(other.";
  protected final String TEXT_69 = "()) != 0)" + NL + "        {" + NL + "            return false;" + NL + "        }";
  protected final String TEXT_70 = NL + "        else if (!";
  protected final String TEXT_71 = "().equals(other.";
  protected final String TEXT_72 = "()))" + NL + "        {" + NL + "            return false;" + NL + "        }";
  protected final String TEXT_73 = NL + "        return true;" + NL + "    }" + NL + "}";
  protected final String TEXT_74 = NL;

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
    stringBuffer.append( n );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_8);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_9);
     for (final IField f : ctxt.getSingleFields()) { 
    stringBuffer.append(TEXT_10);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_11);
    stringBuffer.append( f.getJavaTypeName() );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_13);
    }  // end for 
    stringBuffer.append(TEXT_14);
     for (final IField f : ctxt.getListFields()) { 
    stringBuffer.append(TEXT_15);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( f.getJavaTypeName() );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_18);
    } // end for 
    stringBuffer.append(TEXT_19);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_20);
    stringBuffer.append( ctxt.getDeclarations() );
    stringBuffer.append(TEXT_21);
     for (final IField f : ctxt.getSingleFields()) {
if (f.isDate()) {

    stringBuffer.append(TEXT_22);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_24);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_25);
    } else if (f.isByteArray()) {
    stringBuffer.append(TEXT_26);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_27);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_28);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_29);
    } else { 
    stringBuffer.append(TEXT_30);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_31);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_32);
    } // end if 
    }  // end for 
    stringBuffer.append(TEXT_33);
     for (final IField f : ctxt.getListFields()) { 
    stringBuffer.append(TEXT_34);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_35);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_36);
    }  // end for 
    stringBuffer.append(TEXT_37);
     for (final IField f : ctxt.getSingleFields()) { 
    stringBuffer.append(TEXT_38);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_39);
    stringBuffer.append( f.getJavaTypeName() );
    stringBuffer.append(TEXT_40);
    stringBuffer.append( f.getNameAsAccessor() );
    stringBuffer.append(TEXT_41);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_42);
    }  // end for 
    stringBuffer.append(TEXT_43);
     for (final IField f : ctxt.getListFields()) { 
    stringBuffer.append(TEXT_44);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_45);
    stringBuffer.append( f.getJavaTypeName() );
    stringBuffer.append(TEXT_46);
    stringBuffer.append( f.getNameAsAccessor() );
    stringBuffer.append(TEXT_47);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_48);
    } // end for 
    stringBuffer.append(TEXT_49);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_50);
    stringBuffer.append( ctxt.getParameterListAsToString() );
    stringBuffer.append(TEXT_51);
    stringBuffer.append( ctxt.getAccessorMethods("this") );
    stringBuffer.append(TEXT_52);
     for (final IField field : ctxt.getSingleFields()) { 
     if (field.isDate()) {
    stringBuffer.append(TEXT_53);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_54);
     } else { 
    stringBuffer.append(TEXT_55);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_56);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_57);
     } 
    }
    stringBuffer.append(TEXT_58);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_59);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_60);
     for (final IField field : ctxt.getSingleFields()) { 
    stringBuffer.append(TEXT_61);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_62);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_63);
     if (field.isDate()) {
    stringBuffer.append(TEXT_64);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_65);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_66);
     } else if (field.isBigDecimal()) { 
    stringBuffer.append(TEXT_67);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_68);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_69);
     } else { 
    stringBuffer.append(TEXT_70);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_71);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_72);
     }
    }
    stringBuffer.append(TEXT_73);
    stringBuffer.append(TEXT_74);
    return stringBuffer.toString();
  }
}