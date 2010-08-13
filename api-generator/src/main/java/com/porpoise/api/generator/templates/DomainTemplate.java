package com.porpoise.api.generator.templates;

import com.porpoise.dao.generator.model.api.*;
import com.porpoise.dao.generator.gen.*;
import com.porpoise.generator.model.*;
import java.util.*;
import com.porpoise.generator.*;
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
  protected final String TEXT_2 = ".domain;" + NL + "" + NL + "import java.math.BigDecimal;" + NL + "import java.io.Serializable;" + NL + "import java.util.*;" + NL + "import javax.xml.bind.annotation.*;" + NL + "import com.google.common.base.*;" + NL + "import com.google.common.collect.*;" + NL + "import com.google.common.primitives.*;" + NL + "import ";
  protected final String TEXT_3 = ".*;" + NL + "" + NL + "/**" + NL + " * ";
  protected final String TEXT_4 = NL + " */" + NL + "@XmlType(name=\"";
  protected final String TEXT_5 = "\")" + NL + "@XmlRootElement(name=\"";
  protected final String TEXT_6 = "\")" + NL + "public final class ";
  protected final String TEXT_7 = " extends AbstractDomainObject implements I";
  protected final String TEXT_8 = NL + "{" + NL + "    /**" + NL + "     */" + NL + "    private static final long         serialVersionUID = 1L;" + NL;
  protected final String TEXT_9 = NL + "    /**" + NL + "     * ";
  protected final String TEXT_10 = NL + "     */" + NL + "    private final ";
  protected final String TEXT_11 = " ";
  protected final String TEXT_12 = ";";
  protected final String TEXT_13 = NL;
  protected final String TEXT_14 = NL + "    /**" + NL + "     * ";
  protected final String TEXT_15 = NL + "     */" + NL + "    private final Collection<";
  protected final String TEXT_16 = "> ";
  protected final String TEXT_17 = ";";
  protected final String TEXT_18 = NL + NL + "    /**" + NL + "     */" + NL + "    public ";
  protected final String TEXT_19 = "(";
  protected final String TEXT_20 = ") {";
  protected final String TEXT_21 = NL + "        this.";
  protected final String TEXT_22 = " = ";
  protected final String TEXT_23 = " == null ? null : new Date(";
  protected final String TEXT_24 = ".getTime()); ";
  protected final String TEXT_25 = NL + "        this.";
  protected final String TEXT_26 = " = ";
  protected final String TEXT_27 = " == null ? null : ImmutableList.copyOf(Bytes.asList(";
  protected final String TEXT_28 = ")); ";
  protected final String TEXT_29 = NL + "        this.";
  protected final String TEXT_30 = " = ";
  protected final String TEXT_31 = "; ";
  protected final String TEXT_32 = NL;
  protected final String TEXT_33 = NL + "        this.";
  protected final String TEXT_34 = " = ImmutableList.copyOf(";
  protected final String TEXT_35 = ");";
  protected final String TEXT_36 = NL + "    }" + NL;
  protected final String TEXT_37 = NL + "    /**" + NL + "     * @return ";
  protected final String TEXT_38 = NL + "     */" + NL + "    public ";
  protected final String TEXT_39 = " ";
  protected final String TEXT_40 = "()" + NL + "    {";
  protected final String TEXT_41 = NL + "        return this.";
  protected final String TEXT_42 = " == null ? null : new Date(this.";
  protected final String TEXT_43 = ".getTime());";
  protected final String TEXT_44 = NL + "        return this.";
  protected final String TEXT_45 = ";";
  protected final String TEXT_46 = NL + "    }";
  protected final String TEXT_47 = NL;
  protected final String TEXT_48 = NL + "    /**" + NL + "     * @return ";
  protected final String TEXT_49 = NL + "     */" + NL + "    public Collection<";
  protected final String TEXT_50 = "> ";
  protected final String TEXT_51 = "()" + NL + "    {" + NL + "        return this.";
  protected final String TEXT_52 = ";" + NL + "    }" + NL;
  protected final String TEXT_53 = NL + NL + "\t@Override" + NL + "\tpublic Collection<";
  protected final String TEXT_54 = "> ";
  protected final String TEXT_55 = "Ids() {" + NL + "\t\treturn Collections2.transform(";
  protected final String TEXT_56 = "()," + NL + "\t\t\t\tnew Function<I";
  protected final String TEXT_57 = ", ";
  protected final String TEXT_58 = ">() {" + NL + "\t\t\t\t\t@Override" + NL + "\t\t\t\t\tpublic ";
  protected final String TEXT_59 = " apply(final I";
  protected final String TEXT_60 = " from) {" + NL + "\t\t\t\t\t\treturn from.";
  protected final String TEXT_61 = "();" + NL + "\t\t\t\t\t}" + NL + "\t\t\t\t});" + NL + "\t}";
  protected final String TEXT_62 = NL;
  protected final String TEXT_63 = NL + "    /**" + NL + "     * @return ";
  protected final String TEXT_64 = NL + "     */" + NL + "    public ";
  protected final String TEXT_65 = " ";
  protected final String TEXT_66 = "Id()" + NL + "    {" + NL + "        return ";
  protected final String TEXT_67 = "() == null ? null : ";
  protected final String TEXT_68 = "().";
  protected final String TEXT_69 = "();" + NL + "    }";
  protected final String TEXT_70 = NL + NL + "    /* (non-Javadoc)" + NL + "     * @see java.lang.Object#toString()" + NL + "     */" + NL + "    @Override" + NL + "    public String toString()" + NL + "    {" + NL + "        return String.format(\"";
  protected final String TEXT_71 = "Dto [";
  protected final String TEXT_72 = "]\", ";
  protected final String TEXT_73 = ");" + NL + "    }" + NL + "" + NL + "    /* (non-Javadoc)" + NL + "     * @see java.lang.Object#hashCode()" + NL + "     */" + NL + "    @Override" + NL + "    public int hashCode()" + NL + "    {" + NL + "        final int prime = 31;" + NL + "        int result = 1;";
  protected final String TEXT_74 = NL + "        result = prime * result + hashCode(";
  protected final String TEXT_75 = "());";
  protected final String TEXT_76 = NL + "        result = prime * result + ((";
  protected final String TEXT_77 = "() == null) ? 0 : ";
  protected final String TEXT_78 = "().hashCode());";
  protected final String TEXT_79 = NL + "        return result;" + NL + "    }" + NL + "" + NL + "    /* (non-Javadoc)" + NL + "     * @see java.lang.Object#equals(java.lang.Object)" + NL + "     */" + NL + "    @Override" + NL + "    public boolean equals(Object obj)" + NL + "    {" + NL + "        if (this == obj)" + NL + "            return true;" + NL + "        if (obj == null)" + NL + "            return false;" + NL + "        if (getClass() != obj.getClass())" + NL + "            return false;" + NL + "        final ";
  protected final String TEXT_80 = " other = (";
  protected final String TEXT_81 = ") obj;" + NL;
  protected final String TEXT_82 = NL + NL + "        if (";
  protected final String TEXT_83 = "() == null)" + NL + "        {" + NL + "            if (other.";
  protected final String TEXT_84 = "() != null)" + NL + "            {" + NL + "                return false;" + NL + "            }" + NL + "        }";
  protected final String TEXT_85 = NL + "        else if (!equals(";
  protected final String TEXT_86 = "(), other.";
  protected final String TEXT_87 = "()))" + NL + "        {" + NL + "            return false;" + NL + "        }";
  protected final String TEXT_88 = NL + "        else if (";
  protected final String TEXT_89 = "().compareTo(other.";
  protected final String TEXT_90 = "()) != 0)" + NL + "        {" + NL + "            return false;" + NL + "        }";
  protected final String TEXT_91 = NL + "        else if (!";
  protected final String TEXT_92 = "().equals(other.";
  protected final String TEXT_93 = "()))" + NL + "        {" + NL + "            return false;" + NL + "        }";
  protected final String TEXT_94 = NL + "        return true;" + NL + "    }" + NL + "    " + NL + "}";
  protected final String TEXT_95 = NL;

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
     for (final IField f : ctxt.getSingleFields()) { 
    stringBuffer.append(TEXT_9);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_10);
    stringBuffer.append( f.getJavaInterfaceName() );
    stringBuffer.append(TEXT_11);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_12);
    }  // end for 
    stringBuffer.append(TEXT_13);
     for (final IField f : ctxt.getListFields()) { 
    stringBuffer.append(TEXT_14);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_15);
    stringBuffer.append( f.getJavaInterfaceName() );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_17);
    } // end for 
    stringBuffer.append(TEXT_18);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_19);
    stringBuffer.append( ctxt.getDeclarations() );
    stringBuffer.append(TEXT_20);
     for (final IField f : ctxt.getSingleFields()) {
if (f.isDate()) {

    stringBuffer.append(TEXT_21);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_22);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_24);
    } else if (f.isByteArray()) {
    stringBuffer.append(TEXT_25);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_26);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_27);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_28);
    } else { 
    stringBuffer.append(TEXT_29);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_30);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_31);
    } // end if 
    }  // end for 
    stringBuffer.append(TEXT_32);
     for (final IField f : ctxt.getListFields()) { 
    stringBuffer.append(TEXT_33);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_34);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_35);
    }  // end for 
    stringBuffer.append(TEXT_36);
     for (final IField f : ctxt.getSingleFields()) { 
    stringBuffer.append(TEXT_37);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_38);
    stringBuffer.append( f.getJavaInterfaceName() );
    stringBuffer.append(TEXT_39);
    stringBuffer.append( f.getNameAsAccessor() );
    stringBuffer.append(TEXT_40);
     if (f.isDate()) { 
    stringBuffer.append(TEXT_41);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_42);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_43);
     } else { 
    stringBuffer.append(TEXT_44);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_45);
     } 
    stringBuffer.append(TEXT_46);
    }  // end for 
    stringBuffer.append(TEXT_47);
     for (final IField f : ctxt.getListFields()) {
DomainObjectField dof = (DomainObjectField) f;
if (!dof.getType().hasIdField())
{
    continue;
}
 
    stringBuffer.append(TEXT_48);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_49);
    stringBuffer.append( f.getJavaInterfaceName() );
    stringBuffer.append(TEXT_50);
    stringBuffer.append( f.getNameAsAccessor() );
    stringBuffer.append(TEXT_51);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_52);
     
