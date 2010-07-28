/**
 * DbConfiguration.java
 */
package com.porpoise.dao.database;

import java.util.Properties;

import com.google.common.base.Strings;
import com.porpoise.dao.Resources;

/**
 * DbConfiguration created: Jul 27, 2010
 * 
 * @author Aaron
 *         <p>
 *         enumeration representing all available values for a database configuration.
 *         </p>
 *         <p>
 *         If any new configuration keys are added to a property file, then they should be included here so they can be referenced safely.
 *         </p>
 *         <p>
 *         This class will be initialised the first time a property's value is accessed via {@link getValue}.
 *         </p>
 */
public enum DbConfiguration
{
    /** */
    USER("jdbc.user")
    {
        @Override
        protected void initDetails(final DbConnectionDetails details)
        {
            details.setUser(getValue());
        }
    },
    /** */
    PASSWORD("jdbc.password")
    {
        @Override
        protected void initDetails(final DbConnectionDetails details)
        {
            details.setPassword(getValue());
        }
    },
    /** */
    VENDOR("jdbc.vendor")
    {
        @Override
        protected void initDetails(final DbConnectionDetails details)
        {
            details.setVendor(getValue());
        }
    },
    /** */
    URL("jdbc.url")
    {
        @Override
        protected void initDetails(final DbConnectionDetails details)
        {
            details.setUrl(getValue());
        }
    },
    /** */
    DATABASE_NAME("jdbc.database.name")
    {
        @Override
        protected void initDetails(final DbConnectionDetails details)
        {
            details.setDatabaseName(getValue());
        }
    };

    private final String propertyName;
    private String       value;

    DbConfiguration(final String prop)
    {
        this.propertyName = prop;
    }

    /**
     * System property which, when set, specifies the database property file location. The classpath will first be searched, followed by the
     * file system if it is an absolute location.
     */
    public static final String      SYSTEM_PROPERTY_CONFIG_LOCATION = "com.porpoise.gen.aaron.config";
    /**
     * The default property file to use when the {@link #SYSTEM_PROPERTY_CONFIG_LOCATION} is not set
     */
    public static final String      DEFAULT_CONFIGURATION           = "database.properties";

    private static volatile boolean initialized                     = false;

    /**
     * ensures {@link init} has been called
     */
    private synchronized static void initIfRequired()
    {
        if (!initialized)
        {
            init();
        }
    }

    /**
     * <p>
     * initialise the configuration. If not called explicitly this method will be called the first time a configuration property is
     * accessed.
     * </p>
     * <p>
     * Multiple calls will force reinitialisation, potentially reloaded previous property values
     * </p>
     */
    private static synchronized final void init()
    {
        final Properties properties = loadConfiguration();
        for (final DbConfiguration configProperty : values())
        {
            configProperty.initialize(properties);
        }
        initialized = true;
    }

    /**
     * return the configuration value. If this is the first call to retrieve a property then the DatabaseConfiguration will attempt to
     * initialize all properties based on the configuration file.
     * 
     * @return the value for the given configuration property
     */
    public String getValue()
    {
        initIfRequired();
        return this.value;
    }

    private void initialize(final Properties properties)
    {
        this.value = properties.getProperty(this.propertyName);
    }

    /**
     * load the configuration from either the default properties file or the properties file as specified by {@code
     * SYSTEM_PROPERTY_CONFIG_LOCATION}
     * 
     * @return the properties for the configuration
     */
    private static Properties loadConfiguration()
    {
        final String systemProperty = SYSTEM_PROPERTY_CONFIG_LOCATION;
        final String defaultLocation = DEFAULT_CONFIGURATION;
        return loadProperties(systemProperty, defaultLocation);
    }

    /**
     * @param systemProperty
     *            the string used to denote where to find the properties from the system properties
     * @param defaultLocation
     *            the default location if no system property value was specified
     * @return the Properties at the given location
     * @throws IllegalStateException
     *             if the properties could not be found
     */
    public static Properties loadProperties(final String systemProperty, final String defaultLocation)
    {
        String location = System.getProperty(systemProperty);
        if (Strings.isNullOrEmpty(location))
        {
            location = defaultLocation;
        }
        final Properties properties = Resources.loadProperties(location);
        if (properties == null)
        {
            throw new IllegalStateException(String.format("Couldn't find properties '%s' on the classpath or file system. "
                    + "Is the system property '%s' set? ", location, systemProperty));
        }
        return properties;
    }

    /**
     * Convert the configuration into a {@link DbConnectionDetails} object
     * 
     * @return the DbConnectionDetails representation of this configuration
     */
    public static synchronized DbConnectionDetails getConnectionDetails()
    {
        // ConnectionDetails is mutable, so we have to create a new one every time
        final DbConnectionDetails connectionDetails = new DbConnectionDetails();
        for (final DbConfiguration value : values())
        {
            value.initDetails(connectionDetails);
        }
        return connectionDetails;
    }

    protected abstract void initDetails(DbConnectionDetails details);
}
