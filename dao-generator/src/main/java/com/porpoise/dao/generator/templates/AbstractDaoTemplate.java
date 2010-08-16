package com.porpoise.dao.generator.templates;

import com.porpoise.generator.*;
import java.util.*;
import com.porpoise.dao.generator.gen.*;
import com.porpoise.dao.generator.model.*;

public class AbstractDaoTemplate implements IGenerator
{
  protected static String nl;
  public static synchronized AbstractDaoTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    AbstractDaoTemplate result = new AbstractDaoTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";" + NL + "" + NL + "import java.util.Collection;" + NL + "import java.util.List;" + NL + "" + NL + "import com.porpoise.dao.database.IDbTransaction;" + NL + "import com.porpoise.dao.database.dao.AbstractDao;" + NL + "import com.porpoise.dao.database.visitors.MultipleScalarResultVisitor;" + NL + "import com.porpoise.dao.database.visitors.SingleScalarResultVisitor;" + NL + "" + NL + "/**" + NL + " */" + NL + "public abstract class AbstractSingleIdDao<K, T> extends AbstractDao" + NL + "{" + NL + "    /**" + NL + "     * @param transaction the transaction used to retrieve the object" + NL + "     * @param id the Id for the ";
  protected final String TEXT_3 = " object" + NL + "     * @return the ";
  protected final String TEXT_4 = " object with the given ID" + NL + "     */" + NL + "    public abstract T findById(final IDbTransaction transaction, final K id);" + NL + "" + NL + "    /** " + NL + "     * @return the select count" + NL + "     */" + NL + "    protected abstract String getSelectCountQuery();" + NL + "" + NL + "    /** " + NL + "     * @return the select Id query" + NL + "     */" + NL + "    protected abstract String getSelectIdQuery();" + NL + "" + NL + "    /**" + NL + "     * @return the next available ID" + NL + "     */" + NL + "\tpublic abstract K nextId(final IDbTransaction transaction);" + NL + "" + NL + "    /**" + NL + "     * @return the number of rows in the database" + NL + "     */" + NL + "    public int count(final IDbTransaction transaction)" + NL + "    {" + NL + "\t    final Integer count = transaction.executeQuery(new SingleScalarResultVisitor()," + NL + "\t\t\t\tgetSelectCountQuery()).getInteger();" + NL + "\t\treturn count.intValue();" + NL + "    }" + NL + "    " + NL + "    " + NL + "    /**" + NL + "     * @return an iterable of all IDs for the given type" + NL + "     */" + NL + "    public Iterable<K> listAllKeys(final IDbTransaction transaction)" + NL + "    {" + NL + "\t    final List<K> keys = transaction.executeQuery(" + NL + "\t       new MultipleScalarResultVisitor<K>(), getSelectIdQuery()).getResults();" + NL + "\t\treturn keys;" + NL + "    }" + NL + "" + NL + "    /**" + NL + "     * @param transaction the transaction used to retrieve the object" + NL + "     * @return all ";
  protected final String TEXT_5 = " objects" + NL + "     */" + NL + "    public abstract Collection<T> listAll(final IDbTransaction transaction);" + NL + "" + NL + "    /** " + NL + "     * create a new entry for the given dto" + NL + "     * @param transaction" + NL + "     * @param dto the dto to insert" + NL + "     */" + NL + "    public abstract void insert(final IDbTransaction transaction, final T dto);" + NL + "" + NL + "    /** " + NL + "     * @param transaction" + NL + "     * @param id the ID of the element to update" + NL + "     * @param dto" + NL + "     */" + NL + "    public abstract void update(final IDbTransaction transaction, K id, final T dto);" + NL + "}";
  protected final String TEXT_6 = NL;

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
    stringBuffer.append( n );
    stringBuffer.append(TEXT_4);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(TEXT_6);
    return stringBuffer.toString();
  }
}