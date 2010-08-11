package com.porpoise.dao.generator.templates;

import java.util.*;
import com.porpoise.dao.generator.gen.*;
import com.porpoise.dao.generator.model.*;

public class GeneratorTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized GeneratorTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    GeneratorTemplate result = new GeneratorTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";" + NL + "" + NL + "import java.io.File;" + NL + "import java.io.IOException;" + NL + "import java.util.*;" + NL + "" + NL + "import com.google.common.collect.Lists;" + NL + "import com.google.common.collect.ImmutableList;" + NL + "import com.porpoise.dao.generator.gen.DaoGenerator;" + NL + "import com.porpoise.dao.generator.gen.ApiGenerator;" + NL + "import com.porpoise.dao.generator.gen.ProjectDefinition;" + NL + "import com.porpoise.dao.generator.model.ColType;" + NL + "import com.porpoise.dao.generator.model.Column;" + NL + "import com.porpoise.dao.generator.model.Table;" + NL + "" + NL + "/**" + NL + " * Generator class used to create source code/resources from an in-memory schema" + NL + " */" + NL + "public class BaseGenerator" + NL + "{" + NL + "       private final ImmutableList<Table> tables;";
  protected final String TEXT_3 = NL + "        // =======================================================================================" + NL + "        // ";
  protected final String TEXT_4 = NL + "        // =======================================================================================" + NL + "\t\tpublic final Table ";
  protected final String TEXT_5 = ";";
  protected final String TEXT_6 = NL + "\t\tpublic final Column ";
  protected final String TEXT_7 = "__";
  protected final String TEXT_8 = ";";
  protected final String TEXT_9 = NL + NL + "\tpublic static void main(final String[] args) throws IOException {" + NL + "\t    final Collection<Table> tables = new BaseGenerator().getTables();" + NL + "" + NL + "\t\tfinal File dest = new File(System.getProperty(\"user.dir\")," + NL + "\t\t\t\t\"generator-main\");" + NL + "" + NL + "\t\tfinal File dao = new File(dest, \"dao\");" + NL + "\t\tfinal File api = new File(dest, \"api\");" + NL + "" + NL + "\t\tfinal String packageName = args.length == 1 ? args[0] : \"com.example\";" + NL + "\t\tfinal String group = packageName;" + NL + "\t\t" + NL + "\t\tnew DaoGenerator().generateProject(new ProjectDefinition(tables, dao, group, \"main-test-dao\", \"1.0.0\", packageName));" + NL + "" + NL + "\t\tnew ApiGenerator().generateProject(new ProjectDefinition(tables, api, group, \"api\", \"1.0.0\", packageName));" + NL + "\t}" + NL + "\t" + NL + "\t/** " + NL + "\t * @return the tables;" + NL + "\t */" + NL + "\tpublic Collection<Table> getTables()" + NL + "\t{" + NL + "\t    return this.tables;" + NL + "\t}" + NL + "" + NL + "    /** " + NL + "     * default constructor" + NL + "     */" + NL + "\tpublic BaseGenerator() throws IOException {" + NL + "\t\tfinal List<Table> allTables = Lists.newArrayList();" + NL;
  protected final String TEXT_10 = NL + "        // =======================================================================================" + NL + "        // ";
  protected final String TEXT_11 = NL + "        // =======================================================================================" + NL + "\t\t";
  protected final String TEXT_12 = " = new Table(\"";
  protected final String TEXT_13 = "\");";
  protected final String TEXT_14 = NL + "\t\t";
  protected final String TEXT_15 = " = ";
  protected final String TEXT_16 = ".";
  protected final String TEXT_17 = "(\"";
  protected final String TEXT_18 = "\", ";
  protected final String TEXT_19 = ", ColType.";
  protected final String TEXT_20 = ");" + NL + "" + NL + "\t\tallTables.add(";
  protected final String TEXT_21 = ");" + NL;
  protected final String TEXT_22 = NL + NL + "       makeJoins();" + NL + "       tables = ImmutableList.copyOf(allTables);" + NL + "\t}" + NL + "\t" + NL + "\t/** " + NL + "\t *" + NL + "\t */" + NL + "\tprivate void makeJoins()" + NL + "\t{" + NL + "\t";
  protected final String TEXT_23 = NL + "        // =======================================================================================" + NL + "        // ";
  protected final String TEXT_24 = "JOIN TABLE";
  protected final String TEXT_25 = " ";
  protected final String TEXT_26 = NL + "        // =======================================================================================";
  protected final String TEXT_27 = NL + "        ";
  protected final String TEXT_28 = ".fkReferenceTo(";
  protected final String TEXT_29 = ");" + NL + "        ";
  protected final String TEXT_30 = NL + "\t}" + NL + "}";
  protected final String TEXT_31 = NL;

   /* (non-javadoc)
    * @see IGenerator#generate(Object)
    */
   public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     final GeneratorContext ctxt = (GeneratorContext) argument; 

    stringBuffer.append(TEXT_1);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_2);
     for (final Table t : ctxt.getTables()) { 
       final String table = t.getTableName(); 

    stringBuffer.append(TEXT_3);
    stringBuffer.append( table );
    stringBuffer.append(TEXT_4);
    stringBuffer.append( table );
    stringBuffer.append(TEXT_5);
     for (final Column c : t.getColumns()) { 
    stringBuffer.append(TEXT_6);
    stringBuffer.append( table );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( c.getName() );
    stringBuffer.append(TEXT_8);
    } // end for column 
    } // end for table 
    stringBuffer.append(TEXT_9);
     for (final Table t : ctxt.getTables()) { 
       final String table = t.getTableName(); 

    stringBuffer.append(TEXT_10);
    stringBuffer.append( table );
    stringBuffer.append(TEXT_11);
    stringBuffer.append( table );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( table );
    stringBuffer.append(TEXT_13);
     for (final Column c : t.getColumns()) {
       final String column = c.getName();
       final String addMethod;
       final String field = table  + "__" + column;
       if (t.hasIdColumn() && t.getIdColumn().equals(c)) {
           addMethod = "addKeyColumn";
       } else { 
           addMethod = "addColumn";
       } 

    stringBuffer.append(TEXT_14);
    stringBuffer.append( field );
    stringBuffer.append(TEXT_15);
    stringBuffer.append( table );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( addMethod );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( column );
    stringBuffer.append(TEXT_18);
    stringBuffer.append( c.isRequired() );
    stringBuffer.append(TEXT_19);
    stringBuffer.append( c.getType() );
    stringBuffer.append(TEXT_20);
    stringBuffer.append( table );
    stringBuffer.append(TEXT_21);
    } // end for column 
    } // end for table 
    stringBuffer.append(TEXT_22);
     for (final Table t : ctxt.getTables()) {
	   
	   if (!t.hasForeignKeyReferences())
	   {
	       continue;
	   }
       final String table = t.getTableName();

    stringBuffer.append(TEXT_23);
     if (t.isJoinTable()) { 
    stringBuffer.append(TEXT_24);
     } 
    stringBuffer.append(TEXT_25);
    stringBuffer.append( table );
    stringBuffer.append(TEXT_26);
    
       for (final Reference r : t.getForeignKeyReferences()) {
           final String column = r.getFrom().getName();
           final String field = table  + "__" + column;
           final String other = r.getTo().getTable().getTableName() + "__" + r.getTo().getName();

    stringBuffer.append(TEXT_27);
    stringBuffer.append( field );
    stringBuffer.append(TEXT_28);
    stringBuffer.append( other );
    stringBuffer.append(TEXT_29);
         } // end for column 
      } // end for table 
    stringBuffer.append(TEXT_30);
    stringBuffer.append(TEXT_31);
    return stringBuffer.toString();
  }
}