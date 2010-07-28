/**
 * StateNodeDaoTest.java

 */
package com.porpoise.gen.database.dao.finitestatemachine.statenode;

import java.util.List;
import java.util.Collection;

import com.porpoise.gen.finitestatemachine.statenode.IStateNode;
import com.porpoise.gen.finitestatemachine.statenode.StateNodeBuilder;
import com.porpoise.gen.finitestatemachine.statenode.StateNodeFactory;
import com.porpoise.gen.finitestatemachine.statenode.IStateNodeDetails;
import com.porpoise.gen.finitestatemachine.statetransition.IStateTransitionDetails;
import com.porpoise.gen.database.AbstractTransactionalTest;
import com.porpoise.gen.database.visitors.Visitors;
import com.porpoise.gen.util.collections.Function;
import com.porpoise.gen.util.collections.Lists;
import com.porpoise.gen.test.Assert;
import org.junit.Test;


/**
 * StateNodeDaoTest
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class StateNodeDaoTest extends AbstractTransactionalTest
{
    /**
     * test the successful path for the StateNodeDao.insertStateNode method
     */
    @Test
    public void test_insertStateNode1()
    {

        //
        // create a state node to insert
        //
        final StateNode stateNode = newStateNode();
        final Long stateMachineNodesId = Long.valueOf(0);
 
        //
        // insert the new state node
        //
        final Long insertedStateNodeID = StateNodeDao.insertStateNode(
            getTransaction(),
        stateNode, 
        stateMachineNodesId
      );

        //
        // check the new state node ID
        //
        Assert.assertNotNull(insertedStateNodeID);
    }

    /**
     * test the successful path for the
     * {@link StateNodeDao\#insertStateNode(com.porpoise.gen.database.IDbTransaction, StateNode , Long)}
     * method
     */
    @Test
    public void test_insertStateNode()
    {
        final StateNode stateNode = newStateNode();
        insertStateNode(stateNode, null );
    }

    /**
     * test a stateNode can be read back using the
     * {@link StateNodeDao#getStateNode(com.porpoise.gen.database.IDbTransaction, Long)} method
     */
    @Test
    public void test_getStateNode()
    {
        //
        // create a stateNode to find
        //
        final StateNode newStateNode = newStateNode();
        final Long newStateNodeId = insertStateNode(newStateNode, null );
        newStateNode.setId(newStateNodeId);

        //
        // call the method under test
        //
        final StateNodeBuilder foundStateNode = StateNodeDao.getStateNodeBuilder(getTransaction(), newStateNodeId);

        assertShallowBuilder(foundStateNode, newStateNode);

        StateNode readStateNode = foundStateNode.build();
        Assert.assertNotSame(newStateNode, readStateNode);
        Assert.assertEquals("The read stateNode doesn't equal the saved stateNode", newStateNode, readStateNode);
    }

    /**
     * test a stateNode can be deleted via
     * {@link StateNodeDao#deleteStateNode(com.porpoise.gen.database.IDbTransaction, Long, boolean)}
     */
    @Test
    public void test_deleteStateNode()
    {
        //
        // create a new stateNode
        //
        final StateNode newStateNode = newStateNode();
        final Long stateNodeId = insertStateNode(newStateNode, null );
        newStateNode.setId(stateNodeId);

        //
        // check we can find it
        //
        final Long beforeCount = getTransaction().executeQuery(Visitors.scalarVisitor(),
                "SELECT COUNT(*) FROM abp_STATE_NODE WHERE ID=?", stateNodeId).getLong();
        Assert.assertEquals(1, beforeCount.longValue());

        //
        // call the method under test
        //
        final StateNodeBuilder deletedStateNode = StateNodeDao.deleteStateNode(getTransaction(), stateNodeId, false);

        //
        // assert the stateNode has been deleted
        //
        assertShallowBuilder(deletedStateNode, newStateNode);
        Assert.assertEquals(deletedStateNode.build(), newStateNode);

        final Long afterCount = getTransaction().executeQuery(Visitors.scalarVisitor(),
                "SELECT COUNT(*) FROM abp_STATE_NODE WHERE ID=?", stateNodeId).getLong();
        Assert.assertEquals(String.format("stateNode %d should have been deleted", stateNodeId), 0, afterCount.longValue());
    }

    /**
     * test a stateNode can be updated via
     * {@link StateNodeDao#updateStateNode(com.porpoise.gen.database.IDbTransaction, IStateNode, Long)}
     */
    @Test
    public void test_updateStateNode()
    {
        //
        // create a new stateNode
        //
        final StateNode newStateNode = newStateNode();
        final Long stateNodeId = insertStateNode(newStateNode, null );
        newStateNode.setId(stateNodeId);

        final StateNode updatedStateNode = StateNodeBuilder.copy(newStateNode);

        //
        // alter the stateNode so we can update a new version
        //
        StateNodeFactory.alterStateNode(updatedStateNode);
        Assert.assertTrue(!updatedStateNode.equals(newStateNode));

                StateNodeDao.updateStateNode(getTransaction(), updatedStateNode, null );
        final StateNodeBuilder readStateNode = StateNodeDao.getStateNodeBuilder(getTransaction(), stateNodeId);
        assertShallowBuilder(readStateNode, updatedStateNode);
        if (!updatedStateNode.equals(readStateNode.build()))
        {
            Assert.fail(String.format("The stateNode %s should have been changed to %s, but was %s", newStateNode, updatedStateNode,
                    readStateNode));
        }
    }

    /**
     * test the successful path for the
     * {@link StateNodeDao#findStateNodeForStateMachineNodes(com.porpoise.gen.database.IDbTransaction, Long)}
     * method
     */
    @Test
    public void test_findStateNodeForStateMachineNodes()
    {
        //
        // ensure a unique state machine id with which to create a new stateNode
        //
        Long stateMachineNodesId = getTransaction().executeQuery(Visitors.scalarVisitor(),
                "SELECT MAX(STATE_MACHINE_NODES_ID) FROM abp_STATE_NODE").getLong();
        if (stateMachineNodesId == null)
        {
            stateMachineNodesId = Long.valueOf(0);
        }
        else
        {
            stateMachineNodesId = Long.valueOf(stateMachineNodesId.longValue() + 1);
        }

        // ensure one entry for exists with the given stateMachineNodesId
        final StateNode newStateNode = newStateNode();

        final Long stateNodeId = insertStateNode(newStateNode, 
            stateMachineNodesId
        );
        newStateNode.setId(stateNodeId);

        //
        // call our method under test
        //
        final Collection<StateNodeBuilder> foundStateNodeCollection = StateNodeDao.findStateNodeBuildersForStateMachineNodes(getTransaction(), stateMachineNodesId);

        //
        // assert the same stateNode was returned
        //
        Assert.assertEquals(1, foundStateNodeCollection.size());

        final StateNodeBuilder foundStateNode = foundStateNodeCollection.iterator().next();
        assertShallowBuilder(foundStateNode, newStateNode);
        Assert.assertEquals("A single stateNode with the given desk id was expected", newStateNode, foundStateNode.build());
    }
 
    private Long insertStateNode(final StateNode stateNode  ,  final Long stateMachineNodesId  )
    {
        //
        // insert the new stateNode
        //
        final Long insertedStateNodeID = StateNodeDao.insertStateNode(getTransaction(), stateNode  ,  stateMachineNodesId  );

        //
        // check the new stateNode ID
        //
        Assert.assertNotNull(insertedStateNodeID);

        return insertedStateNodeID;
    }

    /**
     * @return a new StateNode which can be inserted
     */
    private StateNode newStateNode()
    {
        final StateNode stateNode = StateNodeFactory.newStateNode(true);
        stateNode.setId(null);
        return stateNode; 
    }

    /**
     * the found state node will only be a shallow find, so assert the IDs are
     * correct and then add the real values to the builder to support an
     * object equals call
     *
     * @param stateNodeBuilder
     *          the initialized builder containing shallow references to complex objects
     * @param sourceStateNode
     *          the source State Node used to create the builder
     * @return the builder for convenience
     */
    private StateNodeBuilder assertShallowBuilder(final StateNodeBuilder stateNodeBuilder, final StateNode sourceStateNode)
    {
            //
            // assert transitions IDs
            // this actually should always be empty, as the StateNodeDao won't call into
            // other DAOs
            //
            final List<Long> foundTransitionsIds = stateNodeBuilder.getTransitionsIds();
            if (!foundTransitionsIds.isEmpty())
            {
                final List<Long> originalTransitionsIds = Lists.transform(sourceStateNode.getTransitions(), new Function<StateTransition, Long>()
                {
                    @Override
                    public Long apply(final StateTransition stateTransition)
                    {
                        return stateTransition.getId();
                    }
                });
                Assert.assertArrayEquals(originalTransitionsIds.toArray(), foundTransitionsIds.toArray());
            }
            stateNodeBuilder.setTransitions(sourceStateNode.getTransitions());

          return stateNodeBuilder;
    }
 
}
