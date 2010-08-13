package com.porpoise.dao.generator.templates;

import com.porpoise.generator.*;
import java.util.*;
import com.porpoise.dao.generator.gen.*;
import com.porpoise.dao.generator.model.*;
import com.porpoise.generator.model.*;

public class DomainObjectsTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized DomainObjectsTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    DomainObjectsTemplate result = new DomainObjectsTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ".assembler;" + NL + "" + NL + "import java.util.concurrent.ConcurrentMap;" + NL + "import java.util.*;" + NL + "" + NL + "import com.google.common.base.Function;" + NL + "import com.google.common.collect.MapMaker;" + NL + "import ";
  protected final String TEXT_3 = ".*;" + NL + "import ";
  protected final String TEXT_4 = ".domain.*;" + NL + "import ";
  protected final String TEXT_5 = ".model.*;" + NL + "import com.porpoise.dao.database.IDbTransaction;" + NL + "" + NL + "/**" + NL + " * An assembler class used to assemble/retrieve domain objects" + NL + " */" + NL + "public class DomainObjects {" + NL;
  protected final String TEXT_6 = NL + "\tprivate final ConcurrentMap<FindKey<";
  protected final String TEXT_7 = ">, I";
  protected final String TEXT_8 = "> ";
  protected final String TEXT_9 = "ById;";
  protected final String TEXT_10 = NL + NL + "    /**" + NL + "     * default constructor" + NL + "     */" + NL + "\tpublic DomainObjects() {" + NL + "\t    final int concurrencyLevel = 1;";
  protected final String TEXT_11 = NL + "        // =============================================================================" + NL + "        // ";
  protected final String TEXT_12 = NL + "        // =============================================================================" + NL + "        {" + NL + "            final Function<FindKey<";
  protected final String TEXT_13 = ">, I";
  protected final String TEXT_14 = "> computingFunction = new Function<FindKey<Integer>, I";
  protected final String TEXT_15 = ">() {" + NL + "\t\t\t@Override" + NL + "\t\t\tpublic I";
  protected final String TEXT_16 = " apply(final FindKey<Integer> from) {" + NL + "\t\t\t\tfinal IDbTransaction transaction = from.getTx();" + NL + "\t\t\t\tfinal ";
  protected final String TEXT_17 = " id = from.getKey();" + NL + "\t\t\t\treturn load";
  protected final String TEXT_18 = "(transaction, id);" + NL + "\t\t\t}" + NL + "" + NL + "\t\t    };";
  protected final String TEXT_19 = NL + "            ";
  protected final String TEXT_20 = "ById = new MapMaker().concurrencyLevel(concurrencyLevel).makeComputingMap(computingFunction);" + NL + "        }" + NL;
  protected final String TEXT_21 = NL + "\t}" + NL;
  protected final String TEXT_22 = NL + NL + "\tprotected I";
  protected final String TEXT_23 = " load";
  protected final String TEXT_24 = "(final IDbTransaction transaction," + NL + "\t\t\tfinal Integer id) {" + NL + "\t\tfinal ";
  protected final String TEXT_25 = "Dao ";
  protected final String TEXT_26 = "Dao = new ";
  protected final String TEXT_27 = "Dao();" + NL + "\t\tfinal ";
  protected final String TEXT_28 = "Dto dto = ";
  protected final String TEXT_29 = "Dao.findById(transaction, id);" + NL + "" + NL + "\t\t";
  protected final String TEXT_30 = ";" + NL + "" + NL + "\t\tfinal I";
  protected final String TEXT_31 = " value = new ";
  protected final String TEXT_32 = "(";
  protected final String TEXT_33 = ");" + NL + "" + NL + "\t\treturn value;" + NL + "\t}" + NL + "" + NL + "    /**" + NL + "     * @return the I";
  protected final String TEXT_34 = " for the given Id " + NL + "     */" + NL + "\tpublic I";
  protected final String TEXT_35 = " get";
  protected final String TEXT_36 = "(final ";
  protected final String TEXT_37 = " id, final IDbTransaction tx) {" + NL + "\t\treturn ";
  protected final String TEXT_38 = "ById.get(new FindKey<";
  protected final String TEXT_39 = ">(id, tx));" + NL + "\t}";
  protected final String TEXT_40 = NL + NL + "}";
  protected final String TEXT_41 = NL;

   /* (non-javadoc)
    * @see IGenerator#generate(Object)
    */
   public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     final 
DomainObjectContext ctxt = (DomainObjectContext) argument; 

    stringBuffer.append(TEXT_1);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_2);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_4);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_5);
    
for (final ApiContext t : ctxt.getTableContextsWithIds())
{

    stringBuffer.append(TEXT_6);
    stringBuffer.append( t.getIdType() );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( t.getJavaName() );
    stringBuffer.append(TEXT_8);
    stringBuffer.append( t.getPropertyName() );
    stringBuffer.append(TEXT_9);
     } 
    stringBuffer.append(TEXT_10);
    
for (final ApiContext t : ctxt.getTableContextsWithIds())
{
    final String k = t.getIdType();
    final String n = t.getJavaName();
    final String p = t.getPropertyName();

    stringBuffer.append(TEXT_11);
    stringBuffer.append( t.getJavaName() );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( k );
    stringBuffer.append(TEXT_13);
    stringBuffer.append( t.getJavaName() );
    stringBuffer.append(TEXT_14);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_15);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( k );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_18);
    stringBuffer.append(TEXT_19);
    stringBuffer.append( t.getPropertyName() );
    stringBuffer.append(TEXT_20);
     } // end for 
    stringBuffer.append(TEXT_21);
    
for (final ApiContext t : ctxt.getTableContextsWithIds())
{
    final String k = t.getIdType();
    final String n = t.getJavaName();
    final String p = t.getPropertyName();

    stringBuffer.append(TEXT_22);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_24);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_25);
    stringBuffer.append( p );
    stringBuffer.append(TEXT_26);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_27);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_28);
    stringBuffer.append( p );
    stringBuffer.append(TEXT_29);
    stringBuffer.append( t.getDeclarationDefinitions("dto") );
    stringBuffer.append(TEXT_30);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_31);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_32);
    stringBuffer.append( t.getDeclarationVariables() );
    stringBuffer.append(TEXT_33);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_34);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_35);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_36);
    stringBuffer.append( k );
    stringBuffer.append(TEXT_37);
    stringBuffer.append( t.getPropertyName() );
    stringBuffer.append(TEXT_38);
    stringBuffer.append( k );
    stringBuffer.append(TEXT_39);
     } // end for 
    stringBuffer.append(TEXT_40);
    stringBuffer.append(TEXT_41);
    return stringBuffer.toString();
  }
}