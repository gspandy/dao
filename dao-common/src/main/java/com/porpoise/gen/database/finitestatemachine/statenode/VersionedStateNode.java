/**
 * VersionedStateNode.java

 */
package com.porpoise.gen.database.finitestatemachine.statenode;

import com.porpoise.gen.finitestatemachine.statenode.StateNodeDelegate;
import com.porpoise.gen.finitestatemachine.statenode.StateNode;
import com.porpoise.gen.version.IVersionSupplier;
import com.porpoise.gen.version.IVersionedObject;
import com.porpoise.gen.version.VersionInfo;

/**
 * VersionedStateNode
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class VersionedStateNode extends StateNodeDelegate implements IStateNode, IVersionSupplier<Long>, IVersionedObject
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private VersionInfo       version;

    /**
     * @param delegate The underlying implementation
     */
    public VersionedStateNode(final StateNode delegate)
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
     * @return the version number for this state node
     */
    public int getVersionNumber()
    {
        return getVersionInfo().getVersionNumber();
    }
}
