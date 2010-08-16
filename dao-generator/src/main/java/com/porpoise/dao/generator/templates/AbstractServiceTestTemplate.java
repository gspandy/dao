package com.porpoise.dao.generator.templates;

import java.util.*;
import com.porpoise.generator.*;
import com.porpoise.dao.generator.gen.*;
import com.porpoise.dao.generator.model.*;

public class AbstractServiceTestTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized AbstractServiceTestTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    AbstractServiceTestTemplate result = new AbstractServiceTestTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ".impl;" + NL + "" + NL + "import ";
  protected final String TEXT_3 = ".*;" + NL + "import org.junit.Ignore;" + NL + "" + NL + "/**" + NL + " */" + NL + "@Ignore" + NL + "public abstract class AbstractServiceImplTest extends AbstractDaoTest" + NL + "{" + NL + "" + NL + "}";

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
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_3);
    return stringBuffer.toString();
  }
}