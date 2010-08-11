package com.porpoise.api.generator.templates;

import java.util.*;
import com.porpoise.generator.*;
import com.porpoise.api.generator.model.*;
import com.porpoise.api.generator.gen.*;

public class AccessorServiceTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized AccessorServiceTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    AccessorServiceTemplate result = new AccessorServiceTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ".api;" + NL + "" + NL + "import java.util.Collection;" + NL + "" + NL + "/**" + NL + " */" + NL + "public final interface I";
  protected final String TEXT_3 = "AccessorService extends IBaseAccessorService<Integer, Object>" + NL + "{" + NL + "    /**" + NL + "     * @return an object for the given key" + NL + "     */" + NL + "    public Object get(Integer key);" + NL + "    " + NL + "    /**" + NL + "     * @return an object for the given key" + NL + "     */" + NL + "    public Collection<Object> getAll(Collection<Integer> keys);" + NL + "}";
  protected final String TEXT_4 = NL;

   /* (non-javadoc)
    * @see IGenerator#generate(Object)
    */
   public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     final ApiContext ctxt = (ApiContext) argument;
final String n = ctxt.getJavaName(); 

    stringBuffer.append(TEXT_1);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_2);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    return stringBuffer.toString();
  }
}