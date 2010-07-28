/**
 * StateMachineAssemblerTest.java

 */
package com.porpoise.gen.database.assembler.finitestatemachine;

import com.porpoise.gen.finitestatemachine.statemachine.StateMachineFactory;
import com.porpoise.gen.finitestatemachine.statemachine.IStateMachineDetails;
import com.porpoise.gen.cache.ObjectCache;
import com.porpoise.gen.database.IDbTransaction;
import com.porpoise.gen.test.Assert;
import org.easymock.EasyMock;
import org.junit.Test;

/**
 * StateMachineAssemblerTest
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class StateMachineAssemblerTest
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

        final StateMachine newStateMachine = StateMachineFactory.newStateMachine(true);
        StateMachineFactory.registerInCache(objectCache, newStateMachine);

        final StateMachine stateMachineAssembled = StateMachineAssembler.assemble(transaction, objectCache, newStateMachine.getId());

        // ensure nothing accessed the transaction
        EasyMock.verify(transaction);
        
        Assert.assertSame(newStateMachine, stateMachineAssembled);
    }
    
    /**
     * test a State Machine can be assembled from the database
     */
    @Test
    public void test_assemblyFromDatabase()
    {
       // TODO - assembly a State Machine from the database
    }
}
