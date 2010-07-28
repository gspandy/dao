/**
 * ThreadData.java

 */
package com.porpoise.gen.aaron;

/**
 * ThreadData
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 *
 * ThreadData provides thread local data
 */
public class ThreadData
{
    private static ThreadLocal<ThreadData> THREAD_LOCAL = new ThreadLocal<ThreadData>() {

                                                            @Override
                                                            protected ThreadData initialValue()
                                                            {
                                                                return new ThreadData();
                                                            }

                                                        };
    private final SessionInfo              session;

    private ThreadData()
    {
        this.session = ApplicationInfo.getInstance().newSession();
    }

    /**
     * @return the thread data
     */
    public static ThreadData getThreadData()
    {
        return THREAD_LOCAL.get();
    }

    /**
     * @return the session
     */
    public SessionInfo getSession()
    {
        return this.session;
    }

}
