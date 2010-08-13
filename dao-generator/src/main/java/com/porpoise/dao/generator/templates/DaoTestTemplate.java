package com.porpoise.dao.generator.templates;

import com.porpoise.generator.*;
import java.util.*;
import com.porpoise.dao.generator.gen.*;
import com.porpoise.dao.generator.model.*;

public class DaoTestTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized DaoTestTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    DaoTestTemplate result = new DaoTestTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";" + NL + "" + NL + "import ";
  protected final String TEXT_3 = ".model.*;" + NL + "" + NL + "import java.io.File;" + NL + "import java.math.BigDecimal;" + NL + "import java.sql.ResultSet;" + NL + "import java.sql.SQLException;" + NL + "import java.util.Date;" + NL + "import java.util.Collection;" + NL + "import java.util.List;" + NL + "" + NL + "import ";
  protected final String TEXT_4 = ".model.";
  protected final String TEXT_5 = "Dto;" + NL + "" + NL + "import org.junit.Assert;" + NL + "import org.junit.After;" + NL + "import org.junit.AfterClass;" + NL + "import org.junit.Before;" + NL + "import org.junit.BeforeClass;" + NL + "import org.junit.Test;" + NL + "" + NL + "import com.google.common.collect.Iterables;" + NL + "import com.porpoise.dao.database.DbConnectionDetails;" + NL + "import com.porpoise.dao.database.DbConnectionFactory;" + NL + "import com.porpoise.dao.database.IDbTransaction;" + NL + "import com.porpoise.dao.database.init.Databases;" + NL + "" + NL + "" + NL + "/**" + NL + " * Tests for the ";
  protected final String TEXT_6 = "Dao class" + NL + " */" + NL + "public class ";
  protected final String TEXT_7 = "DaoTest extends AbstractDaoTest" + NL + "{" + NL + "    /**" + NL + "     * test the DAO can create and find an entry " + NL + "     */" + NL + "    @Test" + NL + "    public void test_listAll()" + NL + "    {" + NL + "        final Collection<";
  protected final String TEXT_8 = "Dto> values = new ";
  protected final String TEXT_9 = "Dao().listAll(getTransaction());" + NL + "        for (final ";
  protected final String TEXT_10 = "Dto value : values)" + NL + "        {" + NL + "            println(value);" + NL + "        }" + NL + " " + NL + "        Assert.assertFalse(values.isEmpty());" + NL + "    }" + NL;
  protected final String TEXT_11 = NL + NL + "    /**" + NL + "     * data-dependant test for the ";
  protected final String TEXT_12 = " method" + NL + "     */" + NL + "    @Test" + NL + "    public void test_";
  protected final String TEXT_13 = "()" + NL + "    {";
  protected final String TEXT_14 = NL + "\t\tfinal ";
  protected final String TEXT_15 = "Dao dao = new ";
  protected final String TEXT_16 = "Dao();" + NL + "\t\tfinal Collection<";
  protected final String TEXT_17 = "Dto> all = new ";
  protected final String TEXT_18 = "Dao()" + NL + "\t\t\t\t.listAll(getTransaction());" + NL + "\t\t\t\t" + NL + "\t\tfinal Integer id = Iterables.getLast(all).";
  protected final String TEXT_19 = "();" + NL + "\t\tfinal Collection<";
  protected final String TEXT_20 = "Dto> results = dao.findBy";
  protected final String TEXT_21 = "Id(getTransaction(), id);" + NL + "" + NL + "\t\tAssert.assertFalse(results.isEmpty());" + NL + "    }";
  protected final String TEXT_22 = NL;
  protected final String TEXT_23 = NL + "    /**" + NL + "     * test the DAO can create and find an entry " + NL + "     */" + NL + "    @Test" + NL + "    public void test_createAndFindById()" + NL + "    {" + NL + "        // create the DAO to test" + NL + "        final ";
  protected final String TEXT_24 = "Dao dao = new ";
  protected final String TEXT_25 = "Dao();" + NL + "         " + NL + "        final ";
  protected final String TEXT_26 = " id = dao.nextId(getTransaction());" + NL + "" + NL + "        // create an entry to find" + NL + "        final ";
  protected final String TEXT_27 = "Dto dto = new ";
  protected final String TEXT_28 = "Dto(";
  protected final String TEXT_29 = ");" + NL + " " + NL + "        dao.insert(getTransaction(), dto);" + NL + "" + NL + "        // call the method under test - find our new entry        " + NL + "        final ";
  protected final String TEXT_30 = "Dto read = dao.findById(getTransaction(), id);" + NL + "        Assert.assertEquals(dto, read);" + NL + "        Assert.assertEquals(dto.hashCode(), read.hashCode());" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * test the DAO can update an entry " + NL + "     */" + NL + "    @Test" + NL + "    public void test_update()" + NL + "    {" + NL + "        final ";
  protected final String TEXT_31 = "Dao dao = new ";
  protected final String TEXT_32 = "Dao();" + NL + "        final ";
  protected final String TEXT_33 = " id = dao.nextId(getTransaction());" + NL + "        " + NL + "        // create an entry to find" + NL + "        final ";
  protected final String TEXT_34 = "Dto dto = new ";
  protected final String TEXT_35 = "Dto(";
  protected final String TEXT_36 = ");" + NL + " " + NL + "        dao.insert(getTransaction(), dto);" + NL + "" + NL + "        // find our new entry        " + NL + "        final ";
  protected final String TEXT_37 = "Dto read = dao.findById(getTransaction(), id);" + NL + "        Assert.assertEquals(dto, read);" + NL + "        Assert.assertEquals(dto.hashCode(), read.hashCode());" + NL + "    }";
  protected final String TEXT_38 = NL + "}";

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
    stringBuffer.append( ctxt.getPackageName() );
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
    stringBuffer.append( n );
    stringBuffer.append(TEXT_10);
     for (final Reference r : ctxt.getReferencesToThisTable()) { 
final Column col = ctxt.resolveColumn(r.getFrom());
final Column pk = ctxt.resolvePrimaryKey(col);



final String id = pk.getTable().getJavaName() + "Id";
final String methodName = "findBy" + pk.getNameAsJava() + "Id";


    stringBuffer.append(TEXT_11);
    stringBuffer.append( methodName );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( methodName );
    stringBuffer.append(TEXT_13);
    
        final String fromTable = col.getTable().getJavaName();

    stringBuffer.append(TEXT_14);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_15);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( fromTable );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( fromTable );
    stringBuffer.append(TEXT_18);
    stringBuffer.append( pk.getNameAsAccessor() );
    stringBuffer.append(TEXT_19);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_20);
    stringBuffer.append( fromTable );
    stringBuffer.append(TEXT_21);
     } 
    stringBuffer.append(TEXT_22);
     if (ctxt.hasIdField()) { 
    stringBuffer.append(TEXT_23);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_24);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_25);
    stringBuffer.append( ctxt.getIdField().getJavaTypeName() );
    stringBuffer.append(TEXT_26);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_27);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_28);
    stringBuffer.append( ctxt.getTestValues() );
    stringBuffer.append(TEXT_29);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_30);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_31);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_32);
    stringBuffer.append( ctxt.getIdField().getJavaTypeName() );
    stringBuffer.append(TEXT_33);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_34);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_35);
    stringBuffer.append( ctxt.getTestValues() );
    stringBuffer.append(TEXT_36);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_37);
     } // end if has id  
    stringBuffer.append(TEXT_38);
    return stringBuffer.toString();
  }
}