package com.porpoise.dao.generator.templates;

import com.porpoise.generator.*;
import java.util.*;
import com.porpoise.dao.generator.gen.*;
import com.porpoise.dao.generator.model.*;
import com.porpoise.generator.model.*;
import com.porpoise.dao.generator.model.api.*;

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
  protected final String TEXT_2 = ".assembler;" + NL + "" + NL + "" + NL + "import com.google.common.collect.Collections2;" + NL + "import com.google.common.collect.ComputationException;" + NL + "" + NL + "import java.util.concurrent.ConcurrentMap;" + NL + "import java.util.*;" + NL + "" + NL + "import com.google.common.base.Function;" + NL + "import com.google.common.collect.MapMaker;" + NL + "import ";
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
  protected final String TEXT_29 = "Dao.findById(transaction, id);" + NL + "\t\tif (dto == null)" + NL + "\t\t{" + NL + "\t\t    return null;" + NL + "\t\t}";
  protected final String TEXT_30 = NL;
  protected final String TEXT_31 = " = ";
  protected final String TEXT_32 = " ";
  protected final String TEXT_33 = NL + "       ";
  protected final String TEXT_34 = "(dto.";
  protected final String TEXT_35 = "(), transaction);";
  protected final String TEXT_36 = NL + "       dto.";
  protected final String TEXT_37 = "();";
  protected final String TEXT_38 = NL;
  protected final String TEXT_39 = NL + "       ";
  protected final String TEXT_40 = " = null;";
  protected final String TEXT_41 = NL + "\t\tfinal Collection<";
  protected final String TEXT_42 = "> ";
  protected final String TEXT_43 = " = new ";
  protected final String TEXT_44 = "Dao()" + NL + "\t\t\t.findBy";
  protected final String TEXT_45 = "(transaction, ";
  protected final String TEXT_46 = ");";
  protected final String TEXT_47 = NL;
  protected final String TEXT_48 = " = Collections2.transform(" + NL + "\t\t\t";
  protected final String TEXT_49 = ", new Function<";
  protected final String TEXT_50 = ", I";
  protected final String TEXT_51 = ">() {" + NL + "\t\t\t\t@Override" + NL + "\t\t\t\tpublic I";
  protected final String TEXT_52 = " apply(final ";
  protected final String TEXT_53 = " from) {" + NL + "\t\t\t\t\treturn get";
  protected final String TEXT_54 = "(from.";
  protected final String TEXT_55 = "(), transaction);" + NL + "\t\t\t\t}" + NL + "\t\t});" + NL + "\t\t";
  protected final String TEXT_56 = NL + NL + "\t\tfinal I";
  protected final String TEXT_57 = " value = new ";
  protected final String TEXT_58 = "(";
  protected final String TEXT_59 = ");" + NL + "" + NL + "\t\treturn value;" + NL + "\t}" + NL + "" + NL + "    /**" + NL + "     * @return the I";
  protected final String TEXT_60 = " for the given Id " + NL + "     */" + NL + "\tpublic I";
  protected final String TEXT_61 = " get";
  protected final String TEXT_62 = "(final ";
  protected final String TEXT_63 = " id, final IDbTransaction tx) {" + NL + "\t\tI";
  protected final String TEXT_64 = " value;" + NL + "\t\tif (id == null || id.intValue() == 0) {" + NL + "\t\t\tvalue = null;" + NL + "\t\t} else {" + NL + "\t\t\ttry {" + NL + "\t\t\t\tvalue = ";
  protected final String TEXT_65 = "ById.get(new FindKey<";
  protected final String TEXT_66 = ">(id, tx));" + NL + "\t\t\t} catch (final ComputationException e) {" + NL + "\t\t\t\tonError(e);" + NL + "\t\t\t\tvalue = null;" + NL + "\t\t\t}" + NL + "\t\t}" + NL + "\t\treturn value;" + NL + "\t}";
  protected final String TEXT_67 = NL + NL + "    private void onError(ComputationException e)" + NL + "    {" + NL + "        e.printStackTrace();" + NL + "    }" + NL + "" + NL + "}";
  protected final String TEXT_68 = NL;

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
     for (final IField field : t.getDomainObject().getSingleFields()) 
{
   final String decl = t.getDeclarationForField(field);
   final boolean requiresLoad = field instanceof DomainObjectField;

    stringBuffer.append(TEXT_30);
    stringBuffer.append( decl );
    stringBuffer.append(TEXT_31);
    
    if (requiresLoad) {
        final DomainObjectField dof = (DomainObjectField)field;
        final String idAccessor = dof.getType().getIdField().getNameAsAccessor();
    
    stringBuffer.append(TEXT_32);
    stringBuffer.append(TEXT_33);
    stringBuffer.append( dof.getNameAsAccessor() );
    stringBuffer.append(TEXT_34);
    stringBuffer.append( idAccessor );
    stringBuffer.append(TEXT_35);
     } else { // primitive 
    stringBuffer.append(TEXT_36);
    stringBuffer.append( field.getNameAsAccessor() );
    stringBuffer.append(TEXT_37);
     } // end if primitive 
     }// end for single fields 
    stringBuffer.append(TEXT_38);
     for (final IField field : t.getDomainObject().getListFields()) 
{
   final DomainObjectField dof = (DomainObjectField)field;

   final String decl = t.getDeclarationForField(field);
   final String coll = field.getNameAsProperty()  + "DtoCollection";
   final String type = field.getJavaTypeName();
//   final String pkId = field.getNameAsAccessor();
   final String dto  = field.getJavaTypeName() + "Dto";

   if (!dof.hasId())
   {

    stringBuffer.append(TEXT_39);
    stringBuffer.append( decl );
    stringBuffer.append(TEXT_40);
    
       continue;
   }

   final Field id = t.getDomainObject().getIdField();

    stringBuffer.append(TEXT_41);
    stringBuffer.append( dto );
    stringBuffer.append(TEXT_42);
    stringBuffer.append( coll );
    stringBuffer.append(TEXT_43);
    stringBuffer.append( type );
    stringBuffer.append(TEXT_44);
    stringBuffer.append( id.getJavaName() );
    stringBuffer.append(TEXT_45);
    stringBuffer.append( id.getNameAsProperty() );
    stringBuffer.append(TEXT_46);
    stringBuffer.append(TEXT_47);
    stringBuffer.append( decl );
    stringBuffer.append(TEXT_48);
    stringBuffer.append( coll );
    stringBuffer.append(TEXT_49);
    stringBuffer.append( dto );
    stringBuffer.append(TEXT_50);
    stringBuffer.append( type );
    stringBuffer.append(TEXT_51);
    stringBuffer.append( type );
    stringBuffer.append(TEXT_52);
    stringBuffer.append( dto );
    stringBuffer.append(TEXT_53);
    stringBuffer.append( type );
    stringBuffer.append(TEXT_54);
    stringBuffer.append( dof.getIdAsAccessor());
    stringBuffer.append(TEXT_55);
     }// end for single fields 
    stringBuffer.append(TEXT_56);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_57);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_58);
    stringBuffer.append( t.getDeclarationVariables() );
    stringBuffer.append(TEXT_59);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_60);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_61);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_62);
    stringBuffer.append( k );
    stringBuffer.append(TEXT_63);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_64);
    stringBuffer.append( t.getPropertyName() );
    stringBuffer.append(TEXT_65);
    stringBuffer.append( k );
    stringBuffer.append(TEXT_66);
     } // end for 
    stringBuffer.append(TEXT_67);
    stringBuffer.append(TEXT_68);
    return stringBuffer.toString();
  }
}