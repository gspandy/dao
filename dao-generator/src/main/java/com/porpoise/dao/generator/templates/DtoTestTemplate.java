package com.porpoise.dao.generator.templates;

import com.porpoise.generator.*;
import java.util.*;
import com.porpoise.dao.generator.gen.*;
import com.porpoise.dao.generator.model.*;

public class DtoTestTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized DtoTestTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    DtoTestTemplate result = new DtoTestTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";" + NL + "" + NL + "import java.io.File;" + NL + "import java.math.BigDecimal;" + NL + "import java.sql.ResultSet;" + NL + "import java.sql.SQLException;" + NL + "import java.util.Date;" + NL + "import java.util.Collection;" + NL + "import java.util.List;" + NL + "" + NL + "import ";
  protected final String TEXT_3 = ".model.";
  protected final String TEXT_4 = "Dto;" + NL + "" + NL + "import org.junit.Assert;" + NL + "import org.junit.After;" + NL + "import org.junit.AfterClass;" + NL + "import org.junit.Before;" + NL + "import org.junit.BeforeClass;" + NL + "import org.junit.Test;" + NL + "" + NL + "" + NL + "/**" + NL + " * Tests for the ";
  protected final String TEXT_5 = "Dto class" + NL + " */" + NL + "public class ";
  protected final String TEXT_6 = "DtoTest extends AbstractDtoTest" + NL + "{" + NL + "    /**" + NL + "     * test the DAO can create and find an entry " + NL + "     */" + NL + "    @Test" + NL + "    public void test_equalsAndHashCodeForTwoEquivalentObjects()" + NL + "    {";
  protected final String TEXT_7 = NL + "        final ";
  protected final String TEXT_8 = " id = ";
  protected final String TEXT_9 = ".valueOf(1);";
  protected final String TEXT_10 = NL + "        final ";
  protected final String TEXT_11 = "Dto dto1 = new ";
  protected final String TEXT_12 = "Dto(";
  protected final String TEXT_13 = ");" + NL + "        final ";
  protected final String TEXT_14 = "Dto dto2 = new ";
  protected final String TEXT_15 = "Dto(";
  protected final String TEXT_16 = ");" + NL + "" + NL + "        assertEquals(dto1, dto2);        " + NL + "    }" + NL + "    /**" + NL + "     * test the DAO can create and find an entry " + NL + "     */" + NL + "    @Test" + NL + "    public void test_equalsAndHashCodeForTwoDifferenceObjects()" + NL + "    {";
  protected final String TEXT_17 = NL + "        final ";
  protected final String TEXT_18 = " id = ";
  protected final String TEXT_19 = ".valueOf(1);";
  protected final String TEXT_20 = NL + "        final ";
  protected final String TEXT_21 = "Dto dto1 = new ";
  protected final String TEXT_22 = "Dto(";
  protected final String TEXT_23 = ");" + NL + "        final ";
  protected final String TEXT_24 = "Dto dto2 = new ";
  protected final String TEXT_25 = "Dto(";
  protected final String TEXT_26 = ");" + NL + "" + NL + "        assertNotEquals(dto1, dto2);        " + NL + "    }" + NL + "}";

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
    stringBuffer.append( ctxt.getIdField().getJavaTypeName() );
    stringBuffer.append(TEXT_8);
    stringBuffer.append( ctxt.getIdField().getJavaTypeName() );
    stringBuffer.append(TEXT_9);
     } 
    stringBuffer.append(TEXT_10);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_11);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( ctxt.getTestValues() );
    stringBuffer.append(TEXT_13);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_14);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_15);
    stringBuffer.append( ctxt.getTestValues() );
    stringBuffer.append(TEXT_16);
     if (ctxt.hasIdField()) { 
    stringBuffer.append(TEXT_17);
    stringBuffer.append( ctxt.getIdField().getJavaTypeName() );
    stringBuffer.append(TEXT_18);
    stringBuffer.append( ctxt.getIdField().getJavaTypeName() );
    stringBuffer.append(TEXT_19);
     } 
    stringBuffer.append(TEXT_20);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_21);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_22);
    stringBuffer.append( ctxt.getTestValues() );
    stringBuffer.append(TEXT_23);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_24);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_25);
    stringBuffer.append( ctxt.getOtherTestValues() );
    stringBuffer.append(TEXT_26);
    return stringBuffer.toString();
  }
}