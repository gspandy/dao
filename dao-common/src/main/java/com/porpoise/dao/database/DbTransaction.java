/**
 * DbTransaction.java
 */
package com.porpoise.dao.database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * DbTransaction An implementation of IDbTransaction
 */
final class DbTransaction implements IDbTransaction {
	private Connection conn;
	private final DbConnectionFactory connectionFactory;

	DbTransaction(final DbConnectionFactory factory, final Connection connection) {
		this.connectionFactory = factory;
		this.conn = connection;
	}

	@Override
	public <T extends IResultSetVisitor> T executeQuery(final T visitor,
			final String sql, final Object... params) {
		PreparedStatement statement = null;
		try {
			statement = prepareStatement(sql, params);

			final ResultSet resultSet = statement.executeQuery();

			final ResultSet proxiedResultSet = proxyResultSet(resultSet);

			if (!resultSet.next()) {
				visitor.onNoResults();
			} else {
				boolean continueLoop;
				do {
					continueLoop = visitor.onResultSet(proxiedResultSet);
				} while (continueLoop && resultSet.next());
			}

		} catch (final SQLException exp) {
			throw new DbException(exp);
		} finally {
			close(statement);
		}
		return visitor;
	}

	private ResultSet proxyResultSet(final ResultSet resultSet) {
		return new ResultSetDelegate(resultSet) {
			@Override
			public boolean next() {
				throw new UnsupportedOperationException(
						"visitors are not allowed to call next");
			}
		};
	}

	/**
	 * @param sql
	 * @param params
	 */
	@Override
	public void executeUpdate(final String sql, final Object... params) {
		PreparedStatement statement = null;
		try {
			statement = prepareStatement(sql, params);
			statement.execute();
		} catch (final SQLException exp) {
			throw new DbException(exp);
		} finally {
			close(statement);
		}
	}

	private PreparedStatement prepareStatement(final String sql,
			final Object... params) {
		PreparedStatement statement = null;
		try {
			statement = getConn().prepareStatement(sql);
			if (params != null) {
				int parameterIndex = 1;
				for (final Object param : params) {
					if (param instanceof Null) {
						final Null nullValue = (Null) param;
						statement
								.setNull(parameterIndex++, nullValue.getType());
					} else if (param instanceof Long) {
						statement.setLong(parameterIndex++,
								((Long) param).longValue());
					} else if (param instanceof Integer) {
						statement.setInt(parameterIndex++,
								((Integer) param).intValue());
					} else if (param instanceof String) {
						statement.setString(parameterIndex++, ((String) param));
					} else if (param instanceof BigDecimal) {
						statement.setBigDecimal(parameterIndex++,
								((BigDecimal) param));
					} else if (param instanceof byte[]) {
						statement.setBytes(parameterIndex++, ((byte[]) param));
					} else if (param instanceof Date) {
						statement.setTimestamp(parameterIndex++, new Timestamp(
								((Date) param).getTime()));
					} else if (param instanceof Timestamp) {
						statement.setTimestamp(parameterIndex++,
								((Timestamp) param));
					} else if (param instanceof Boolean) {
						statement.setBoolean(parameterIndex++,
								((Boolean) param));
					} else {
						statement.setObject(parameterIndex++, param);
					}
				}
			}
		} catch (final SQLException exp) {
			throw new DbException(exp);
		}
		return statement;
	}

	private Connection getConn() {
		if (this.conn == null) {
			throw new IllegalStateException("the connection has been closed");
		}
		return this.conn;
	}

	static void close(final PreparedStatement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (final SQLException e) {
				throw new DbException(e);
			}
		}

	}

	@Override
	public void commit() {
		try {
			getConn().commit();
		} catch (final SQLException e) {
			throw new DbException(e);
		}
	}

	@Override
	public void rollback() {
		try {
			getConn().rollback();
		} catch (final SQLException e) {
			throw new DbException(e);
		}
	}

	/**
	 * close the transaction
	 * 
	 * @throws DbException
	 */
	@Override
	public void close() throws DbException {
		this.connectionFactory.close(this);
		closeInternal();
	}

	void closeInternal() throws DbException {
		if (this.conn == null) {
			return;
		}

		try {
			rollback();
		} finally {
			// try
			// {
			// this.conn.close();
			// }
			// catch (final SQLException e)
			// {
			// throw new DbException(e);
			// }
			// finally
			// {
			// }
			this.conn = null;
		}
	}
}
