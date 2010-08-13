package com.porpoise.dao.generator.templates;

import com.porpoise.generator.*;
import java.util.*;
import com.porpoise.dao.generator.gen.*;
import com.porpoise.dao.generator.model.*;

public class FindKeyTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized FindKeyTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    FindKeyTemplate result = new FindKeyTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ".assembler;" + NL + "" + NL + "import static com.google.common.base.Preconditions.*;" + NL + "import com.porpoise.dao.database.IDbTransaction;" + NL + "" + NL + "/**" + NL + " * key class which may also be used to construct an object from the database" + NL + " */" + NL + "class FindKey<T> {" + NL + "\tprivate T key;" + NL + "\tprivate IDbTransaction tx;" + NL + "\t" + NL + "\tFindKey(final T k, final IDbTransaction t) {" + NL + "\t\tkey = checkNotNull(k);" + NL + "\t\ttx = checkNotNull(t);" + NL + "\t}" + NL + "\t" + NL + "\t@Override" + NL + "\tpublic int hashCode() {" + NL + "\t\tfinal int prime = 31;" + NL + "\t\tint result = 1;" + NL + "\t\tresult = prime * result + (key == null ? 0 : key.hashCode());" + NL + "\t\treturn result;" + NL + "\t}" + NL + "" + NL + "\t@Override" + NL + "\tpublic boolean equals(final Object obj) {" + NL + "\t\tif (this == obj) {" + NL + "\t\t\treturn true;" + NL + "\t\t}" + NL + "\t\tif (obj == null) {" + NL + "\t\t\treturn false;" + NL + "\t\t}" + NL + "\t\tif (getClass() != obj.getClass()) {" + NL + "\t\t\treturn false;" + NL + "\t\t}" + NL + "\t\tfinal FindKey other = (FindKey) obj;" + NL + "\t\tif (key == null) {" + NL + "\t\t\tif (other.key != null) {" + NL + "\t\t\t\treturn false;" + NL + "\t\t\t}" + NL + "\t\t} else if (!key.equals(other.key)) {" + NL + "\t\t\treturn false;" + NL + "\t\t}" + NL + "\t\treturn true;" + NL + "\t}" + NL + "" + NL + "    /**" + NL + "     * @return the object primary key" + NL + "     */" + NL + "\tpublic T getKey() {" + NL + "\t\treturn key;" + NL + "\t}" + NL + "" + NL + "    /**" + NL + "     * @return the database transaction" + NL + "     */" + NL + "\tpublic IDbTransaction getTx() {" + NL + "\t\treturn tx;" + NL + "\t}" + NL + "" + NL + "}";

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
    return stringBuffer.toString();
  }
}