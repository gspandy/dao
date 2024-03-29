package com.porpoise.api.generator.templates;

import java.util.*;
import com.porpoise.generator.*;
import com.porpoise.dao.generator.model.api.*;
import com.porpoise.dao.generator.gen.*;
import com.porpoise.api.generator.gen.*;

public class AbstractAccessorServiceTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized AbstractAccessorServiceTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    AbstractAccessorServiceTemplate result = new AbstractAccessorServiceTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ".service;" + NL + "" + NL + "import java.util.Collection;" + NL + "" + NL + "/**" + NL + " */" + NL + "public interface IAccessorService<K, T>" + NL + "{" + NL + "    /**" + NL + "     * @return an iterable of all IDs for the given type" + NL + "     */" + NL + "    public Iterable<K> listAllKeys();" + NL + "    " + NL + "    /**" + NL + "     * @return an object for the given key" + NL + "     */" + NL + "    public T get(final K key);" + NL + "    " + NL + "    /**" + NL + "     * @return an object for the given key" + NL + "     */" + NL + "    public Collection<T> getAll(final Collection<K> keys);" + NL + "}";
  protected final String TEXT_3 = NL;

   /* (non-javadoc)
    * @see IGenerator#generate(Object)
    */
   public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     final ApiContext ctxt = (ApiContext) argument; 

    stringBuffer.append(TEXT_1);
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_2);
    stringBuffer.append(TEXT_3);
    return stringBuffer.toString();
  }
}