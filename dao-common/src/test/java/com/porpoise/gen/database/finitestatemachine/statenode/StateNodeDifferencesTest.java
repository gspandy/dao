/**
 * StateNodeDifferencesTest.java

 */
package com.porpoise.gen.database.finitestatemachine.statenode;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.porpoise.gen.finitestatemachine.statenode.StateNodeFactory;
import com.porpoise.gen.finitestatemachine.statenode.IStateNodeDetails;
import com.porpoise.gen.finitestatemachine.statenode.StateNodeBuilder;
import com.porpoise.gen.database.dao.finitestatemachine.statenode.StateNodeDao;
import com.porpoise.gen.finitestatemachine.statetransition.IStateTransitionDetails;
import com.porpoise.gen.finitestatemachine.statetransition.StateTransitionBuilder;
import com.porpoise.gen.database.dao.finitestatemachine.statetransition.StateTransitionDao;
import com.porpoise.gen.finitestatemachine.statetransition.StateTransitionFactory;
import com.porpoise.gen.cache.ObjectCache;
import com.porpoise.gen.database.AbstractTransactionalTest;
import com.porpoise.gen.database.assembler.AssemblerSuppliers;
import com.porpoise.gen.metadata.Difference;
import com.porpoise.gen.metadata.DifferenceType;
import com.porpoise.gen.metadata.Differences;
import com.porpoise.gen.metadata.IAssemblerSupplier;
import com.porpoise.gen.metadata.MetaData;
import com.porpoise.gen.test.Assert;
import com.porpoise.gen.util.Objects;

/**
 * StateNodeDifferencesTest
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class StateNodeDifferencesTest extends AbstractTransactionalTest
{
    private StateNode originalStateNode; 
    private StateNode readStateNode;
    
    /**
     * prepare the test
     */ 
    @Before
    public final void setup()
    {
        //
        // create a new stateNode
        //
        this.originalStateNode = StateNodeFactory.newStateNode();
        final Long stateNodeId = StateNodeDao.insertStateNode(
            getTransaction(), 
            originalStateNode , null /* stateMachineNodesId */          );
        this.originalStateNode.setId(stateNodeId);
        this.readStateNode = StateNodeDao.getStateNodeBuilder(getTransaction(), stateNodeId).build();
    }

    /**
     * Test the difference can be found between two state node objects with different name fields 
     */
    @Test
    public void test_stateNodeNameDifferences()
    {
        //
        // update the name
        //
        final String newName;
                newName = "a new name name";
                this.readStateNode.setName(newName);

        final ObjectCache cache = new ObjectCache();
        final IAssemblerSupplier supplier = AssemblerSuppliers.newSupplier(getTransaction(), cache);

        //
        // diff the original stateNode and the updated one. we should now have
        // two differences:
        // the name being added to the stateNode and the updated name
        //
        final Differences stateNodeDifferences = MetaData.stateNode().diff(supplier, this.originalStateNode, this.readStateNode);
        Assert.assertEquals(1, stateNodeDifferences.size());

        System.out.println(stateNodeDifferences);

        final Collection<Difference> updatedDifferences = stateNodeDifferences
                .getDifferencesOfType(DifferenceType.UPDATED);
                
        final Difference difference = updatedDifferences.iterator().next();
        //
        // assert the name has changed
        //
        Assert.assertEquals("stateNode.name", difference.getTargetPath().toString());
        Assert.assertEquals(newName, difference.getTargetValue());
    }
 
  

    /**
     * test a StateNode object with some new StateTransition objects added to its Transitions collection
     * produces NEW differences
     */
    @Test
    public void test_newTransitionsDifferences()
    {
        final StateNode stateNode = this.readStateNode;
        final StateTransition firstStateTransition = StateTransitionFactory.newStateTransition();
        final StateTransition secondStateTransition = StateTransitionFactory.alterStateTransition(StateTransitionFactory.newStateTransition());

        final Collection<Difference> differences = updateTransitionsOnStateNode(stateNode, firstStateTransition, secondStateTransition);
        final Iterator<Difference> iter = differences.iterator();

        //
        // first StateTransition
        //
        assertTransitionsDifference(iter.next(), firstStateTransition, DifferenceType.NEW);

        //
        // second StateTransition
        //
        assertTransitionsDifference(iter.next(), secondStateTransition, DifferenceType.NEW);
    }

    /**
     * <p>
     * test a StateNode object with it's Transitions collection cleared produces a DELETED
     * difference.
     * </p>
     * <p>
     * For an StateNode to be deleted from the Transitions collection, it has to have an ID
     * (otherwise it wouldn't have been persisted).
     * </p>
     */
    @Test
    public void test_deletedTransitionsDifferences()
    {
        final StateNode stateNode = this.readStateNode;

        // we need a StateTransition with an ID so it can be picked up as deleted
        final StateTransition stateTransition = StateTransitionFactory.newStateTransition(true);
        stateNode.addTransitions(stateTransition);

        final StateNode updatedStateNode = StateNodeBuilder.copy(stateNode);
        updatedStateNode.getTransitions().clear();

        final IAssemblerSupplier supplier = AssemblerSuppliers.newSupplier(getTransaction(), new ObjectCache());
        final Differences diff = MetaData.stateNode().diff(supplier, stateNode, updatedStateNode);

        Assert.assertEquals(1, diff.size());

        //
        // assert our Transitions has been deleted
        //
        assertTransitionsDifference(diff.iterator().next(), null, DifferenceType.DELETED);
    }

    private void assertTransitionsDifference(final Difference difference, final Object targetValue, final DifferenceType type)
    {
        Assert.assertEquals(type, difference.getType());
        Assert.assertEquals("stateNode.transitions", difference.getSourcePath().toString());
        Assert.assertEquals("stateNode.transitions", difference.getTargetPath().toString());
        Assert.assertSame(targetValue, difference.getTargetValue());
    }

    private Collection<Difference> updateTransitionsOnStateNode(final StateNode stateNode, final StateTransition ... stateTransitionObjectsToAdd)
    {
        for (final StateTransition stateTransition : stateTransitionObjectsToAdd)
        {
            stateNode.addTransitions(stateTransition);
        }

        final ObjectCache cache = new ObjectCache();
        final IAssemblerSupplier supplier = AssemblerSuppliers.newSupplier(getTransaction(), cache);

        final Differences diff = MetaData.stateNode().diff(supplier, this.originalStateNode, stateNode);

        final Collection<Difference> differences = diff.getDifferences();

        final int expectedDifferencesSize = stateTransitionObjectsToAdd.length;
        final String msg = String.format("%d differences expected", Integer.valueOf(expectedDifferencesSize));
        Assert.assertEquals(msg, expectedDifferencesSize, diff.size());

        return differences;
    }
}
