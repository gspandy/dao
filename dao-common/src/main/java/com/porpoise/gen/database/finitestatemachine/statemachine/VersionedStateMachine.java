/**
 * VersionedStateMachine.java

 */
package com.porpoise.gen.database.finitestatemachine.statemachine;

import com.porpoise.gen.finitestatemachine.statemachine.StateMachineDelegate;
import com.porpoise.gen.finitestatemachine.statemachine.StateMachine;
import com.porpoise.gen.version.IVersionSupplier;
import com.porpoise.gen.version.IVersionedObject;
import com.porpoise.gen.version.VersionInfo;

/**
 * VersionedStateMachine
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class VersionedStateMachine extends StateMachineDelegate implements IStateMachine, IVersionSupplier<Long>, IVersionedObject
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private VersionInfo       version;

    /**
     * @param delegate The underlying implementation
     */
    public VersionedStateMachine(final StateMachine delegate)
    {
        super(delegate);
    }

    /**
     * @return the version
     */
    public VersionInfo getVersionInfo()
    {
        return this.version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersionInfo(final VersionInfo version)
    {
        this.version = version;
    }

    @Override
    public Long get()
    {
        return getId();
    }

    /**
     * @return the version number for this state machine
     */
    public int getVersionNumber()
    {
        return getVersionInfo().getVersionNumber();
    }
}
