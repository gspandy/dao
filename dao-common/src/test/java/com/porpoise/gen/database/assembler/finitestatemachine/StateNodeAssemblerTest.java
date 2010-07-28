/**
 * StateNodeAssemblerTest.java

 */
package com.porpoise.gen.database.assembler.finitestatemachine;

import com.porpoise.gen.finitestatemachine.statenode.StateNodeFactory;
import com.porpoise.gen.finitestatemachine.statenode.IStateNodeDetails;
import com.porpoise.gen.cache.ObjectCache;
import com.porpoise.gen.database.IDbTransaction;
import com.porpoise.gen.test.Assert;
import org.easymock.EasyMock;
import org.junit.Test;

/**
 * StateNodeAssemblerTest
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class StateNodeAssemblerTest
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

        final StateNode newStateNode = StateNodeFactory.newStateNode(true);
        StateNodeFactory.registerInCache(objectCache, newStateNode);

        final StateNode stateNodeAssembled = StateNodeAssembler.assemble(transaction, objectCache, newStateNode.getId());

        // ensure nothing accessed the transaction
        EasyMock.verify(transaction);
        
        Assert.assertSame(newStateNode, stateNodeAssembled);
    }
    
    /**
     * test a State Node can be assembled from the database
     */
    @Test
    public void test_assemblyFromDatabase()
    {
       // TODO - assembly a State Node from the database
    }
}
