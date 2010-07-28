/**
 * IVersionedObject.java

 */
package com.porpoise.gen.version;

public interface IVersionedObject
{
    /**
     * get the version info for this key
     * 
     * @return the version info for this key
     */
    VersionInfo getVersionInfo();

    void setVersionInfo(VersionInfo version);

}
