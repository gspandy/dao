package com.porpoise.dao.generator.templates;

import java.util.*;
import com.porpoise.dao.generator.gen.*;
import com.porpoise.dao.generator.model.*;

public class AbstractDaoTestTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized AbstractDaoTestTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    AbstractDaoTestTemplate result = new AbstractDaoTestTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";" + NL + "" + NL + "import java.io.File;" + NL + "import java.math.BigDecimal;" + NL + "import java.sql.ResultSet;" + NL + "import java.sql.SQLException;" + NL + "import java.util.Date;" + NL + "import java.util.List;" + NL + "" + NL + "import org.junit.Assert;" + NL + "import org.junit.After;" + NL + "import org.junit.AfterClass;" + NL + "import org.junit.Before;" + NL + "import org.junit.BeforeClass;" + NL + "import org.junit.Test;" + NL + "import org.junit.Ignore;" + NL + "" + NL + "import com.porpoise.dao.database.DbConnectionDetails;" + NL + "import com.porpoise.dao.database.DbConnectionFactory;" + NL + "import com.porpoise.dao.database.IDbTransaction;" + NL + "import com.porpoise.dao.database.init.Databases;" + NL + "" + NL + "" + NL + "/**" + NL + " */" + NL + "@Ignore" + NL + "public abstract class AbstractDaoTest" + NL + "{" + NL + "    private static DbConnectionFactory factory;" + NL + "    private IDbTransaction      transaction;" + NL + "    " + NL + "    /**" + NL + "     * @return the transaction" + NL + "     */" + NL + "    protected IDbTransaction getTransaction()" + NL + "    {" + NL + "        return transaction;" + NL + "    }" + NL + "" + NL + "    @BeforeClass" + NL + "    public static void setupClass()" + NL + "    {" + NL + "\t\tif (factory == null) {" + NL + "\t\t\tfactory = initAccess();" + NL + "\t\t\t// factory = initDerby();" + NL + "\t\t}" + NL + "\t}" + NL + "" + NL + "\tprivate static DbConnectionFactory initAccess() {" + NL + "\t\tfinal DbConnectionDetails config = new DbConnectionDetails();" + NL + "\t\tconfig.setUrl(\"c:/temp/Meteor12.mdb\");" + NL + "\t\treturn Databases.ACCESS.init(config);" + NL + "\t}" + NL + "    " + NL + "    private static DbConnectionFactory initDerby()" + NL + "    {" + NL + "        final DbConnectionDetails config = new DbConnectionDetails();" + NL + "        final File testDir = new File(System.getProperty(\"user.dir\"));" + NL + "        final String url = new File(testDir, \"dao-gen-test\").getAbsolutePath();" + NL + "        config.setDatabaseName(url);" + NL + "        return Databases.DERBY.init(config);" + NL + "    }" + NL + "    " + NL + "    @Before" + NL + "    public void setup()" + NL + "    {" + NL + "        transaction = factory.startNewTransaction();" + NL + "    }" + NL + "    " + NL + "    @After" + NL + "    public void tearDown()" + NL + "    {" + NL + "        transaction.rollback();" + NL + "    }" + NL + "" + NL + "    @AfterClass" + NL + "    public static void tearDownClass()" + NL + "    {" + NL + "        factory.closeAllConnections();" + NL + "    }" + NL + "}";

   /* (non-javadoc)
    * @see IGenerator#generate(Object)
    */
   public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     final DaoContext ctxt = (DaoContext) argument; 

    stringBuffer.append(TEXT_1);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}