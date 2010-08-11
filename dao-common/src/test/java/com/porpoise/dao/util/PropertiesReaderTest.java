/**
 * PropertiesReaderTest.java
 */
package com.porpoise.dao.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.porpoise.dao.Resources;
import com.porpoise.dao.test.DbTestConstants;

/**
 * PropertiesReaderTest created: Jul 27, 2010
 * 
 * @author Aaron
 */
public class PropertiesReaderTest
{

    /**
     * test PropertiesReader can find a properties file that should be on the classpath
     */
    @Test
    public void test_findPropertiesOnClasspath()
    {
        final String expectedResourceName = DbTestConstants.CONNECTION_PROPERTIES_DERBY;
        final Properties props = Resources.loadProperties(expectedResourceName);
        Assert.assertNotNull(String.format("%s should've been on the classpath for this test to work", expectedResourceName), props);
    }

    /**
     * test PropertiesReader does NOT find a properties file not on the classpath or filesystem
     */
    @Test
    public void test_doesntFindProperties()
    {
        //
        //
        final Properties unknownProps = Resources.loadProperties("shouldReturnNull");
        Assert.assertNull(unknownProps);
    }

    /**
     * @throws IOException
     */
    @Test
    public void test_findPropertiesOnFileSystem() throws IOException
    {
        //
        // set up a temporary file to read
        //
        final String userDir = System.getProperty("user.dir");
        final File dir = new File(userDir);
        final File temp = File.createTempFile(getClass().getSimpleName(), "-test.properties", dir);
        temp.deleteOnExit();

        final FileOutputStream fos = new FileOutputStream(temp);
        fos.write("test=test value".getBytes());
        fos.close();

        //
        // now test it will be loaded from the file system
        //
        final Properties loadedProperties = Resources.loadProperties(temp.getPath());
        Assert.assertNotNull(loadedProperties);
        Assert.assertEquals("test value", loadedProperties.get("test"));
    }
}
