/**
 * StateTransitionAssemblerTest.java

 */
package com.porpoise.gen.database.assembler.finitestatemachine;

import com.porpoise.gen.finitestatemachine.statetransition.StateTransitionFactory;
import com.porpoise.gen.finitestatemachine.statetransition.IStateTransitionDetails;
import com.porpoise.gen.cache.ObjectCache;
import com.porpoise.gen.database.IDbTransaction;
import com.porpoise.gen.test.Assert;
import org.easymock.EasyMock;
import org.junit.Test;

/**
 * StateTransitionAssemblerTest
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class StateTransitionAssemblerTest
{
    /**
     * test an object can be assembled from the cache
     */
    @Test
    public void test_assembleFromCache()
    {
        final IDbTransaction transaction = EasyMock.createMock(IDbTransaction.class);
        //
        // nothing should get loaded from the database
        //
        EasyMock.replay(transaction);
        final ObjectCache objectCache = new ObjectCache();

        final StateTransition newStateTransition = StateTransitionFactory.newStateTransition(true);
        StateTransitionFactory.registerInCache(objectCache, newStateTransition);

        final StateTransition stateTransitionAssembled = StateTransitionAssembler.assemble(transaction, objectCache, newStateTransition.getId());

        // ensure nothing accessed the transaction
        EasyMock.verify(transaction);
        
        Assert.assertSame(newStateTransition, stateTransitionAssembled);
    }
    
    /**
     * test a State Transition can be assembled from the database
     */
    @Test
    public void test_assemblyFromDatabase()
    {
       // TODO - assembly a State Transition from the database
    }
}
