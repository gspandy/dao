/**
 * ICustomQueryService.java

 */
package com.porpoise.gen.query.service;

import java.util.Collection;

import com.porpoise.gen.query.ICustomQuery;

/**
 * ICustomQueryService
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * Service for handling custom query objects
 */
public interface ICustomQueryService
{
    /**
     * list all custom query objects
     * 
     * @return a collection of all custom query objects
     */
    public Collection<ICustomQuery> list();

    /**
     * get the custom query object with the given name
     * 
     * @param queryId
     *            the custom query id
     * @return the custom query by the given name
     */
    public ICustomQuery get(final Long queryId);

    /**
     * save the custom query
     * 
     * @param customQuery
     *            the custom query to save
     */
    public void save(ICustomQuery customQuery);

    /**
     * delete the custom query
     * 
     * @param customQuery
     *            the custom query to delete
     */
    public void delete(ICustomQuery customQuery);
}
