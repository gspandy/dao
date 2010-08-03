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
  protected final String TEXT_3 = ";" + NL + "" + NL + "import java.math.BigDecimal;" + NL + "import java.sql.ResultSet;" + NL + "import java.sql.SQLException;" + NL + "import java.util.List;" + NL + "" + NL + "import ";
  protected final String TEXT_4 = ".model.";
  protected final String TEXT_5 = "Dto;" + NL + "" + NL + "import org.junit.BeforeClass;" + NL + "import org.junit.Test;" + NL + "" + NL + "/**" + NL + " * Tests for the AaronDao class" + NL + " */" + NL + "public class ";
  protected final String TEXT_6 = "DaoTest" + NL + "{" + NL + "" + NL + "    @BeforeClass" + NL + "    public static void setup()" + NL + "    {" + NL + "" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * " + NL + "     */" + NL + "    @Test" + NL + "    public void test_findById()" + NL + "    {" + NL + "    }" + NL + "" + NL + "}";

   /* (non-javadoc)
    * @see IGenerator#generate(Object)
    */
   public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
     final DaoContext ctxt = (DaoContext) argument; 
final String n = ctxt.getName();

    stringBuffer.append(TEXT_2);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_4);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_6);
    return stringBuffer.toString();
  }
}