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
  protected final String TEXT_2 = ";" + NL + "" + NL + "import java.math.BigDecimal;" + NL + "import java.sql.ResultSet;" + NL + "import java.sql.SQLException;" + NL + "import java.util.Collection;" + NL + "import java.util.List;" + NL + "import java.util.Date;" + NL + "" + NL + "import com.google.common.collect.ImmutableList;" + NL + "import com.google.common.collect.Iterables;" + NL + "import com.google.common.collect.Lists;" + NL + "import com.porpoise.dao.database.IDbTransaction;" + NL + "import com.porpoise.dao.database.DbConnectionFactory;" + NL + "import com.porpoise.dao.database.dao.AbstractDao;" + NL + "import com.porpoise.dao.database.visitors.AbstractResultSetVisitor;" + NL + "" + NL + "/**" + NL + " * Generator class used to create source code/resources from an in-memory schema" + NL + " */" + NL + "public class Generator" + NL + "{" + NL + "public class GeneratorMain {" + NL + "" + NL + "\tpublic static void main(final String[] args) throws IOException {" + NL + "\t    final Collection<Table> tables = createTables();" + NL + "" + NL + "\t\tfinal File dest = new File(System.getProperty(\"user.dir\")," + NL + "\t\t\t\t\"generator-main\");" + NL + "\t\tfinal String packageName = args.length == 1 ? args[0] : \"com.example\";" + NL + "\t\tDaoGenerator.generateProject(new ProjectDefinition(tables, dest, packageName + \".dao\", \"main-test-dao\", packageName));\t    " + NL + "\t}" + NL + "" + NL + "\tprivate static Collection<Table> createTables() throws IOException {" + NL + "\t\tfinal Collection<Table> tables = Lists.newArrayList();" + NL + NL + NL;
  protected final String TEXT_3 = NL;
  protected final String TEXT_4 = NL;
  protected final String TEXT_5 = NL;
  protected final String TEXT_6 = NL + "//============ COLUMN =============";
  protected final String TEXT_7 = NL;
  protected final String TEXT_8 = NL + NL + "\t\tfinal Table tbl = new Table(\"Aaron\");" + NL + "\t\ttbl.addColumn(\"ID\", true, ColType.Long);" + NL + "\t\ttbl.addColumn(\"Name\", false, ColType.String);" + NL + "" + NL + "\t\tfinal Table tbl2 = new Table(\"Benjamin\");" + NL + "\t\ttbl2.addColumn(\"ID\", true, ColType.Long);" + NL + "\t\tfinal Column fk = tbl2.addColumn(\"AARON_ID\", true, ColType.Long);" + NL + "\t\ttbl2.addColumn(\"Name\", false, ColType.String);" + NL + "" + NL + "\t\ttbl.oneToMany(fk);" + NL + "" + NL + "\t\ttables.add(tbl);" + NL + "\t\ttables.add(tbl2);" + NL + "" + NL + "        return tables;" + NL + "\t}" + NL + "}";
  protected final String TEXT_9 = NL;

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
    stringBuffer.append(TEXT_3);
    } // end for 
    stringBuffer.append(TEXT_4);
     for (final Column c : t.getColumns()) { 
    stringBuffer.append(TEXT_5);
     if (t.hasIdColumn() && t.getIdColumn().equals(c)) { 
//============ ID COLUMN =============

     } else { 
    stringBuffer.append(TEXT_6);
     } 
    stringBuffer.append(TEXT_7);
    } // end for 
    stringBuffer.append(TEXT_8);
    stringBuffer.append(TEXT_9);
    return stringBuffer.toString();
  }
}