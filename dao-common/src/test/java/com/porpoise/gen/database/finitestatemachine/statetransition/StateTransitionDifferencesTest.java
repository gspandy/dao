/**
 * StateTransitionDifferencesTest.java

 */
package com.porpoise.gen.database.finitestatemachine.statetransition;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.porpoise.gen.finitestatemachine.statetransition.StateTransitionFactory;
import com.porpoise.gen.finitestatemachine.statetransition.IStateTransitionDetails;
import com.porpoise.gen.finitestatemachine.statetransition.StateTransitionBuilder;
import com.porpoise.gen.database.dao.finitestatemachine.statetransition.StateTransitionDao;
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
 * StateTransitionDifferencesTest
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class StateTransitionDifferencesTest extends AbstractTransactionalTest
{
    private StateTransition originalStateTransition; 
    private StateTransition readStateTransition;
    
    /**
     * prepare the test
     */ 
    @Before
    public final void setup()
    {
        //
        // create a new stateTransition
        //
        this.originalStateTransition = StateTransitionFactory.newStateTransition();
        final Long stateTransitionId = StateTransitionDao.insertStateTransition(
            getTransaction(), 
            originalStateTransition , null /* stateNodeTransitionsId */          );
        this.originalStateTransition.setId(stateTransitionId);
        this.readStateTransition = StateTransitionDao.getStateTransitionBuilder(getTransaction(), stateTransitionId).build();
    }

    /**
     * Test the difference can be found between two state transition objects with different name fields 
     */
    @Test
    public void test_stateTransitionNameDifferences()
    {
        //
        // update the name
        //
        final String newName;
                newName = "a new name name";
                this.readStateTransition.setName(newName);

        final ObjectCache cache = new ObjectCache();
        final IAssemblerSupplier supplier = AssemblerSuppliers.newSupplier(getTransaction(), cache);

        //
        // diff the original stateTransition and the updated one. we should now have
        // two differences:
        // the name being added to the stateTransition and the updated name
        //
        final Differences stateTransitionDifferences = MetaData.stateTransition().diff(supplier, this.originalStateTransition, this.readStateTransition);
        Assert.assertEquals(1, stateTransitionDifferences.size());

        System.out.println(stateTransitionDifferences);

        final Collection<Difference> updatedDifferences = stateTransitionDifferences
                .getDifferencesOfType(DifferenceType.UPDATED);
                
        final Difference difference = updatedDifferences.iterator().next();
        //
        // assert the name has changed
        //
        Assert.assertEquals("stateTransition.name", difference.getTargetPath().toString());
        Assert.assertEquals(newName, difference.getTargetValue());
    }
    /**
     * Test the difference can be found between two state transition objects with different from state fields 
     */
    @Test
    public void test_stateTransitionFromStateDifferences()
    {
        //
        // update the fromState
        //
        final String newFromState;
                newFromState = "a new fromState name";
                this.readStateTransition.setFromState(newFromState);

        final ObjectCache cache = new ObjectCache();
        final IAssemblerSupplier supplier = AssemblerSuppliers.newSupplier(getTransaction(), cache);

        //
        // diff the original stateTransition and the updated one. we should now have
        // two differences:
        // the fromState being added to the stateTransition and the updated fromState
        //
        final Differences stateTransitionDifferences = MetaData.stateTransition().diff(supplier, this.originalStateTransition, this.readStateTransition);
        Assert.assertEquals(1, stateTransitionDifferences.size());

        System.out.println(stateTransitionDifferences);

        final Collection<Difference> updatedDifferences = stateTransitionDifferences
                .getDifferencesOfType(DifferenceType.UPDATED);
                
        final Difference difference = updatedDifferences.iterator().next();
        //
        // assert the from state has changed
        //
        Assert.assertEquals("stateTransition.fromState", difference.getTargetPath().toString());
        Assert.assertEquals(newFromState, difference.getTargetValue());
    }
    /**
     * Test the difference can be found between two state transition objects with different to state fields 
     */
    @Test
    public void test_stateTransitionToStateDifferences()
    {
        //
        // update the toState
        //
        final String newToState;
                newToState = "a new toState name";
                this.readStateTransition.setToState(newToState);

        final ObjectCache cache = new ObjectCache();
        final IAssemblerSupplier supplier = AssemblerSuppliers.newSupplier(getTransaction(), cache);

        //
        // diff the original stateTransition and the updated one. we should now have
        // two differences:
        // the toState being added to the stateTransition and the updated toState
        //
        final Differences stateTransitionDifferences = MetaData.stateTransition().diff(supplier, this.originalStateTransition, this.readStateTransition);
        Assert.assertEquals(1, stateTransitionDifferences.size());

        System.out.println(stateTransitionDifferences);

        final Collection<Difference> updatedDifferences = stateTransitionDifferences
                .getDifferencesOfType(DifferenceType.UPDATED);
                
        final Difference difference = updatedDifferences.iterator().next();
        //
        // assert the to state has changed
        //
        Assert.assertEquals("stateTransition.toState", difference.getTargetPath().toString());
        Assert.assertEquals(newToState, difference.getTargetValue());
    }
 
 
}
