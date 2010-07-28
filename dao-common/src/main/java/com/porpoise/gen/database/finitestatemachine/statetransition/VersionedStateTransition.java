/**
 * VersionedStateTransition.java

 */
package com.porpoise.gen.database.finitestatemachine.statetransition;

import com.porpoise.gen.finitestatemachine.statetransition.StateTransitionDelegate;
import com.porpoise.gen.finitestatemachine.statetransition.StateTransition;
import com.porpoise.gen.version.IVersionSupplier;
import com.porpoise.gen.version.IVersionedObject;
import com.porpoise.gen.version.VersionInfo;

/**
 * VersionedStateTransition
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class VersionedStateTransition extends StateTransitionDelegate implements IStateTransition, IVersionSupplier<Long>, IVersionedObject
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private VersionInfo       version;

    /**
     * @param delegate The underlying implementation
     */
    public VersionedStateTransition(final StateTransition delegate)
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
     * @return the version number for this state transition
     */
    public int getVersionNumber()
    {
        return getVersionInfo().getVersionNumber();
    }
}
