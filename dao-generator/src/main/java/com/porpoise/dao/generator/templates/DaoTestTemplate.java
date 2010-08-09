package com.porpoise.dao.generator.templates;

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
  protected final String TEXT_2 = ";" + NL + "" + NL + "import java.io.File;" + NL + "import java.math.BigDecimal;" + NL + "import java.sql.ResultSet;" + NL + "import java.sql.SQLException;" + NL + "import java.util.Date;" + NL + "import java.util.List;" + NL + "" + NL + "import ";
  protected final String TEXT_3 = ".model.";
  protected final String TEXT_4 = "Dto;" + NL + "" + NL + "import org.junit.Assert;" + NL + "import org.junit.After;" + NL + "import org.junit.AfterClass;" + NL + "import org.junit.Before;" + NL + "import org.junit.BeforeClass;" + NL + "import org.junit.Test;" + NL + "" + NL + "import com.porpoise.dao.database.DbConnectionDetails;" + NL + "import com.porpoise.dao.database.DbConnectionFactory;" + NL + "import com.porpoise.dao.database.IDbTransaction;" + NL + "import com.porpoise.dao.database.init.Databases;" + NL + "" + NL + "" + NL + "/**" + NL + " * Tests for the ";
  protected final String TEXT_5 = "Dao class" + NL + " */" + NL + "public class ";
  protected final String TEXT_6 = "DaoTest extends AbstractDaoTest" + NL + "{";
  protected final String TEXT_7 = NL + "    /**" + NL + "     * test the DAO can create and find an entry " + NL + "     */" + NL + "    @Test" + NL + "    public void test_createAndFindById()" + NL + "    {" + NL + "        // create an entry to find" + NL + "        final ";
  protected final String TEXT_8 = "Dto dto = new ";
  protected final String TEXT_9 = "Dto(";
  protected final String TEXT_10 = ");" + NL + " " + NL + "        final ";
  protected final String TEXT_11 = "Dao dao = new ";
  protected final String TEXT_12 = "Dao(); " + NL + "        dao.insert(getTransaction(), dto);" + NL + "" + NL + "        // find our new entry        " + NL + "        final ";
  protected final String TEXT_13 = "Dto read = dao.findById(getTransaction(), Long.valueOf(1));" + NL + "        Assert.assertEquals(dto, read);" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * test the DAO can update an entry " + NL + "     */" + NL + "    @Test" + NL + "    public void test_update()" + NL + "    {" + NL + "        // create an entry to find" + NL + "        final ";
  protected final String TEXT_14 = "Dto dto = new ";
  protected final String TEXT_15 = "Dto(";
  protected final String TEXT_16 = ");" + NL + " " + NL + "        final ";
  protected final String TEXT_17 = "Dao dao = new ";
  protected final String TEXT_18 = "Dao(); " + NL + "        dao.insert(getTransaction(), dto);" + NL + "" + NL + "        // find our new entry        " + NL + "        final ";
  protected final String TEXT_19 = "Dto read = dao.findById(getTransaction(), Long.valueOf(1));" + NL + "        Assert.assertEquals(dto, read);" + NL + "    }";
  protected final String TEXT_20 = NL + "}";

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
     if (ctxt.hasIdField()) { 
    stringBuffer.append(TEXT_7);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_8);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( ctxt.getTestValues() );
    stringBuffer.append(TEXT_10);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_11);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_13);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_14);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_15);
    stringBuffer.append( ctxt.getTestValues() );
    stringBuffer.append(TEXT_16);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_17);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_18);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_19);
     } // end if has id  
    stringBuffer.append(TEXT_20);
    return stringBuffer.toString();
  }
}