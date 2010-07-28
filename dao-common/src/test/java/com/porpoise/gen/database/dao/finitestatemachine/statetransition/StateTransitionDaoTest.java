/**
 * StateTransitionDaoTest.java

 */
package com.porpoise.gen.database.dao.finitestatemachine.statetransition;

import java.util.List;
import java.util.Collection;

import com.porpoise.gen.finitestatemachine.statetransition.IStateTransition;
import com.porpoise.gen.finitestatemachine.statetransition.StateTransitionBuilder;
import com.porpoise.gen.finitestatemachine.statetransition.StateTransitionFactory;
import com.porpoise.gen.finitestatemachine.statetransition.IStateTransitionDetails;
import com.porpoise.gen.database.AbstractTransactionalTest;
import com.porpoise.gen.database.visitors.Visitors;
import com.porpoise.gen.util.collections.Function;
import com.porpoise.gen.util.collections.Lists;
import com.porpoise.gen.test.Assert;
import org.junit.Test;


/**
 * StateTransitionDaoTest
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class StateTransitionDaoTest extends AbstractTransactionalTest
{
    /**
     * test the successful path for the StateTransitionDao.insertStateTransition method
     */
    @Test
    public void test_insertStateTransition1()
    {

        //
        // create a state transition to insert
        //
        final StateTransition stateTransition = newStateTransition();
        final Long stateNodeTransitionsId = Long.valueOf(0);
 
        //
        // insert the new state transition
        //
        final Long insertedStateTransitionID = StateTransitionDao.insertStateTransition(
            getTransaction(),
        stateTransition, 
        stateNodeTransitionsId
      );

        //
        // check the new state transition ID
        //
        Assert.assertNotNull(insertedStateTransitionID);
    }

    /**
     * test the successful path for the
     * {@link StateTransitionDao\#insertStateTransition(com.porpoise.gen.database.IDbTransaction, StateTransition , Long)}
     * method
     */
    @Test
    public void test_insertStateTransition()
    {
        final StateTransition stateTransition = newStateTransition();
        insertStateTransition(stateTransition, null );
    }

    /**
     * test a stateTransition can be read back using the
     * {@link StateTransitionDao#getStateTransition(com.porpoise.gen.database.IDbTransaction, Long)} method
     */
    @Test
    public void test_getStateTransition()
    {
        //
        // create a stateTransition to find
        //
        final StateTransition newStateTransition = newStateTransition();
        final Long newStateTransitionId = insertStateTransition(newStateTransition, null );
        newStateTransition.setId(newStateTransitionId);

        //
        // call the method under test
        //
        final StateTransitionBuilder foundStateTransition = StateTransitionDao.getStateTransitionBuilder(getTransaction(), newStateTransitionId);


        StateTransition readStateTransition = foundStateTransition.build();
        Assert.assertNotSame(newStateTransition, readStateTransition);
        Assert.assertEquals("The read stateTransition doesn't equal the saved stateTransition", newStateTransition, readStateTransition);
    }

    /**
     * test a stateTransition can be deleted via
     * {@link StateTransitionDao#deleteStateTransition(com.porpoise.gen.database.IDbTransaction, Long, boolean)}
     */
    @Test
    public void test_deleteStateTransition()
    {
        //
        // create a new stateTransition
        //
        final StateTransition newStateTransition = newStateTransition();
        final Long stateTransitionId = insertStateTransition(newStateTransition, null );
        newStateTransition.setId(stateTransitionId);

        //
        // check we can find it
        //
        final Long beforeCount = getTransaction().executeQuery(Visitors.scalarVisitor(),
                "SELECT COUNT(*) FROM abp_STATE_TRANSITION WHERE ID=?", stateTransitionId).getLong();
        Assert.assertEquals(1, beforeCount.longValue());

        //
        // call the method under test
        //
        final StateTransitionBuilder deletedStateTransition = StateTransitionDao.deleteStateTransition(getTransaction(), stateTransitionId, false);

        //
        // assert the stateTransition has been deleted
        //
        Assert.assertEquals(deletedStateTransition.build(), newStateTransition);

        final Long afterCount = getTransaction().executeQuery(Visitors.scalarVisitor(),
                "SELECT COUNT(*) FROM abp_STATE_TRANSITION WHERE ID=?", stateTransitionId).getLong();
        Assert.assertEquals(String.format("stateTransition %d should have been deleted", stateTransitionId), 0, afterCount.longValue());
    }

    /**
     * test a stateTransition can be updated via
     * {@link StateTransitionDao#updateStateTransition(com.porpoise.gen.database.IDbTransaction, IStateTransition, Long)}
     */
    @Test
    public void test_updateStateTransition()
    {
        //
        // create a new stateTransition
        //
        final StateTransition newStateTransition = newStateTransition();
        final Long stateTransitionId = insertStateTransition(newStateTransition, null );
        newStateTransition.setId(stateTransitionId);

        final StateTransition updatedStateTransition = StateTransitionBuilder.copy(newStateTransition);

        //
        // alter the stateTransition so we can update a new version
        //
        StateTransitionFactory.alterStateTransition(updatedStateTransition);
        Assert.assertTrue(!updatedStateTransition.equals(newStateTransition));

                StateTransitionDao.updateStateTransition(getTransaction(), updatedStateTransition, null );
        final StateTransitionBuilder readStateTransition = StateTransitionDao.getStateTransitionBuilder(getTransaction(), stateTransitionId);
        if (!updatedStateTransition.equals(readStateTransition.build()))
        {
            Assert.fail(String.format("The stateTransition %s should have been changed to %s, but was %s", newStateTransition, updatedStateTransition,
                    readStateTransition));
        }
    }

    /**
     * test the successful path for the
     * {@link StateTransitionDao#findStateTransitionForStateNodeTransitions(com.porpoise.gen.database.IDbTransaction, Long)}
     * method
     */
    @Test
    public void test_findStateTransitionForStateNodeTransitions()
    {
        //
        // ensure a unique state node id with which to create a new stateTransition
        //
        Long stateNodeTransitionsId = getTransaction().executeQuery(Visitors.scalarVisitor(),
                "SELECT MAX(STATE_NODE_TRANSITIONS_ID) FROM abp_STATE_TRANSITION").getLong();
        if (stateNodeTransitionsId == null)
        {
            stateNodeTransitionsId = Long.valueOf(0);
        }
        else
        {
            stateNodeTransitionsId = Long.valueOf(stateNodeTransitionsId.longValue() + 1);
        }

        // ensure one entry for exists with the given stateNodeTransitionsId
        final StateTransition newStateTransition = newStateTransition();

        final Long stateTransitionId = insertStateTransition(newStateTransition, 
            stateNodeTransitionsId
        );
        newStateTransition.setId(stateTransitionId);

        //
        // call our method under test
        //
        final Collection<StateTransitionBuilder> foundStateTransitionCollection = StateTransitionDao.findStateTransitionBuildersForStateNodeTransitions(getTransaction(), stateNodeTransitionsId);

        //
        // assert the same stateTransition was returned
        //
        Assert.assertEquals(1, foundStateTransitionCollection.size());

        final StateTransitionBuilder foundStateTransition = foundStateTransitionCollection.iterator().next();
        Assert.assertEquals("A single stateTransition with the given desk id was expected", newStateTransition, foundStateTransition.build());
    }
 
    private Long insertStateTransition(final StateTransition stateTransition  ,  final Long stateNodeTransitionsId  )
    {
        //
        // insert the new stateTransition
        //
        final Long insertedStateTransitionID = StateTransitionDao.insertStateTransition(getTransaction(), stateTransition  ,  stateNodeTransitionsId  );

        //
        // check the new stateTransition ID
        //
        Assert.assertNotNull(insertedStateTransitionID);

        return insertedStateTransitionID;
    }

    /**
     * @return a new StateTransition which can be inserted
     */
    private StateTransition newStateTransition()
    {
        final StateTransition stateTransition = StateTransitionFactory.newStateTransition(true);
        stateTransition.setId(null);
        return stateTransition; 
    }

 
}
