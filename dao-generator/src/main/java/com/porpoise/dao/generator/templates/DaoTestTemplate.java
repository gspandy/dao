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
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + NL + "package ";
  protected final String TEXT_3 = ";" + NL + "" + NL + "import java.io.File;" + NL + "import java.math.BigDecimal;" + NL + "import java.sql.ResultSet;" + NL + "import java.sql.SQLException;" + NL + "import java.util.List;" + NL + "" + NL + "import ";
  protected final String TEXT_4 = ".model.";
  protected final String TEXT_5 = "Dto;" + NL + "" + NL + "import org.junit.AfterClass;" + NL + "import org.junit.BeforeClass;" + NL + "import org.junit.Test;" + NL + "" + NL + "import com.porpoise.dao.database.DbConnectionDetails;" + NL + "import com.porpoise.dao.database.DbConnectionFactory;" + NL + "import com.porpoise.dao.database.init.Databases;" + NL + "" + NL + "" + NL + "/**" + NL + " * Tests for the ";
  protected final String TEXT_6 = "Dao class" + NL + " */" + NL + "public class ";
  protected final String TEXT_7 = "DaoTest" + NL + "{" + NL + "    private static DbConnectionFactory factory;" + NL + "" + NL + "    @BeforeClass" + NL + "    public static void setup()" + NL + "    {" + NL + "        final DbConnectionDetails config = new DbConnectionDetails();" + NL + "" + NL + "        final File testDir = new File(System.getProperty(\"user.dir\"));" + NL + "        final String url = new File(testDir, \"dao-gen-test\").getAbsolutePath();" + NL + "        config.setDatabaseName(url);" + NL + "        factory = Databases.DERBY.init(config);" + NL + "    }" + NL + "" + NL + "    @AfterClass" + NL + "    public static void tearDown()" + NL + "    {" + NL + "        factory.closeAllConnections();" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * " + NL + "     */" + NL + "    @Test" + NL + "    public void test_findById()" + NL + "    {" + NL + "        // create an entry to find" + NL + "        final ";
  protected final String TEXT_8 = "Dto dto = new ";
  protected final String TEXT_9 = "Dto(1, \"lastname\", \"firstname\");" + NL + "        dao.insert(factory, dto);" + NL + "" + NL + "        // find our new entry        " + NL + "        final ";
  protected final String TEXT_10 = "Dto read = dao.findById(factory, Long.valueOf(1));" + NL + "        Assert.assertEquals(dto, read);" + NL + "    }" + NL + "" + NL + "}";

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
    stringBuffer.append( n );
    stringBuffer.append(TEXT_8);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_10);
    return stringBuffer.toString();
  }
}