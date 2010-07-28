/**
 * ApplicationInfo.java

 */
package com.porpoise.gen.aaron;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.porpoise.gen.version.VersionControl;
import com.porpoise.gen.cache.ObjectCache;

/**
 * ApplicationInfo
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public enum ApplicationInfo
{
    /**
     * the singleton instance.
     * 
     * Use {@link #getInstance()} for access.
     */
    INSTANCE;

    private final Map<UUID, SessionInfo> sessionMap;
    private final ObjectCache            objectCache;

    private final VersionControl         versionControl = new VersionControl();

    /**
     * @return the singleton instance
     */
    public static ApplicationInfo getInstance()
    {
        return INSTANCE;
    }

    private ApplicationInfo()
    {
        this.sessionMap = new HashMap<UUID, SessionInfo>();
        this.objectCache = new ObjectCache();
    }

    /**
     * @return a new session
     */
    public SessionInfo newSession()
    {
        final UUID id = UUID.randomUUID();
        final SessionInfo session = new SessionInfo(id, this);
        this.sessionMap.put(id, session);
        return session;
    }

    /**
     * return the version control, which keeps a record of what sessions have
     * what objects checked out
     * 
     * @return the version control object
     */
    public VersionControl getVersionControl()
    {
        return this.versionControl;
    }

    /**
     * @return the application-wide object cache
     */
    ObjectCache getObjectCache()
    {
        return this.objectCache;
    }
}
