/**
 * ResultSetDelegate.java

 */
package com.porpoise.dao.database;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * ResultSetDelegate
 * 
 * created: Jul 27, 2010
 * @author Aaron
 *
 *
 * Delegate pattern, designed to be subclassed
 */
public abstract class ResultSetDelegate implements ResultSet
{
    private final ResultSet delegate;

    public ResultSetDelegate(final ResultSet resultSetDelegate)
    {
        this.delegate = resultSetDelegate;
    }

    /**
     * @see java.sql.ResultSet#absolute(int)
     */
    public boolean absolute(final int row) throws SQLException
    {
        return this.delegate.absolute(row);
    }

    /**
     * @see java.sql.ResultSet#afterLast()
     */
    public void afterLast() throws SQLException
    {
        this.delegate.afterLast();
    }

    /**
     * @see java.sql.ResultSet#beforeFirst()
     */
    public void beforeFirst() throws SQLException
    {
        this.delegate.beforeFirst();

    }

    /**
     * @see java.sql.ResultSet#cancelRowUpdates()
     */
    public void cancelRowUpdates() throws SQLException
    {
        this.delegate.cancelRowUpdates();

    }

    /**
     * @see java.sql.ResultSet#clearWarnings()
     */
    public void clearWarnings() throws SQLException
    {
        this.delegate.clearWarnings();

    }

    /**
     * @see java.sql.ResultSet#close()
     */
    public void close() throws SQLException
    {
        this.delegate.close();

    }

    /**
     * @see java.sql.ResultSet#deleteRow()
     */
    public void deleteRow() throws SQLException
    {
        this.delegate.deleteRow();

    }

