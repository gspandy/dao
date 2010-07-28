/**
 * Visitors.java

 */
package com.porpoise.gen.database.visitors;

import com.porpoise.gen.database.IResultSetVisitor;

/**
 * Visitors
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * Visitors contains methods to access commonly used {@link IResultSetVisitor}s
 */
public enum Visitors
{
    // uninstantiable
    ;


    /**
     * @param v1
     *            the first visitor
     * @param v2
     *            the second visitor
     * @return a composite visitor
     */
    public static IResultSetVisitor compositeVisitor(final IResultSetVisitor v1, final IResultSetVisitor v2)
    {
        return new CompositeResultSetVisitor(v1, v2);
    }

    /**
     * return a result set visitor which asserts at most a given number of
     * results were returned
     * 
     * @param expectedCount
     *            The number of expected results
     * 
     * @return a counting result set visitor
     */
    public static CountingResultSetVisitor maxResultsVisitor(final int expectedCount)
    {
        return new CountingResultSetVisitor(expectedCount);
    }

    /**
     * @return a {@link SingleResultToMapVisitor}
     */
    public static SingleResultToMapVisitor singleResultVisitor()
    {
        return new SingleResultToMapVisitor();
    }

    /**
     * @return a {@link FirstResultToMapVisitor}
     */
    public static FirstResultToMapVisitor firstResultVisitor()
    {
        return new FirstResultToMapVisitor();
    }

    /**
     * @return a {@link MultipleResultToMapVisitor}
     */
    public static MultipleResultToMapVisitor multiResultVisitor()
    {
        return new MultipleResultToMapVisitor();
    }

    /**
     * return a SingleScalarResultVisitor used for queries which return a single
     * row which contains a single column
     * 
     * @return a SingleScalarResultVisitor
     */
    public static SingleScalarResultVisitor scalarVisitor()
    {
        return new SingleScalarResultVisitor();
    }
}
