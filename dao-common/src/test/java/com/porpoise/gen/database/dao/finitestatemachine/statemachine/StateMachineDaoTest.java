/**
 * StateMachineDaoTest.java

 */
package com.porpoise.gen.database.dao.finitestatemachine.statemachine;

import java.util.List;
import java.util.Collection;

import com.porpoise.gen.finitestatemachine.statemachine.IStateMachine;
import com.porpoise.gen.finitestatemachine.statemachine.StateMachineBuilder;
import com.porpoise.gen.finitestatemachine.statemachine.StateMachineFactory;
import com.porpoise.gen.finitestatemachine.statemachine.IStateMachineDetails;
import com.porpoise.gen.finitestatemachine.statenode.IStateNodeDetails;
import com.porpoise.gen.database.AbstractTransactionalTest;
import com.porpoise.gen.database.visitors.Visitors;
import com.porpoise.gen.util.collections.Function;
import com.porpoise.gen.util.collections.Lists;
import com.porpoise.gen.test.Assert;
import org.junit.Test;


/**
 * StateMachineDaoTest
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class StateMachineDaoTest extends AbstractTransactionalTest
{
    /**
     * test the successful path for the StateMachineDao.insertStateMachine method
     */
    @Test
    public void test_insertStateMachine1()
    {

        //
        // create a state machine to insert
        //
        final StateMachine stateMachine = newStateMachine();
 
        //
        // insert the new state machine
        //
        final Long insertedStateMachineID = StateMachineDao.insertStateMachine(
            getTransaction(),
        stateMachine
      );

        //
        // check the new state machine ID
        //
        Assert.assertNotNull(insertedStateMachineID);
    }

    /**
     * test the successful path for the
     * {@link StateMachineDao\#insertStateMachine(com.porpoise.gen.database.IDbTransaction, StateMachine )}
     * method
     */
    @Test
    public void test_insertStateMachine()
    {
        final StateMachine stateMachine = newStateMachine();
        insertStateMachine(stateMachine );
    }

    /**
     * test a stateMachine can be read back using the
     * {@link StateMachineDao#getStateMachine(com.porpoise.gen.database.IDbTransaction, Long)} method
     */
    @Test
    public void test_getStateMachine()
    {
        //
        // create a stateMachine to find
        //
        final StateMachine newStateMachine = newStateMachine();
        final Long newStateMachineId = insertStateMachine(newStateMachine );
        newStateMachine.setId(newStateMachineId);

        //
        // call the method under test
        //
        final StateMachineBuilder foundStateMachine = StateMachineDao.getStateMachineBuilder(getTransaction(), newStateMachineId);

        assertShallowBuilder(foundStateMachine, newStateMachine);

        StateMachine readStateMachine = foundStateMachine.build();
        Assert.assertNotSame(newStateMachine, readStateMachine);
        Assert.assertEquals("The read stateMachine doesn't equal the saved stateMachine", newStateMachine, readStateMachine);
    }

    /**
     * test a stateMachine can be deleted via
     * {@link StateMachineDao#deleteStateMachine(com.porpoise.gen.database.IDbTransaction, Long, boolean)}
     */
    @Test
    public void test_deleteStateMachine()
    {
        //
        // create a new stateMachine
        //
        final StateMachine newStateMachine = newStateMachine();
        final Long stateMachineId = insertStateMachine(newStateMachine );
        newStateMachine.setId(stateMachineId);

        //
        // check we can find it
        //
        final Long beforeCount = getTransaction().executeQuery(Visitors.scalarVisitor(),
                "SELECT COUNT(*) FROM abp_STATE_MACHINE WHERE ID=?", stateMachineId).getLong();
        Assert.assertEquals(1, beforeCount.longValue());

        //
        // call the method under test
        //
        final StateMachineBuilder deletedStateMachine = StateMachineDao.deleteStateMachine(getTransaction(), stateMachineId, false);

        //
        // assert the stateMachine has been deleted
        //
        assertShallowBuilder(deletedStateMachine, newStateMachine);
        Assert.assertEquals(deletedStateMachine.build(), newStateMachine);

        final Long afterCount = getTransaction().executeQuery(Visitors.scalarVisitor(),
                "SELECT COUNT(*) FROM abp_STATE_MACHINE WHERE ID=?", stateMachineId).getLong();
        Assert.assertEquals(String.format("stateMachine %d should have been deleted", stateMachineId), 0, afterCount.longValue());
    }

    /**
     * test a stateMachine can be updated via
     * {@link StateMachineDao#updateStateMachine(com.porpoise.gen.database.IDbTransaction, IStateMachine, Long)}
     */
    @Test
    public void test_updateStateMachine()
    {
        //
        // create a new stateMachine
        //
        final StateMachine newStateMachine = newStateMachine();
        final Long stateMachineId = insertStateMachine(newStateMachine );
        newStateMachine.setId(stateMachineId);

        final StateMachine updatedStateMachine = StateMachineBuilder.copy(newStateMachine);

        //
        // alter the stateMachine so we can update a new version
        //
        StateMachineFactory.alterStateMachine(updatedStateMachine);
        Assert.assertTrue(!updatedStateMachine.equals(newStateMachine));

                StateMachineDao.updateStateMachine(getTransaction(), updatedStateMachine );
        final StateMachineBuilder readStateMachine = StateMachineDao.getStateMachineBuilder(getTransaction(), stateMachineId);
        assertShallowBuilder(readStateMachine, updatedStateMachine);
        if (!updatedStateMachine.equals(readStateMachine.build()))
        {
            Assert.fail(String.format("The stateMachine %s should have been changed to %s, but was %s", newStateMachine, updatedStateMachine,
                    readStateMachine));
        }
    }

 
    private Long insertStateMachine(final StateMachine stateMachine  )
    {
        //
        // insert the new stateMachine
        //
        final Long insertedStateMachineID = StateMachineDao.insertStateMachine(getTransaction(), stateMachine  );

        //
        // check the new stateMachine ID
        //
        Assert.assertNotNull(insertedStateMachineID);

        return insertedStateMachineID;
    }

    /**
     * @return a new StateMachine which can be inserted
     */
    private StateMachine newStateMachine()
    {
        final StateMachine stateMachine = StateMachineFactory.newStateMachine(true);
        stateMachine.setId(null);
        return stateMachine; 
    }

    /**
     * the found state machine will only be a shallow find, so assert the IDs are
     * correct and then add the real values to the builder to support an
     * object equals call
     *
     * @param stateMachineBuilder
     *          the initialized builder containing shallow references to complex objects
     * @param sourceStateMachine
     *          the source State Machine used to create the builder
     * @return the builder for convenience
     */
    private StateMachineBuilder assertShallowBuilder(final StateMachineBuilder stateMachineBuilder, final StateMachine sourceStateMachine)
    {
            //
            // assert nodes IDs
            // this actually should always be empty, as the StateMachineDao won't call into
            // other DAOs
            //
            final List<Long> foundNodesIds = stateMachineBuilder.getNodesIds();
            if (!foundNodesIds.isEmpty())
            {
                final List<Long> originalNodesIds = Lists.transform(sourceStateMachine.getNodes(), new Function<StateNode, Long>()
                {
                    @Override
                    public Long apply(final StateNode stateNode)
                    {
                        return stateNode.getId();
                    }
                });
                Assert.assertArrayEquals(originalNodesIds.toArray(), foundNodesIds.toArray());
            }
            stateMachineBuilder.setNodes(sourceStateMachine.getNodes());

          return stateMachineBuilder;
    }
 
}