    /**
     * @see java.sql.ResultSet#findColumn(java.lang.String)
     */
    public int findColumn(final String columnLabel) throws SQLException
    {
        return this.delegate.findColumn(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#first()
     */
    public boolean first() throws SQLException
    {
        return this.delegate.first();
    }

    /**
     * @see java.sql.ResultSet#getArray(int)
     */
    public Array getArray(final int columnIndex) throws SQLException
    {
        return this.delegate.getArray(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getArray(java.lang.String)
     */
    public Array getArray(final String columnLabel) throws SQLException
    {
        return this.delegate.getArray(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getAsciiStream(int)
     */
    public InputStream getAsciiStream(final int columnIndex) throws SQLException
    {
        return this.delegate.getAsciiStream(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getAsciiStream(java.lang.String)
     */
    public InputStream getAsciiStream(final String columnLabel) throws SQLException
    {
        return this.delegate.getAsciiStream(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getBigDecimal(int)
     */
    public BigDecimal getBigDecimal(final int columnIndex) throws SQLException
    {
        return this.delegate.getBigDecimal(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getBigDecimal(java.lang.String)
     */
    public BigDecimal getBigDecimal(final String columnLabel) throws SQLException
    {
        return this.delegate.getBigDecimal(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getBigDecimal(int, int)
     */
    public BigDecimal getBigDecimal(final int columnIndex, final int scale) throws SQLException
    {
        return this.delegate.getBigDecimal(columnIndex, scale);
    }

    /**
     * @see java.sql.ResultSet#getBigDecimal(java.lang.String, int)
     */
    public BigDecimal getBigDecimal(final String columnLabel, final int scale) throws SQLException
    {
        return this.delegate.getBigDecimal(columnLabel, scale);
    }

    /**
     * @see java.sql.ResultSet#getBinaryStream(int)
     */
    public InputStream getBinaryStream(final int columnIndex) throws SQLException
    {
        return this.delegate.getBinaryStream(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getBinaryStream(java.lang.String)
     */
    public InputStream getBinaryStream(final String columnLabel) throws SQLException
    {
        return this.delegate.getBinaryStream(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getBlob(int)
     */
    public Blob getBlob(final int columnIndex) throws SQLException
    {
        return this.delegate.getBlob(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getBlob(java.lang.String)
     */
    public Blob getBlob(final String columnLabel) throws SQLException
    {
        return this.delegate.getBlob(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getBoolean(int)
     */
    public boolean getBoolean(final int columnIndex) throws SQLException
    {
        return this.delegate.getBoolean(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getBoolean(java.lang.String)
     */
    public boolean getBoolean(final String columnLabel) throws SQLException
    {
        return this.delegate.getBoolean(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getByte(int)
     */
    public byte getByte(final int columnIndex) throws SQLException
    {
        return this.delegate.getByte(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getByte(java.lang.String)
     */
    public byte getByte(final String columnLabel) throws SQLException
    {
        return this.delegate.getByte(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getBytes(int)
     */
    public byte[] getBytes(final int columnIndex) throws SQLException
    {
        return this.delegate.getBytes(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getBytes(java.lang.String)
     */
    public byte[] getBytes(final String columnLabel) throws SQLException
    {
        return this.delegate.getBytes(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getCharacterStream(int)
     */
    public Reader getCharacterStream(final int columnIndex) throws SQLException
    {
        return this.delegate.getCharacterStream(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getCharacterStream(java.lang.String)
     */
    public Reader getCharacterStream(final String columnLabel) throws SQLException
    {
        return this.delegate.getCharacterStream(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getClob(int)
     */
    public Clob getClob(final int columnIndex) throws SQLException
    {
        return this.delegate.getClob(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getClob(java.lang.String)
     */
    public Clob getClob(final String columnLabel) throws SQLException
    {
        return this.delegate.getClob(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getConcurrency()
     */
    public int getConcurrency() throws SQLException
    {
        return this.delegate.getConcurrency();
    }

    /**
     * @see java.sql.ResultSet#getCursorName()
     */
    public String getCursorName() throws SQLException
    {
        return this.delegate.getCursorName();
    }

    /**
     * @see java.sql.ResultSet#getDate(int)
     */
    public Date getDate(final int columnIndex) throws SQLException
    {
        return this.delegate.getDate(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getDate(java.lang.String)
     */
    public Date getDate(final String columnLabel) throws SQLException
    {
        return this.delegate.getDate(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getDate(int, java.util.Calendar)
     */
    public Date getDate(final int columnIndex, final Calendar cal) throws SQLException
    {
        return this.delegate.getDate(columnIndex, cal);
    }

    /**
     * @see java.sql.ResultSet#getDate(java.lang.String, java.util.Calendar)
     */
    public Date getDate(final String columnLabel, final Calendar cal) throws SQLException
    {
        return this.delegate.getDate(columnLabel, cal);
    }

    /**
     * @see java.sql.ResultSet#getDouble(int)
     */
    public double getDouble(final int columnIndex) throws SQLException
    {
        return this.delegate.getDouble(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getDouble(java.lang.String)
     */
    public double getDouble(final String columnLabel) throws SQLException
    {
        return this.delegate.getDouble(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getFetchDirection()
     */
    public int getFetchDirection() throws SQLException
    {
        return this.delegate.getFetchDirection();
    }

    /**
     * @see java.sql.ResultSet#getFetchSize()
     */
    public int getFetchSize() throws SQLException
    {
        return this.delegate.getFetchSize();
    }

    /**
     * @see java.sql.ResultSet#getFloat(int)
     */
    public float getFloat(final int columnIndex) throws SQLException
    {
        return this.delegate.getFloat(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getFloat(java.lang.String)
     */
    public float getFloat(final String columnLabel) throws SQLException
    {
        return this.delegate.getFloat(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getHoldability()
     */
    public int getHoldability() throws SQLException
    {
        return this.delegate.getHoldability();
    }

    /**
     * @see java.sql.ResultSet#getInt(int)
     */
    public int getInt(final int columnIndex) throws SQLException
    {
        return this.delegate.getInt(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getInt(java.lang.String)
     */
    public int getInt(final String columnLabel) throws SQLException
    {
        return this.delegate.getInt(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getLong(int)
     */
    public long getLong(final int columnIndex) throws SQLException
    {
        return this.delegate.getLong(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getLong(java.lang.String)
     */
    public long getLong(final String columnLabel) throws SQLException
    {
        return this.delegate.getLong(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getMetaData()
     */
    public ResultSetMetaData getMetaData() throws SQLException
    {
        return this.delegate.getMetaData();
    }

    /**
     * @see java.sql.ResultSet#getNCharacterStream(int)
     */
    public Reader getNCharacterStream(final int columnIndex) throws SQLException
    {
        return this.delegate.getNCharacterStream(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getNCharacterStream(java.lang.String)
     */
    public Reader getNCharacterStream(final String columnLabel) throws SQLException
    {
        return this.delegate.getNCharacterStream(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getNClob(int)
     */
    public NClob getNClob(final int columnIndex) throws SQLException
    {
        return this.delegate.getNClob(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getNClob(java.lang.String)
     */
    public NClob getNClob(final String columnLabel) throws SQLException
    {
        return this.delegate.getNClob(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getNString(int)
     */
    public String getNString(final int columnIndex) throws SQLException
    {
        return this.delegate.getNString(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getNString(java.lang.String)
     */
    public String getNString(final String columnLabel) throws SQLException
    {
        return this.delegate.getNString(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getObject(int)
     */
    public Object getObject(final int columnIndex) throws SQLException
    {
        return this.delegate.getObject(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getObject(java.lang.String)
     */
    public Object getObject(final String columnLabel) throws SQLException
    {
        return this.delegate.getObject(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getObject(int, java.util.Map)
     */
    public Object getObject(final int columnIndex, final Map<String, Class<?>> map) throws SQLException
    {
        return this.delegate.getObject(columnIndex, map);
    }

    /**
     * @see java.sql.ResultSet#getObject(java.lang.String, java.util.Map)
     */
    public Object getObject(final String columnLabel, final Map<String, Class<?>> map) throws SQLException
    {
        return this.delegate.getObject(columnLabel, map);
    }

    /**
     * @see java.sql.ResultSet#getRef(int)
     */
    public Ref getRef(final int columnIndex) throws SQLException
    {
        return this.delegate.getRef(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getRef(java.lang.String)
     */
    public Ref getRef(final String columnLabel) throws SQLException
    {
        return this.delegate.getRef(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getRow()
     */
    public int getRow() throws SQLException
    {
        return this.delegate.getRow();
    }

    /**
     * @see java.sql.ResultSet#getRowId(int)
     */
    public RowId getRowId(final int columnIndex) throws SQLException
    {
        return this.delegate.getRowId(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getRowId(java.lang.String)
     */
    public RowId getRowId(final String columnLabel) throws SQLException
    {
        return this.delegate.getRowId(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getSQLXML(int)
     */
    public SQLXML getSQLXML(final int columnIndex) throws SQLException
    {
        return this.delegate.getSQLXML(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getSQLXML(java.lang.String)
     */
    public SQLXML getSQLXML(final String columnLabel) throws SQLException
    {
        return this.delegate.getSQLXML(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getShort(int)
     */
    public short getShort(final int columnIndex) throws SQLException
    {
        return this.delegate.getShort(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getShort(java.lang.String)
     */
    public short getShort(final String columnLabel) throws SQLException
    {
        return this.delegate.getShort(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getStatement()
     */
    public Statement getStatement() throws SQLException
    {
        return this.delegate.getStatement();
    }

    /**
     * @see java.sql.ResultSet#getString(int)
     */
    public String getString(final int columnIndex) throws SQLException
    {
        return this.delegate.getString(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getString(java.lang.String)
     */
    public String getString(final String columnLabel) throws SQLException
    {
        return this.delegate.getString(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getTime(int)
     */
    public Time getTime(final int columnIndex) throws SQLException
    {
        return this.delegate.getTime(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getTime(java.lang.String)
     */
    public Time getTime(final String columnLabel) throws SQLException
    {
        return this.delegate.getTime(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getTime(int, java.util.Calendar)
     */
    public Time getTime(final int columnIndex, final Calendar cal) throws SQLException
    {
        return this.delegate.getTime(columnIndex, cal);
    }

    /**
     * @see java.sql.ResultSet#getTime(java.lang.String, java.util.Calendar)
     */
    public Time getTime(final String columnLabel, final Calendar cal) throws SQLException
    {
        return this.delegate.getTime(columnLabel, cal);
    }

    /**
     * @see java.sql.ResultSet#getTimestamp(int)
     */
    public Timestamp getTimestamp(final int columnIndex) throws SQLException
    {
        return this.delegate.getTimestamp(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getTimestamp(java.lang.String)
     */
    public Timestamp getTimestamp(final String columnLabel) throws SQLException
    {
        return this.delegate.getTimestamp(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getTimestamp(int, java.util.Calendar)
     */
    public Timestamp getTimestamp(final int columnIndex, final Calendar cal) throws SQLException
    {
        return this.delegate.getTimestamp(columnIndex, cal);
    }

    /**
     * @see java.sql.ResultSet#getTimestamp(java.lang.String,
     * java.util.Calendar)
     */
    public Timestamp getTimestamp(final String columnLabel, final Calendar cal) throws SQLException
    {
        return this.delegate.getTimestamp(columnLabel, cal);
    }

    /**
     * @see java.sql.ResultSet#getType()
     */
    public int getType() throws SQLException
    {
        return this.delegate.getType();
    }

    /**
     * @see java.sql.ResultSet#getURL(int)
     */
    public URL getURL(final int columnIndex) throws SQLException
    {
        return this.delegate.getURL(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getURL(java.lang.String)
     */
    public URL getURL(final String columnLabel) throws SQLException
    {
        return this.delegate.getURL(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getUnicodeStream(int)
     */
    public InputStream getUnicodeStream(final int columnIndex) throws SQLException
    {
        return this.delegate.getUnicodeStream(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#getUnicodeStream(java.lang.String)
     */
    public InputStream getUnicodeStream(final String columnLabel) throws SQLException
    {
        return this.delegate.getUnicodeStream(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#getWarnings()
     */
    public SQLWarning getWarnings() throws SQLException
    {
        return this.delegate.getWarnings();
    }

    /**
     * @see java.sql.ResultSet#insertRow()
     */
    public void insertRow() throws SQLException
    {
        this.delegate.insertRow();
    }

    /**
     * @see java.sql.ResultSet#isAfterLast()
     */
    public boolean isAfterLast() throws SQLException
    {
        return this.delegate.isAfterLast();
    }

    /**
     * @see java.sql.ResultSet#isBeforeFirst()
     */
    public boolean isBeforeFirst() throws SQLException
    {
        return this.delegate.isBeforeFirst();
    }

    /**
     * @see java.sql.ResultSet#isClosed()
     */
    public boolean isClosed() throws SQLException
    {
        return this.delegate.isClosed();
    }

    /**
     * @see java.sql.ResultSet#isFirst()
     */
    public boolean isFirst() throws SQLException
    {
        return this.delegate.isFirst();
    }

    /**
     * @see java.sql.ResultSet#isLast()
     */
    public boolean isLast() throws SQLException
    {
        return this.delegate.isLast();
    }

    /**
     * @see java.sql.ResultSet#last()
     */
    public boolean last() throws SQLException
    {
        return this.delegate.last();
    }

    /**
     * @see java.sql.ResultSet#moveToCurrentRow()
     */
    public void moveToCurrentRow() throws SQLException
    {
        this.delegate.moveToCurrentRow();
    }

    /**
     * @see java.sql.ResultSet#moveToInsertRow()
     */
    public void moveToInsertRow() throws SQLException
    {
        this.delegate.moveToInsertRow();
    }

    /**
     * @see java.sql.ResultSet#next()
     */
    public boolean next() throws SQLException
    {
        return this.delegate.next();
    }

    /**
     * @see java.sql.ResultSet#previous()
     */
    public boolean previous() throws SQLException
    {
        return this.delegate.previous();
    }

    /**
     * @see java.sql.ResultSet#refreshRow()
     */
    public void refreshRow() throws SQLException
    {
        this.delegate.refreshRow();
    }

    /**
     * @see java.sql.ResultSet#relative(int)
     */
    public boolean relative(final int rows) throws SQLException
    {
        return this.delegate.relative(rows);
    }

    /**
     * @see java.sql.ResultSet#rowDeleted()
     */
    public boolean rowDeleted() throws SQLException
    {
        return this.delegate.rowDeleted();
    }

    /**
     * @see java.sql.ResultSet#rowInserted()
     */
    public boolean rowInserted() throws SQLException
    {
        return this.delegate.rowInserted();
    }

    /**
     * @see java.sql.ResultSet#rowUpdated()
     */
    public boolean rowUpdated() throws SQLException
    {
        return this.delegate.rowUpdated();
    }

    /**
     * @see java.sql.ResultSet# setFetchDirection(int)
     */
    public void setFetchDirection(final int direction) throws SQLException
    {
        this.delegate.setFetchDirection(direction);
    }

    /**
     * @param rows
     * @throws SQLException
     * @see java.sql.ResultSet# setFetchSize(int)
     */
    public void setFetchSize(final int rows) throws SQLException
    {
        this.delegate.setFetchSize(rows);
    }

    /**
     * @see java.sql.ResultSet#updateArray(int, java.sql.Array)
     */
    public void updateArray(final int columnIndex, final Array x) throws SQLException
    {
        this.delegate.updateArray(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateArray(java.lang.String, java.sql.Array)
     */
    public void updateArray(final String columnLabel, final Array x) throws SQLException
    {
        this.delegate.updateArray(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateAsciiStream(int, java.io.InputStream)
     */
    public void updateAsciiStream(final int columnIndex, final InputStream x) throws SQLException
    {
        this.delegate.updateAsciiStream(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateAsciiStream(java.lang.String,
     * java.io.InputStream)
     */
    public void updateAsciiStream(final String columnLabel, final InputStream x) throws SQLException
    {
        this.delegate.updateAsciiStream(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateAsciiStream(int, java.io.InputStream, int)
     */
    public void updateAsciiStream(final int columnIndex, final InputStream x, final int length) throws SQLException
    {
        this.delegate.updateAsciiStream(columnIndex, x, length);
    }

    /**
     * @see java.sql.ResultSet#updateAsciiStream(java.lang.String,
     * java.io.InputStream, int)
     */
    public void updateAsciiStream(final String columnLabel, final InputStream x, final int length) throws SQLException
    {
        this.delegate.updateAsciiStream(columnLabel, x, length);
    }

    /**
     * @see java.sql.ResultSet#updateAsciiStream(int, java.io.InputStream, long)
     */
    public void updateAsciiStream(final int columnIndex, final InputStream x, final long length) throws SQLException
    {
        this.delegate.updateAsciiStream(columnIndex, x, length);
    }

    /**
     * @see java.sql.ResultSet#updateAsciiStream(java.lang.String,
     * java.io.InputStream, long)
     */
    public void updateAsciiStream(final String columnLabel, final InputStream x, final long length) throws SQLException
    {
        this.delegate.updateAsciiStream(columnLabel, x, length);
    }

    /**
     * @see java.sql.ResultSet#updateBigDecimal(int, java.math.BigDecimal)
     */
    public void updateBigDecimal(final int columnIndex, final BigDecimal x) throws SQLException
    {
        this.delegate.updateBigDecimal(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateBigDecimal(java.lang.String,
     * java.math.BigDecimal)
     */
    public void updateBigDecimal(final String columnLabel, final BigDecimal x) throws SQLException
    {
        this.delegate.updateBigDecimal(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateBinaryStream(int, java.io.InputStream)
     */
    public void updateBinaryStream(final int columnIndex, final InputStream x) throws SQLException
    {
        this.delegate.updateBinaryStream(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateBinaryStream(java.lang.String,
     * java.io.InputStream)
     */
    public void updateBinaryStream(final String columnLabel, final InputStream x) throws SQLException
    {
        this.delegate.updateBinaryStream(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateBinaryStream(int, java.io.InputStream, int)
     */
    public void updateBinaryStream(final int columnIndex, final InputStream x, final int length) throws SQLException
    {
        this.delegate.updateBinaryStream(columnIndex, x, length);
    }

    /**
     * @see java.sql.ResultSet#updateBinaryStream(java.lang.String,
     * java.io.InputStream, int)
     */
    public void updateBinaryStream(final String columnLabel, final InputStream x, final int length) throws SQLException
    {
        this.delegate.updateBinaryStream(columnLabel, x, length);
    }

    /**
     * @see java.sql.ResultSet#updateBinaryStream(int, java.io.InputStream,
     * long)
     */
    public void updateBinaryStream(final int columnIndex, final InputStream x, final long length) throws SQLException
    {
        this.delegate.updateBinaryStream(columnIndex, x, length);
    }

    /**
     * @see java.sql.ResultSet#updateBinaryStream(java.lang.String,
     * java.io.InputStream, long)
     */
    public void updateBinaryStream(final String columnLabel, final InputStream x, final long length)
            throws SQLException
    {
        this.delegate.updateBinaryStream(columnLabel, x, length);
    }

    /**
     * @see java.sql.ResultSet#updateBlob(int, java.sql.Blob)
     */
    public void updateBlob(final int columnIndex, final Blob x) throws SQLException
    {
        this.delegate.updateBlob(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateBlob(java.lang.String, java.sql.Blob)
     */
    public void updateBlob(final String columnLabel, final Blob x) throws SQLException
    {
        this.delegate.updateBlob(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateBlob(int, java.io.InputStream)
     */
    public void updateBlob(final int columnIndex, final InputStream inputStream) throws SQLException
    {
        this.delegate.updateBlob(columnIndex, inputStream);
    }

    /**
     * @see java.sql.ResultSet#updateBlob(java.lang.String, java.io.InputStream)
     */
    public void updateBlob(final String columnLabel, final InputStream inputStream) throws SQLException
    {
        this.delegate.updateBlob(columnLabel, inputStream);
    }

    /**
     * @see java.sql.ResultSet#updateBlob(int, java.io.InputStream, long)
     */
    public void updateBlob(final int columnIndex, final InputStream inputStream, final long length) throws SQLException
    {
        this.delegate.updateBlob(columnIndex, inputStream, length);
    }

    /**
     * @see java.sql.ResultSet#updateBlob(java.lang.String, java.io.InputStream,
     * long)
     */
    public void updateBlob(final String columnLabel, final InputStream inputStream, final long length)
            throws SQLException
    {
        this.delegate.updateBlob(columnLabel, inputStream, length);
    }

    /**
     * @see java.sql.ResultSet#updateBoolean(int, boolean)
     */
    public void updateBoolean(final int columnIndex, final boolean x) throws SQLException
    {
        this.delegate.updateBoolean(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateBoolean(java.lang.String, boolean)
     */
    public void updateBoolean(final String columnLabel, final boolean x) throws SQLException
    {
        this.delegate.updateBoolean(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateByte(int, byte)
     */
    public void updateByte(final int columnIndex, final byte x) throws SQLException
    {
        this.delegate.updateByte(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateByte(java.lang.String, byte)
     */
    public void updateByte(final String columnLabel, final byte x) throws SQLException
    {
        this.delegate.updateByte(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateBytes(int, byte[])
     */
    public void updateBytes(final int columnIndex, final byte[] x) throws SQLException
    {
        this.delegate.updateBytes(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateBytes(java.lang.String, byte[])
     */
    public void updateBytes(final String columnLabel, final byte[] x) throws SQLException
    {
        this.delegate.updateBytes(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateCharacterStream(int, java.io.Reader)
     */
    public void updateCharacterStream(final int columnIndex, final Reader x) throws SQLException
    {
        this.delegate.updateCharacterStream(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateCharacterStream(java.lang.String,
     * java.io.Reader)
     */
    public void updateCharacterStream(final String columnLabel, final Reader reader) throws SQLException
    {
        this.delegate.updateCharacterStream(columnLabel, reader);
    }

    /**
     * @see java.sql.ResultSet#updateCharacterStream(int, java.io.Reader, int)
     */
    public void updateCharacterStream(final int columnIndex, final Reader x, final int length) throws SQLException
    {
        this.delegate.updateCharacterStream(columnIndex, x, length);
    }

    /**
     * @see java.sql.ResultSet#updateCharacterStream(java.lang.String,
     * java.io.Reader, int)
     */
    public void updateCharacterStream(final String columnLabel, final Reader reader, final int length)
            throws SQLException
    {
        this.delegate.updateCharacterStream(columnLabel, reader, length);
    }

    /**
     * @see java.sql.ResultSet#updateCharacterStream(int, java.io.Reader, long)
     */
    public void updateCharacterStream(final int columnIndex, final Reader x, final long length) throws SQLException
    {
        this.delegate.updateCharacterStream(columnIndex, x, length);
    }

    /**
     * @see java.sql.ResultSet#updateCharacterStream(java.lang.String,
     * java.io.Reader, long)
     */
    public void updateCharacterStream(final String columnLabel, final Reader reader, final long length)
            throws SQLException
    {
        this.delegate.updateCharacterStream(columnLabel, reader, length);
    }

    /**
     * @see java.sql.ResultSet#updateClob(int, java.sql.Clob)
     */
    public void updateClob(final int columnIndex, final Clob x) throws SQLException
    {
        this.delegate.updateClob(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateClob(java.lang.String, java.sql.Clob)
     */
    public void updateClob(final String columnLabel, final Clob x) throws SQLException
    {
        this.delegate.updateClob(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateClob(int, java.io.Reader)
     */
    public void updateClob(final int columnIndex, final Reader reader) throws SQLException
    {
        this.delegate.updateClob(columnIndex, reader);
    }

    /**
     * @see java.sql.ResultSet#updateClob(java.lang.String, java.io.Reader)
     */
    public void updateClob(final String columnLabel, final Reader reader) throws SQLException
    {
        this.delegate.updateClob(columnLabel, reader);
    }

    /**
     * @see java.sql.ResultSet#updateClob(int, java.io.Reader, long)
     */
    public void updateClob(final int columnIndex, final Reader reader, final long length) throws SQLException
    {
        this.delegate.updateClob(columnIndex, reader, length);
    }

    /**
     * @see java.sql.ResultSet#updateClob(java.lang.String, java.io.Reader,
     * long)
     */
    public void updateClob(final String columnLabel, final Reader reader, final long length) throws SQLException
    {
        this.delegate.updateClob(columnLabel, reader, length);
    }

    /**
     * @see java.sql.ResultSet#updateDate(int, java.sql.Date)
     */
    public void updateDate(final int columnIndex, final Date x) throws SQLException
    {
        this.delegate.updateDate(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateDate(java.lang.String, java.sql.Date)
     */
    public void updateDate(final String columnLabel, final Date x) throws SQLException
    {
        this.delegate.updateDate(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateDouble(int, double)
     */
    public void updateDouble(final int columnIndex, final double x) throws SQLException
    {
        this.delegate.updateDouble(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateDouble(java.lang.String, double)
     */
    public void updateDouble(final String columnLabel, final double x) throws SQLException
    {
        this.delegate.updateDouble(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateFloat(int, float)
     */
    public void updateFloat(final int columnIndex, final float x) throws SQLException
    {
        this.delegate.updateFloat(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateFloat(java.lang.String, float)
     */
    public void updateFloat(final String columnLabel, final float x) throws SQLException
    {
        this.delegate.updateFloat(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateInt(int, int)
     */
    public void updateInt(final int columnIndex, final int x) throws SQLException
    {
        this.delegate.updateInt(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateInt(java.lang.String, int)
     */
    public void updateInt(final String columnLabel, final int x) throws SQLException
    {
        this.delegate.updateInt(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateLong(int, long)
     */
    public void updateLong(final int columnIndex, final long x) throws SQLException
    {
        this.delegate.updateLong(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateLong(java.lang.String, long)
     */
    public void updateLong(final String columnLabel, final long x) throws SQLException
    {
        this.delegate.updateLong(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateNCharacterStream(int, java.io.Reader)
     */
    public void updateNCharacterStream(final int columnIndex, final Reader x) throws SQLException
    {
        this.delegate.updateNCharacterStream(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateNCharacterStream(java.lang.String,
     * java.io.Reader)
     */
    public void updateNCharacterStream(final String columnLabel, final Reader reader) throws SQLException
    {
        this.delegate.updateNCharacterStream(columnLabel, reader);
    }

    /**
     * @see java.sql.ResultSet#updateNCharacterStream(int, java.io.Reader, long)
     */
    public void updateNCharacterStream(final int columnIndex, final Reader x, final long length) throws SQLException
    {
        this.delegate.updateNCharacterStream(columnIndex, x, length);
    }

    /**
     * @see java.sql.ResultSet#updateNCharacterStream(java.lang.String,
     * java.io.Reader, long)
     */
    public void updateNCharacterStream(final String columnLabel, final Reader reader, final long length)
            throws SQLException
    {
        this.delegate.updateNCharacterStream(columnLabel, reader, length);
    }

    /**
     * @see java.sql.ResultSet#updateNClob(int, java.sql.NClob)
     */
    public void updateNClob(final int columnIndex, final NClob clob) throws SQLException
    {
        this.delegate.updateNClob(columnIndex, clob);
    }

    /**
     * @see java.sql.ResultSet#updateNClob(java.lang.String, java.sql.NClob)
     */
    public void updateNClob(final String columnLabel, final NClob clob) throws SQLException
    {
        this.delegate.updateNClob(columnLabel, clob);
    }

    /**
     * @see java.sql.ResultSet#updateNClob(int, java.io.Reader)
     */
    public void updateNClob(final int columnIndex, final Reader reader) throws SQLException
    {
        this.delegate.updateNClob(columnIndex, reader);
    }

    /**
     * @see java.sql.ResultSet#updateNClob(java.lang.String, java.io.Reader)
     */
    public void updateNClob(final String columnLabel, final Reader reader) throws SQLException
    {
        this.delegate.updateNClob(columnLabel, reader);
    }

    /**
     * @see java.sql.ResultSet#updateNClob(int, java.io.Reader, long)
     */
    public void updateNClob(final int columnIndex, final Reader reader, final long length) throws SQLException
    {
        this.delegate.updateNClob(columnIndex, reader, length);
    }

    /**
     * @see java.sql.ResultSet#updateNClob(java.lang.String, java.io.Reader,
     * long)
     */
    public void updateNClob(final String columnLabel, final Reader reader, final long length) throws SQLException
    {
        this.delegate.updateNClob(columnLabel, reader, length);
    }

    /**
     * @see java.sql.ResultSet#updateNString(int, java.lang.String)
     */
    public void updateNString(final int columnIndex, final String string) throws SQLException
    {
        this.delegate.updateNString(columnIndex, string);
    }

    /**
     * @see java.sql.ResultSet#updateNString(java.lang.String, java.lang.String)
     */
    public void updateNString(final String columnLabel, final String string) throws SQLException
    {
        this.delegate.updateNString(columnLabel, string);
    }

    /**
     * @see java.sql.ResultSet#updateNull(int)
     */
    public void updateNull(final int columnIndex) throws SQLException
    {
        this.delegate.updateNull(columnIndex);
    }

    /**
     * @see java.sql.ResultSet#updateNull(java.lang.String)
     */
    public void updateNull(final String columnLabel) throws SQLException
    {
        this.delegate.updateNull(columnLabel);
    }

    /**
     * @see java.sql.ResultSet#updateObject(int, java.lang.Object)
     */
    public void updateObject(final int columnIndex, final Object x) throws SQLException
    {
        this.delegate.updateObject(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateObject(java.lang.String, java.lang.Object)
     */
    public void updateObject(final String columnLabel, final Object x) throws SQLException
    {
        this.delegate.updateObject(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateObject(int, java.lang.Object, int)
     */
    public void updateObject(final int columnIndex, final Object x, final int scaleOrLength) throws SQLException
    {
        this.delegate.updateObject(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateObject(java.lang.String, java.lang.Object,
     * int)
     */
    public void updateObject(final String columnLabel, final Object x, final int scaleOrLength) throws SQLException
    {
        this.delegate.updateObject(columnLabel, x, scaleOrLength);
    }

    /**
     * @see java.sql.ResultSet#updateRef(int, java.sql.Ref)
     */
    public void updateRef(final int columnIndex, final Ref x) throws SQLException
    {
        this.delegate.updateRef(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateRef(java.lang.String, java.sql.Ref)
     */
    public void updateRef(final String columnLabel, final Ref x) throws SQLException
    {
        this.delegate.updateRef(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateRow()
     */
    public void updateRow() throws SQLException
    {
        this.delegate.updateRow();
    }

    /**
     * @see java.sql.ResultSet#updateRowId(int, java.sql.RowId)
     */
    public void updateRowId(final int columnIndex, final RowId x) throws SQLException
    {
        this.delegate.updateRowId(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateRowId(java.lang.String, java.sql.RowId)
     */
    public void updateRowId(final String columnLabel, final RowId x) throws SQLException
    {
        this.delegate.updateRowId(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateSQLXML(int, java.sql.SQLXML)
     */
    public void updateSQLXML(final int columnIndex, final SQLXML xmlObject) throws SQLException
    {
        this.delegate.updateSQLXML(columnIndex, xmlObject);
    }

    /**
     * @see java.sql.ResultSet#updateSQLXML(java.lang.String, java.sql.SQLXML)
     */
    public void updateSQLXML(final String columnLabel, final SQLXML xmlObject) throws SQLException
    {
        this.delegate.updateSQLXML(columnLabel, xmlObject);
    }

    /**
     * @see java.sql.ResultSet#updateShort(int, short)
     */
    public void updateShort(final int columnIndex, final short x) throws SQLException
    {
        this.delegate.updateShort(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateShort(java.lang.String, short)
     */
    public void updateShort(final String columnLabel, final short x) throws SQLException
    {
        this.delegate.updateShort(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateString(int, java.lang.String)
     */
    public void updateString(final int columnIndex, final String x) throws SQLException
    {
        this.delegate.updateString(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateString(java.lang.String, java.lang.String)
     */
    public void updateString(final String columnLabel, final String x) throws SQLException
    {
        this.delegate.updateString(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateTime(int, java.sql.Time)
     */
    public void updateTime(final int columnIndex, final Time x) throws SQLException
    {
        this.delegate.updateTime(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateTime(java.lang.String, java.sql.Time)
     */
    public void updateTime(final String columnLabel, final Time x) throws SQLException
    {
        this.delegate.updateTime(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#updateTimestamp(int, java.sql.Timestamp)
     */
    public void updateTimestamp(final int columnIndex, final Timestamp x) throws SQLException
    {
        this.delegate.updateTimestamp(columnIndex, x);
    }

    /**
     * @see java.sql.ResultSet#updateTimestamp(java.lang.String,
     * java.sql.Timestamp)
     */
    public void updateTimestamp(final String columnLabel, final Timestamp x) throws SQLException
    {
        this.delegate.updateTimestamp(columnLabel, x);
    }

    /**
     * @see java.sql.ResultSet#wasNull()
     */
    public boolean wasNull() throws SQLException
    {
        return this.delegate.wasNull();
    }

    /**
     * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
     */
    public boolean isWrapperFor(final Class<?> iface) throws SQLException
    {
        return this.delegate.isWrapperFor(iface);
    }

    /**
     * @see java.sql.Wrapper#unwrap(java.lang.Class)
     */
    public <T> T unwrap(final Class<T> iface) throws SQLException
    {
        return this.delegate.unwrap(iface);
    }

}