final String idType = ctxt.getJavaKeyTypeForField(f);
final String name = f.getJavaName();


    stringBuffer.append(TEXT_53);
    stringBuffer.append( idType );
    stringBuffer.append(TEXT_54);
    stringBuffer.append( f.getNameAsAccessor() );
    stringBuffer.append(TEXT_55);
    stringBuffer.append( f.getNameAsAccessor() );
    stringBuffer.append(TEXT_56);
    stringBuffer.append( name );
    stringBuffer.append(TEXT_57);
    stringBuffer.append( idType );
    stringBuffer.append(TEXT_58);
    stringBuffer.append( idType );
    stringBuffer.append(TEXT_59);
    stringBuffer.append( name );
    stringBuffer.append(TEXT_60);
    stringBuffer.append( dof.getIdAsAccessor() );
    stringBuffer.append(TEXT_61);
    } // end for 
    stringBuffer.append(TEXT_62);
     for (final DomainObjectField f : ctxt.getDomainObject().getObjectFields()) { 
if (!f.getType().hasIdField()) {
    continue;
}

    stringBuffer.append(TEXT_63);
    stringBuffer.append( f.getNameAsProperty() );
    stringBuffer.append(TEXT_64);
    stringBuffer.append( ctxt.getJavaKeyTypeForField(f) );
    stringBuffer.append(TEXT_65);
    stringBuffer.append( f.getNameAsAccessor() );
    stringBuffer.append(TEXT_66);
    stringBuffer.append( f.getNameAsAccessor() );
    stringBuffer.append(TEXT_67);
    stringBuffer.append( f.getNameAsAccessor() );
    stringBuffer.append(TEXT_68);
    stringBuffer.append( f.getIdAsAccessor() );
    stringBuffer.append(TEXT_69);
    }  // end for 
    stringBuffer.append(TEXT_70);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_71);
    stringBuffer.append( ctxt.getParameterListAsToString() );
    stringBuffer.append(TEXT_72);
    stringBuffer.append( ctxt.getToStringAccessorMethods("this") );
    stringBuffer.append(TEXT_73);
     for (final IField field : ctxt.getSingleFields()) { 
     if (field.isDate()) {
    stringBuffer.append(TEXT_74);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_75);
     } else { 
    stringBuffer.append(TEXT_76);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_77);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_78);
     } 
    }
    stringBuffer.append(TEXT_79);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_80);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_81);
     for (final IField field : ctxt.getSingleFields()) { 
    stringBuffer.append(TEXT_82);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_83);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_84);
     if (field.isDate()) {
    stringBuffer.append(TEXT_85);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_86);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_87);
     } else if (field.isBigDecimal()) { 
    stringBuffer.append(TEXT_88);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_89);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_90);
     } else { 
    stringBuffer.append(TEXT_91);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_92);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_93);
     }
    }
    stringBuffer.append(TEXT_94);
    stringBuffer.append(TEXT_95);
    return stringBuffer.toString();
  }
}