package com.porpoise.dao.generator.templates;

import com.porpoise.generator.*;
import java.util.*;
import com.porpoise.dao.generator.gen.*;
import com.porpoise.dao.generator.model.*;

public class ApiImplTestTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized ApiImplTestTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ApiImplTestTemplate result = new ApiImplTestTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ".impl;" + NL + "" + NL + "import org.junit.Assert;" + NL + "import org.junit.Test;" + NL + "" + NL + "import com.google.common.collect.Iterables;" + NL + "import ";
  protected final String TEXT_3 = ".*;" + NL + "" + NL + "/**" + NL + " * Tests for ";
  protected final String TEXT_4 = "ServiceImpl" + NL + " */" + NL + "public class ";
  protected final String TEXT_5 = "ServiceImplTest extends AbstractServiceImplTest" + NL + "{" + NL + "\t/**" + NL + "\t * test which exercises:" + NL + "\t * <ul>" + NL + "\t * <li>{@link ";
  protected final String TEXT_6 = "ServiceImpl.listAllKeys()}</li>" + NL + "\t * <li>{@link ";
  protected final String TEXT_7 = "ServiceImpl.count()}</li>" + NL + "\t * <li>{@link ";
  protected final String TEXT_8 = "ServiceImpl.get(Integer)}</li>" + NL + "\t * <ul>" + NL + "\t * " + NL + "\t * This test assumes a connection to an access database with data." + NL + "\t */" + NL + "\t@Test" + NL + "\tpublic void test_canRead() {" + NL + "\t\t// create the object under test" + NL + "\t\tfinal ";
  protected final String TEXT_9 = "ServiceImpl service = new ";
  protected final String TEXT_10 = "ServiceImpl(getFactory());" + NL + "" + NL + "\t\t//" + NL + "\t\tfinal Iterable<Integer> allKeys = service.listAllKeys();" + NL + "" + NL + "\t\tfinal int actualCount = service.count(getFactory());" + NL + "\t\tAssert.assertEquals(actualCount, Iterables.size(allKeys));" + NL + "" + NL + "\t\tfor (final Integer key : allKeys) {" + NL + "\t\t\tfinal I";
  protected final String TEXT_11 = " obj = service.get(key);" + NL + "\t\t\tAssert.assertNotNull(obj);" + NL + "\t\t}" + NL + "\t}" + NL + "}";
  protected final String TEXT_12 = NL;

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
    stringBuffer.append( n );
    stringBuffer.append(TEXT_7);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_8);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_9);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_10);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_11);
    stringBuffer.append(TEXT_12);
    return stringBuffer.toString();
  }
}