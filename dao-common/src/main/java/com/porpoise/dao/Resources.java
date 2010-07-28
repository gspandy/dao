package com.porpoise.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

public enum Resources
{
    ; // uninstantiable

    /**
     * find the file on the classpath
     * 
     * @param resourceName
     *            the resource to find
     * @return the File as located on the classpath or null if none found
     */
    public static File findFileOnClasspath(final String resourceName)
    {
        File dir = null;
        Enumeration<URL> resources;
        try
        {
            resources = Resources.class.getClassLoader().getResources(resourceName);
        }
        catch (final IOException e1)
        {
            throw new RuntimeException(e1);
        }
        RuntimeException lastException = null;
        if (resources != null)
        {
            while (dir == null && resources.hasMoreElements())
            {
                final URL resource = resources.nextElement();
                if (resource != null)
                {
                    try
                    {
                        dir = new File(resource.toURI());
                    }
                    catch (final URISyntaxException e)
                    {
                        lastException = new RuntimeException(String.format("Invalid syntax for %s", resources), e);
                    }
                    catch (final IllegalArgumentException e)
                    {
                        lastException = new IllegalArgumentException("Invalid resource: " + resourceName, e);
                    }
                }
            }
        }
        if (dir == null)
        {
            if (lastException != null)
            {
                throw lastException;
            }
            throw new IllegalArgumentException("No file could be found for " + resourceName);
        }
        return dir;
    }

    /**
     * load the properties from a given resource string by first checking the classpath, then the file system
     * 
     * @param resource
     * @return the Properties initialized from the given resource or null if no resource could be found for the given name
     */
    public static Properties loadProperties(final String resource)
    {
        Properties properties = searchClasspath(resource);
        if (properties == null)
        {
            properties = searchFileSystem(resource);
        }
        return properties;
    }

    private static Properties searchClasspath(final String resource)
    {
        Properties properties = null;
        final InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(resource);
        if (resourceAsStream != null)
        {
            properties = new Properties();
            try
            {
                properties.load(resourceAsStream);
            }
            catch (final IOException e)
            {
                throw new RuntimeException(e);
            }
            finally
            {
                try
                {
                    resourceAsStream.close();
                }
                catch (final IOException e)
                {
                    Logger.getLogger(Resources.class).warn("Error closing properties stream", e);
                }
            }

        }
        return properties;
    }

    private static Properties searchFileSystem(final String resource)
    {
        Properties properties = null;

        final File file = new File(resource);
        if (file.exists())
        {
            FileInputStream input = null;
            try
            {
                input = new FileInputStream(file);
                properties = new Properties();
                properties.load(input);
            }
            catch (final IOException e)
            {
                throw new RuntimeException(e);
            }
            finally
            {
                if (input != null)
                {
                    try
                    {
                        input.close();
                    }
                    catch (final IOException e)
                    {
                        Logger.getLogger(Resources.class).warn(String.format("Error closing file input stream from %s", resource), e);
                    }
                }
            }
        }
        return properties;
    }
}
