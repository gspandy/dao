/**
 * MutableStateNodeFunction.java

 */
package com.porpoise.gen.database.finitestatemachine.statenode;

import com.porpoise.gen.finitestatemachine.statenode.StateNodeBuilder;
import com.porpoise.gen.finitestatemachine.statenode.StateNode;
import com.porpoise.gen.util.collections.Function;

/**
 * MutableStateNodeFunction
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
enum MutableStateNodeFunction implements Function<StateNodeBuilder, IStateNode>
{
    INSTANCE; // singleton

    /**
     * return a shared instance
     * 
     * @return a shared MutableStateNodeFunction instance
     */
    public static MutableStateNodeFunction getSharedInstance()
    {
        return INSTANCE;
    }

    /**
     * @return builder a mutable StateNode instance
     */
    public StateNode apply(final StateNodeBuilder builder)
    {
        return builder.build();
    }
}
