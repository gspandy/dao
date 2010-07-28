/**
 * SessionInfo.java

 */
package com.porpoise.gen.aaron;

import java.util.UUID;

import com.porpoise.gen.cache.ObjectCache;

/**
 * SessionInfo
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * */
public class SessionInfo
{
    private final ApplicationInfo app;
    private final UUID            sessionId;
    private final ObjectCache     sessionCache;

    SessionInfo(final UUID id, final ApplicationInfo appInfo)
    {
        this.sessionId = id;
        this.app = appInfo;
        this.sessionCache = ObjectCache.newDelayedCache(appInfo.getObjectCache());
    }

    /**
     * @return a new local object cache
     */
    public ObjectCache newLocalCache()
    {
        return ObjectCache.newDelayedCache(getSessionCache());
    }

    /**
     * @return the session cache
     */
    public ObjectCache getSessionCache()
    {
        return this.sessionCache;
    }

    /**
     * @return the sessionId
     */
    public UUID getSessionId()
    {
        return this.sessionId;
    }

    /**
     * @return the application-scope object
     */
    public ApplicationInfo getAppInfo()
    {
        return this.app;
    }

    /**
     * @return the string value for this session
     */
    @Override
    public String toString()
    {
        return String.format("Session [%s]", getSessionId());
    }

}