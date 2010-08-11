package com.porpoise.dao.generator.templates;

import com.porpoise.generator.*;
import java.util.*;
import com.porpoise.dao.generator.gen.*;
import com.porpoise.dao.generator.model.*;

public class AbstractDaoServiceTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized AbstractDaoServiceTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    AbstractDaoServiceTemplate result = new AbstractDaoServiceTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ".impl;" + NL + "" + NL + "import java.util.*;" + NL + "import com.porpoise.dao.database.DbConnectionFactory;" + NL + "import com.google.common.base.Preconditions;" + NL + "" + NL + "/**" + NL + " * Functional Data Access class used to operate on ";
  protected final String TEXT_3 = "Dto objects" + NL + " */" + NL + "abstract class AbstractDaoService" + NL + "{" + NL + "    private final DbConnectionFactory connectionFactory;" + NL + "" + NL + "    /**" + NL + "     *" + NL + "     */" + NL + "    public AbstractDaoService(final DbConnectionFactory factory)" + NL + "    {" + NL + "        this.connectionFactory = Preconditions.checkNotNull(factory);" + NL + "    }" + NL + "    " + NL + "    /**" + NL + "     * @return the DbConnectionFactory " + NL + "     */" + NL + "    protected DbConnectionFactory getFactory()" + NL + "    {" + NL + "        return this.connectionFactory;" + NL + "    }" + NL + "}";
  protected final String TEXT_4 = NL;

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
    stringBuffer.append( n );
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    return stringBuffer.toString();
  }
}