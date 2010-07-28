/**
 * DbConnectionDetails.java

 */
package com.porpoise.gen.database;

import com.porpoise.gen.database.init.Databases;

import com.porpoise.gen.util.Strings;

/**
 * DbConnectionDetails
 * 
 * created: Jul 27, 2010
 * @author Aaron
 * *
 * <p>
 * The DbConnectionDetails class encapsulates the data contained in
 * {@link DbConfiguration}. This bean copy of the DatabaseConfiguration
 * enumeration exists to allow other.
 * </p>
 */
public class DbConnectionDetails
{
    private String user;
    private String password;
    private String databaseName;
    private String vendor;
    private String url;

    /**
     * @param value The database user
     */
    public void setUser(final String value)
    {
        this.user = value;
    }

    /**
     * @param value The database password
     */
    public void setPassword(final String value)
    {
        this.password = value;
    }

    /**
     * @param value The database vendor. This should be a valid Databases enumeration string value.
     */
    public void setVendor(final String value)
    {
        this.vendor = value;
    }

    /**
     * @param value The database name
     */
    public void setDatabaseName(final String value)
    {
        this.databaseName = value;
    }

    /**
     * @return the user
     */
    public String getUser()
    {
        return this.user;
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return this.password;
    }

    /**
     * @return the databaseName
     */
    public String getDatabaseName()
    {
        return this.databaseName;
    }

    /**
     * @return the vendor
     */
    public String getVendor()
    {
        return this.vendor;
    }


    /**
     * @return the database URL as a string
     */
    public String getUrl()
    {
        return this.url;
    }

    /**
     * @param value the database URL
     */
    public void setUrl(final String value)
    {
        this.url = value;
    }


    /**
     * return the database vendor as an enumeration
     * 
     * @return the vendor
     */
    public Databases getDatabaseVendor()
    {
        if (!Strings.hasValue(getVendor()))
        {
            throw new IllegalStateException("Vendor property is not set");
        }
        return Databases.valueOf(getVendor().toUpperCase());
    }

}
