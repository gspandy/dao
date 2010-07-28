/**
 * AssemblerSuppliers.java

 */
package com.porpoise.gen.database.assembler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


import com.porpoise.gen.finitestatemachine.statemachine.StateMachine;
import com.porpoise.gen.finitestatemachine.statemachine.IStateMachineDetails;
import com.porpoise.gen.database.assembler.finitestatemachine.StateMachineAssembler;
import com.porpoise.gen.finitestatemachine.statenode.StateNode;
import com.porpoise.gen.finitestatemachine.statenode.IStateNodeDetails;
import com.porpoise.gen.database.assembler.finitestatemachine.StateNodeAssembler;
import com.porpoise.gen.finitestatemachine.statetransition.StateTransition;
import com.porpoise.gen.finitestatemachine.statetransition.IStateTransitionDetails;
import com.porpoise.gen.database.assembler.finitestatemachine.StateTransitionAssembler;
  import com.porpoise.gen.util.Validation;

import com.porpoise.gen.cache.ObjectCache;
import com.porpoise.gen.database.IDbTransaction;
import com.porpoise.gen.metadata.IAssembler;
import com.porpoise.gen.metadata.IAssemblerSupplier;

/**
 * AssemblerSuppliers
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public enum AssemblerSuppliers
{
    ; //uninstantiable
    
    private static interface IAssemblerFactory
    {
        <T> IAssembler<Long, T> newAssembler(final IDbTransaction transaction, final ObjectCache cache);
    }

    final static Map<Class<?>, IAssemblerFactory> ASSEMBLY_BY_CLASS;

    static
    {
        final Map<Class<?>, IAssemblerFactory> temp = new HashMap<Class<?>, IAssemblerFactory>();

        //
        // State Machine
        //
        temp.put(IStateMachine.class, new IAssemblerFactory()
        {
            @SuppressWarnings("unchecked")
            @Override
            public IAssembler<Long, ?> newAssembler(final IDbTransaction transaction, final ObjectCache cache)
            {
                return StateMachineAssembler.getAssembler(transaction, cache);
            }
        });

        //
        // State MachineDto
        //
        temp.put(StateMachine.class, new IAssemblerFactory()
        {
            @SuppressWarnings("unchecked")
            @Override
            public IAssembler<Long, ?> newAssembler(final IDbTransaction transaction, final ObjectCache cache)
            {
                return StateMachineAssembler.getAssembler(transaction, cache);
            }
        });


        //
        // State Node
        //
        temp.put(IStateNode.class, new IAssemblerFactory()
        {
            @SuppressWarnings("unchecked")
            @Override
            public IAssembler<Long, ?> newAssembler(final IDbTransaction transaction, final ObjectCache cache)
            {
                return StateNodeAssembler.getAssembler(transaction, cache);
            }
        });

        //
        // State NodeDto
        //
        temp.put(StateNode.class, new IAssemblerFactory()
        {
            @SuppressWarnings("unchecked")
            @Override
            public IAssembler<Long, ?> newAssembler(final IDbTransaction transaction, final ObjectCache cache)
            {
                return StateNodeAssembler.getAssembler(transaction, cache);
            }
        });


        //
        // State Transition
        //
        temp.put(IStateTransition.class, new IAssemblerFactory()
        {
            @SuppressWarnings("unchecked")
            @Override
            public IAssembler<Long, ?> newAssembler(final IDbTransaction transaction, final ObjectCache cache)
            {
                return StateTransitionAssembler.getAssembler(transaction, cache);
            }
        });

        //
        // State TransitionDto
        //
        temp.put(StateTransition.class, new IAssemblerFactory()
        {
            @SuppressWarnings("unchecked")
            @Override
            public IAssembler<Long, ?> newAssembler(final IDbTransaction transaction, final ObjectCache cache)
            {
                return StateTransitionAssembler.getAssembler(transaction, cache);
            }
        });

          ASSEMBLY_BY_CLASS = Collections.unmodifiableMap(temp);
    }

    /**
     * return an {@link IAssemblerSupplier}, which will be able to produce factory
     * interfaces for objects of a given class using the transaction/cache provided 
     *
     * @param transaction The transaction to be used when loading objects
     * @param cache a cache object which may be used to locate objects
     * @return an assembly supplier for the given transaction and object cache
     */
    public static IAssemblerSupplier newSupplier(final IDbTransaction transaction,
            final ObjectCache cache)
    {
        return new IAssemblerSupplier()
        {
            @Override
            public <T> IAssembler<Long, T> getAssembler(final Class<? extends T> c1ass)
            {
                final IAssemblerFactory assemblerFactory = ASSEMBLY_BY_CLASS.get(c1ass);
                if (assemblerFactory == null)
                {
                    throw new NullPointerException("No assembler found for " + c1ass);
                }
                final IAssembler<Long, T> newAssembler = assemblerFactory.newAssembler(transaction, cache);
                return newAssembler;
            }
        };
    }
}
