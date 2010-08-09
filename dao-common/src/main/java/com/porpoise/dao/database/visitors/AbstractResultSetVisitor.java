/**
 * AbstractResultSetVisitor.java

 */
package com.porpoise.dao.database.visitors;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.porpoise.dao.database.DbException;
import com.porpoise.dao.database.IResultSetVisitor;

/**
 * AbstractResultSetVisitor
 * 
 * created: Jul 27, 2010
 * 
 * @author Aaron
 * 
 * 
 *         A base class for result set visitors which require at least one
 *         result
 */
public abstract class AbstractResultSetVisitor implements IResultSetVisitor {
	/**
	 * @see IResultSetVisitor#onNoResults()
	 */
	@Override
	public void onNoResults() {
		// no op
	}

	/**
	 * @see IResultSetVisitor#onResultSet(java.sql.ResultSet)
	 */
	@Override
	public boolean onResultSet(final ResultSet resultSet) throws SQLException {
		return true;
	}

	/**
	 * get a nullable boolean value from the result set at the given column
	 * index
	 * 
	 * @param resultSet
	 *            The result set to evaluate
	 * @param columnIndex
	 *            the column to return
	 * 
	 * @return the boolean value for the given result set column or null
	 */
	public static final Boolean getBoolean(final ResultSet resultSet,
			final int columnIndex) {
		try {
			final boolean value = resultSet.getBoolean(columnIndex);
			if (resultSet.wasNull()) {
				return null;
			}
			return Boolean.valueOf(value);
		} catch (final SQLException e) {
			throw new DbException(e);
		}
	}

	/**
	 * get a nullable string value from the result set at the given column index
	 * 
	 * @param resultSet
	 *            The result set to evaluate
	 * @param columnIndex
	 *            the column to return
	 * 
	 * 
	 * @return the string value for the given result set column or null
	 */
	public static final String getString(final ResultSet resultSet,
			final int columnIndex) {
		try {
			final String value = resultSet.getString(columnIndex);
			if (resultSet.wasNull()) {
				return null;
			}
			return value;
		} catch (final SQLException e) {
			throw new DbException(e);
		}
	}

	/**
	 * get a nullable bytes value from the result set at the given column index
	 * 
	 * @param resultSet
	 *            The result set to evaluate
	 * @param columnIndex
	 *            the column to return
	 * 
	 * 
	 * @return the string value for the given result set column or null
	 */
	public static final byte[] getBytes(final ResultSet resultSet,
			final int columnIndex) {
		try {
			final byte[] value = resultSet.getBytes(columnIndex);
			if (resultSet.wasNull()) {
				return null;
			}
			return value;
		} catch (final SQLException e) {
			throw new DbException(e);
		}
	}

	/**
	 * get a nullable Long value from the result set at the given column index
	 * 
	 * @param resultSet
	 *            The result set to evaluate
	 * @param columnIndex
	 *            the column to return
	 * 
	 * @return the long value for the given result set column or null
	 */
	public static final Long getLong(final ResultSet resultSet,
			final int columnIndex) {
		try {
			final long value = resultSet.getLong(columnIndex);
			if (resultSet.wasNull()) {
				return null;
			}
			return Long.valueOf(value);
		} catch (final SQLException e) {
			throw new DbException(e);
		}
	}

	/**
	 * get a nullable Integer value from the result set at the given column
	 * index
	 * 
	 * @param resultSet
	 *            The result set to evaluate
	 * @param columnIndex
	 *            the column to return
	 * 
	 * @return the integer value for the given result set column or null
	 */
	public static final Integer getInteger(final ResultSet resultSet,
			final int columnIndex) {
		try {
			final int value = resultSet.getInt(columnIndex);
			if (resultSet.wasNull()) {
				return null;
			}
			return Integer.valueOf(value);
		} catch (final SQLException e) {
			throw new DbException(e);
		}
	}

	/**
	 * get a nullable Short value from the result set at the given column index
	 * 
	 * @param resultSet
	 *            The result set to evaluate
	 * @param columnIndex
	 *            the column to return
	 * 
	 * @return the short value for the given result set column or null
	 */
	public static final Short getShort(final ResultSet resultSet,
			final int columnIndex) {
		try {
			final short value = resultSet.getShort(columnIndex);
			if (resultSet.wasNull()) {
				return null;
			}
			return Short.valueOf(value);
		} catch (final SQLException e) {
			throw new DbException(e);
		}
	}

	/**
	 * get a nullable BigDecimal value from the result set at the given column
	 * index
	 * 
	 * @param resultSet
	 *            The result set to evaluate
	 * @param columnIndex
	 *            the column to return
	 * 
	 * @return the BigDecimal value for the given result set column or null
	 */
	public static final BigDecimal getBigDecimal(final ResultSet resultSet,
			final int columnIndex) {
		try {
			final BigDecimal value = resultSet.getBigDecimal(columnIndex);
			if (resultSet.wasNull()) {
				return null;
			}
			return value;
		} catch (final SQLException e) {
			throw new DbException(e);
		}
	}

	/**
	 * Get a nullable Date value from the result set at the given column index.
	 * The time fields for the returned date instance will be set to the default
	 * values.
	 * 
	 * @param resultSet
	 *            The result set to evaluate
	 * @param columnIndex
	 *            the column to return
	 * 
	 * @return the date value for the given result set column or null
	 */
	public static final Date getDate(final ResultSet resultSet,
			final int columnIndex) {
		try {
			final Date value = resultSet.getDate(columnIndex);
			if (resultSet.wasNull()) {
				return null;
			}
			return value;
		} catch (final SQLException e) {
			throw new DbException(e);
		}
	}

	/**
	 * get a nullable Date value from the result set at the given column index
	 * 
	 * @param resultSet
	 *            The result set to evaluate
	 * @param columnIndex
	 *            the column to return
	 * 
	 * @return the date value for the given result set column or null
	 */
	public static final Date getTime(final ResultSet resultSet,
			final int columnIndex) {
		try {
			final Date value = resultSet.getTimestamp(columnIndex);
			if (resultSet.wasNull()) {
				return null;
			}
			return value;
		} catch (final SQLException e) {
			throw new DbException(e);
		}
	}

	/**
	 * get a nullable Date value from the result set at the given column index
	 * 
	 * @param resultSet
	 *            The result set to evaluate
	 * @param columnIndex
	 *            the column to return
	 * 
	 * @return the date value for the given result set column or null
	 */
	public static final Date getDateFromTimestamp(final ResultSet resultSet,
			final int columnIndex) {
		final Long timestamp = getLong(resultSet, columnIndex);
		if (timestamp == null) {
			return null;
		}
		return new Date(timestamp.longValue());
	}
}