/**
 * MutableStateMachineFunction.java

 */
package com.porpoise.gen.database.finitestatemachine.statemachine;

import com.porpoise.gen.finitestatemachine.statemachine.StateMachineBuilder;
import com.porpoise.gen.finitestatemachine.statemachine.StateMachine;
import com.porpoise.gen.util.collections.Function;

/**
 * MutableStateMachineFunction
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
enum MutableStateMachineFunction implements Function<StateMachineBuilder, IStateMachine>
{
    INSTANCE; // singleton

    /**
     * return a shared instance
     * 
     * @return a shared MutableStateMachineFunction instance
     */
    public static MutableStateMachineFunction getSharedInstance()
    {
        return INSTANCE;
    }

    /**
     * @return builder a mutable StateMachine instance
     */
    public StateMachine apply(final StateMachineBuilder builder)
    {
        return builder.build();
    }
}
