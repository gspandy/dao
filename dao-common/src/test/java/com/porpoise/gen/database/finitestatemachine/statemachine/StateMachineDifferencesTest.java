/**
 * StateMachineDifferencesTest.java

 */
package com.porpoise.gen.database.finitestatemachine.statemachine;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.porpoise.gen.finitestatemachine.statemachine.StateMachineFactory;
import com.porpoise.gen.finitestatemachine.statemachine.IStateMachineDetails;
import com.porpoise.gen.finitestatemachine.statemachine.StateMachineBuilder;
import com.porpoise.gen.database.dao.finitestatemachine.statemachine.StateMachineDao;
import com.porpoise.gen.finitestatemachine.statenode.IStateNodeDetails;
import com.porpoise.gen.finitestatemachine.statenode.StateNodeBuilder;
import com.porpoise.gen.database.dao.finitestatemachine.statenode.StateNodeDao;
import com.porpoise.gen.finitestatemachine.statenode.StateNodeFactory;
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
 * StateMachineDifferencesTest
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class StateMachineDifferencesTest extends AbstractTransactionalTest
{
    private StateMachine originalStateMachine; 
    private StateMachine readStateMachine;
    
    /**
     * prepare the test
     */ 
    @Before
    public final void setup()
    {
        //
        // create a new stateMachine
        //
        this.originalStateMachine = StateMachineFactory.newStateMachine();
        final Long stateMachineId = StateMachineDao.insertStateMachine(
            getTransaction(), 
            originalStateMachine         );
        this.originalStateMachine.setId(stateMachineId);
        this.readStateMachine = StateMachineDao.getStateMachineBuilder(getTransaction(), stateMachineId).build();
    }

    /**
     * Test the difference can be found between two state machine objects with different name fields 
     */
    @Test
    public void test_stateMachineNameDifferences()
    {
        //
        // update the name
        //
        final String newName;
                newName = "a new name name";
                this.readStateMachine.setName(newName);

        final ObjectCache cache = new ObjectCache();
        final IAssemblerSupplier supplier = AssemblerSuppliers.newSupplier(getTransaction(), cache);

        //
        // diff the original stateMachine and the updated one. we should now have
        // two differences:
        // the name being added to the stateMachine and the updated name
        //
        final Differences stateMachineDifferences = MetaData.stateMachine().diff(supplier, this.originalStateMachine, this.readStateMachine);
        Assert.assertEquals(1, stateMachineDifferences.size());

        System.out.println(stateMachineDifferences);

        final Collection<Difference> updatedDifferences = stateMachineDifferences
                .getDifferencesOfType(DifferenceType.UPDATED);
                
        final Difference difference = updatedDifferences.iterator().next();
        //
        // assert the name has changed
        //
        Assert.assertEquals("stateMachine.name", difference.getTargetPath().toString());
        Assert.assertEquals(newName, difference.getTargetValue());
    }
 
  

    /**
     * test a StateMachine object with some new StateNode objects added to its Nodes collection
     * produces NEW differences
     */
    @Test
    public void test_newNodesDifferences()
    {
        final StateMachine stateMachine = this.readStateMachine;
        final StateNode firstStateNode = StateNodeFactory.newStateNode();
        final StateNode secondStateNode = StateNodeFactory.alterStateNode(StateNodeFactory.newStateNode());

        final Collection<Difference> differences = updateNodesOnStateMachine(stateMachine, firstStateNode, secondStateNode);
        final Iterator<Difference> iter = differences.iterator();

        //
        // first StateNode
        //
        assertNodesDifference(iter.next(), firstStateNode, DifferenceType.NEW);

        //
        // second StateNode
        //
        assertNodesDifference(iter.next(), secondStateNode, DifferenceType.NEW);
    }

    /**
     * <p>
     * test a StateMachine object with it's Nodes collection cleared produces a DELETED
     * difference.
     * </p>
     * <p>
     * For an StateMachine to be deleted from the Nodes collection, it has to have an ID
     * (otherwise it wouldn't have been persisted).
     * </p>
     */
    @Test
    public void test_deletedNodesDifferences()
    {
        final StateMachine stateMachine = this.readStateMachine;

        // we need a StateNode with an ID so it can be picked up as deleted
        final StateNode stateNode = StateNodeFactory.newStateNode(true);
        stateMachine.addNodes(stateNode);

        final StateMachine updatedStateMachine = StateMachineBuilder.copy(stateMachine);
        updatedStateMachine.getNodes().clear();

        final IAssemblerSupplier supplier = AssemblerSuppliers.newSupplier(getTransaction(), new ObjectCache());
        final Differences diff = MetaData.stateMachine().diff(supplier, stateMachine, updatedStateMachine);

        Assert.assertEquals(1, diff.size());

        //
        // assert our Nodes has been deleted
        //
        assertNodesDifference(diff.iterator().next(), null, DifferenceType.DELETED);
    }

    private void assertNodesDifference(final Difference difference, final Object targetValue, final DifferenceType type)
    {
        Assert.assertEquals(type, difference.getType());
        Assert.assertEquals("stateMachine.nodes", difference.getSourcePath().toString());
        Assert.assertEquals("stateMachine.nodes", difference.getTargetPath().toString());
        Assert.assertSame(targetValue, difference.getTargetValue());
    }

    private Collection<Difference> updateNodesOnStateMachine(final StateMachine stateMachine, final StateNode ... stateNodeObjectsToAdd)
    {
        for (final StateNode stateNode : stateNodeObjectsToAdd)
        {
            stateMachine.addNodes(stateNode);
        }

        final ObjectCache cache = new ObjectCache();
        final IAssemblerSupplier supplier = AssemblerSuppliers.newSupplier(getTransaction(), cache);

        final Differences diff = MetaData.stateMachine().diff(supplier, this.originalStateMachine, stateMachine);

        final Collection<Difference> differences = diff.getDifferences();

        final int expectedDifferencesSize = stateNodeObjectsToAdd.length;
        final String msg = String.format("%d differences expected", Integer.valueOf(expectedDifferencesSize));
        Assert.assertEquals(msg, expectedDifferencesSize, diff.size());

        return differences;
    }
}
