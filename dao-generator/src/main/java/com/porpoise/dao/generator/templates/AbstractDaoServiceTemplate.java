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
  protected final String TEXT_2 = ".impl;" + NL + "" + NL + "import static com.google.common.base.Preconditions.checkNotNull;" + NL + "" + NL + "import java.util.Collection;" + NL + "" + NL + "import com.google.common.collect.Lists;" + NL + "import ";
  protected final String TEXT_3 = ".AbstractSingleIdDao;" + NL + "import ";
  protected final String TEXT_4 = ".assembler.DomainObjects;" + NL + "import com.porpoise.dao.database.DbConnectionFactory;" + NL + "import com.porpoise.dao.database.IDbTransaction;" + NL + "" + NL + "/**" + NL + " * Functional Data Access class used to operate on ";
  protected final String TEXT_5 = "Dto objects" + NL + " * @param DAO the dao type" + NL + " */" + NL + "abstract class AbstractDaoService<K, T, DAO extends AbstractSingleIdDao<K, ?>>" + NL + "{" + NL + "    private final DbConnectionFactory connectionFactory;" + NL + "    private final DAO dao;" + NL + "" + NL + "\tprivate static final DomainObjects objects = new DomainObjects();" + NL + "" + NL + "\tprotected DomainObjects getDomainObjects() {" + NL + "\t\t// use the global cache" + NL + "\t\treturn objects;" + NL + "\t}" + NL + "" + NL + "    /**" + NL + "     *" + NL + "     */" + NL + "    public AbstractDaoService(final DbConnectionFactory factory, final DAO daoInstance)" + NL + "    {" + NL + "        this.connectionFactory = checkNotNull(factory);" + NL + "        this.dao = checkNotNull(daoInstance);" + NL + "    }" + NL + "    " + NL + "    /**" + NL + "     * @return the DAO object" + NL + "     */" + NL + "    protected DAO getDao()" + NL + "    {" + NL + "        return this.dao;" + NL + "    }" + NL + "    " + NL + "    /**" + NL + "     * @return the DbConnectionFactory " + NL + "     */" + NL + "    protected DbConnectionFactory getFactory()" + NL + "    {" + NL + "        return this.connectionFactory;" + NL + "    }" + NL + "" + NL + "" + NL + "    /**" + NL + "     * @return an iterable of all IDs for the given type" + NL + "     */" + NL + "    public Iterable<K> listAllKeys()" + NL + "    {" + NL + "\t\tfinal IDbTransaction tx = getFactory().startNewTransaction();" + NL + "\t\tIterable<K> result;" + NL + "\t\ttry {" + NL + "\t\t\tresult = getDao().listAllKeys(tx);" + NL + "\t\t} finally {" + NL + "\t\t\ttx.close();" + NL + "\t\t}" + NL + "\t\treturn result;" + NL + "    }" + NL + "    " + NL + "    protected abstract T getInternal(final K id, final IDbTransaction tx);" + NL + "    " + NL + "    /**" + NL + "     * @return an object for the given key" + NL + "     */" + NL + "    public T get(final K id)" + NL + "    {" + NL + "        final IDbTransaction tx = getFactory().startNewTransaction();" + NL + "\t\tfinal T value;" + NL + "\t\ttry {" + NL + "\t\t\tvalue = getInternal(id, tx);" + NL + "\t\t} finally {" + NL + "\t\t\ttx.close();" + NL + "\t\t}" + NL + "\t\treturn value;" + NL + "\t}" + NL + "    " + NL + "    /**" + NL + "     * @return the objects for the given key" + NL + "     */" + NL + "    public Collection<T> getAll(final Collection<K> keys)" + NL + "    {" + NL + "\t\tfinal Collection<T> results = Lists" + NL + "\t\t\t\t.newArrayListWithExpectedSize(keys.size());" + NL + "\t\tfor (final K key : keys) {" + NL + "\t\t\tfinal T next = get(key);" + NL + "\t\t\tresults.add(next);" + NL + "\t\t}" + NL + "\t\treturn results;" + NL + "    }" + NL + "" + NL + "\t/**" + NL + "\t * @return the total number of ";
  protected final String TEXT_6 = " objects in the database" + NL + "\t */" + NL + "\tpublic int count() {" + NL + "\t\tfinal IDbTransaction tx = getFactory().startNewTransaction();" + NL + "\t\ttry {" + NL + "\t\t\treturn getDao().count(tx);" + NL + "\t\t} finally {" + NL + "\t\t\ttx.close();" + NL + "\t\t}" + NL + "\t}" + NL + "}";
  protected final String TEXT_7 = NL;

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
    stringBuffer.append( ctxt.getPackageName() );
    stringBuffer.append(TEXT_4);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_5);
    stringBuffer.append( n );
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    return stringBuffer.toString();
  }
}