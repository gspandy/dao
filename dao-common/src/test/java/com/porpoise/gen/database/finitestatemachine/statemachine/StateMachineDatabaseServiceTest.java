/**
 * StateMachineDatabaseServiceTest.java

 */
package com.porpoise.gen.database.finitestatemachine.statemachine;

import java.net.URL;

import java.util.List;

import com.porpoise.gen.test.Assert;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import com.porpoise.gen.finitestatemachine.statemachine.IStateMachineDetails;
import com.porpoise.gen.finitestatemachine.statemachine.AbstractStateMachineServiceTest;

import com.porpoise.gen.database.AbstractTransactionalTest;

/**
 * StateMachineDatabaseServiceTest
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class StateMachineDatabaseServiceTest extends AbstractStateMachineServiceTest
{
    private static StateMachineDatabaseService stateMachineService;


    /**
     * @throws Exception
     */
    @BeforeClass
    public static void setUp() throws Exception
    {
        AbstractTransactionalTest.beforeDbConnectionFactoryTest();
        AbstractTransactionalTest.setupDatabaseTransactionTest();
        stateMachineService = new StateMachineDatabaseService();
    }

    /**
     * tear down the test
     */
    @AfterClass
    public static void tearDown()
    {
        AbstractTransactionalTest.afterAbstractTransactionalTest();
        AbstractTransactionalTest.afterDbConnectionFactoryTest();
        stateMachineService = null;
    }
    
    /**
     * Default Constructor
     */
    public StateMachineDatabaseServiceTest()
    {
        super(stateMachineService);
    }
}
