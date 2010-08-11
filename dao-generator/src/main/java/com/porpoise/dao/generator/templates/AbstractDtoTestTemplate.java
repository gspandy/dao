package com.porpoise.dao.generator.templates;

import java.util.*;
import com.porpoise.dao.generator.gen.*;
import com.porpoise.dao.generator.model.*;
import com.porpoise.generator.IGenerator;

public class AbstractDtoTestTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized AbstractDtoTestTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    AbstractDtoTestTemplate result = new AbstractDtoTestTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";" + NL + "" + NL + "import org.junit.Assert;" + NL + "import org.junit.Ignore;" + NL + "" + NL + "/**" + NL + " */" + NL + "@Ignore" + NL + "public abstract class AbstractDtoTest {" + NL + "\tprotected final static void assertEquals(final Object dto1," + NL + "\t\t\tfinal Object dto2) {" + NL + "\t\tAssert.assertEquals(dto1, dto2);" + NL + "\t\tAssert.assertEquals(dto1, dto1);" + NL + "\t\tAssert.assertEquals(dto2, dto2);" + NL + "" + NL + "\t\tAssert.assertEquals(dto2.hashCode(), dto2.hashCode());" + NL + "\t\tAssert.assertEquals(dto1.hashCode(), dto1.hashCode());" + NL + "\t\tAssert.assertEquals(dto1.hashCode(), dto2.hashCode());" + NL + "\t}" + NL + "" + NL + "\tprotected final static void assertNotEquals(final Object dto1," + NL + "\t\t\tfinal Object dto2) {" + NL + "\t\tAssert.assertFalse(dto1.equals(dto2));" + NL + "\t\tAssert.assertEquals(dto1, dto1);" + NL + "\t\tAssert.assertEquals(dto2, dto2);" + NL + "" + NL + "\t\tAssert.assertEquals(dto2.hashCode(), dto2.hashCode());" + NL + "\t\tAssert.assertEquals(dto1.hashCode(), dto1.hashCode());" + NL + "\t\tAssert.assertFalse(dto1.hashCode() == dto2.hashCode());" + NL + "\t}" + NL + "}";

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