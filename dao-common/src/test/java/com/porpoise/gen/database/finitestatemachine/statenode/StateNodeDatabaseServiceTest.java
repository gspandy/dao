/**
 * StateNodeDatabaseServiceTest.java

 */
package com.porpoise.gen.database.finitestatemachine.statenode;

import java.net.URL;

import java.util.List;

import com.porpoise.gen.test.Assert;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import com.porpoise.gen.finitestatemachine.statenode.IStateNodeDetails;
import com.porpoise.gen.finitestatemachine.statenode.AbstractStateNodeServiceTest;

import com.porpoise.gen.database.AbstractTransactionalTest;

/**
 * StateNodeDatabaseServiceTest
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class StateNodeDatabaseServiceTest extends AbstractStateNodeServiceTest
{
    private static StateNodeDatabaseService stateNodeService;


    /**
     * @throws Exception
     */
    @BeforeClass
    public static void setUp() throws Exception
    {
        AbstractTransactionalTest.beforeDbConnectionFactoryTest();
        AbstractTransactionalTest.setupDatabaseTransactionTest();
        stateNodeService = new StateNodeDatabaseService();
    }

    /**
     * tear down the test
     */
    @AfterClass
    public static void tearDown()
    {
        AbstractTransactionalTest.afterAbstractTransactionalTest();
        AbstractTransactionalTest.afterDbConnectionFactoryTest();
        stateNodeService = null;
    }
    
    /**
     * Default Constructor
     */
    public StateNodeDatabaseServiceTest()
    {
        super(stateNodeService);
    }
}
