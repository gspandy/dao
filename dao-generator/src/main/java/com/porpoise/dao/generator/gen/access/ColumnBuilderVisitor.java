package com.porpoise.dao.generator.gen.access;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.porpoise.dao.database.IResultSetVisitor;
import com.porpoise.dao.generator.model.ColType;
import com.porpoise.dao.generator.model.Column;
import com.porpoise.dao.generator.model.Table;

final class ColumnBuilderVisitor implements IResultSetVisitor {
	private final String tableName;
	private final Table table;

	ColumnBuilderVisitor(final String tableName) {
		this.tableName = tableName;
		this.table = new Table(tableName.toUpperCase()) {

			@Override
			public Column getIdColumn() {
				if (getTableName().endsWith("_REF")) {
					return null;
				}
				return super.getIdColumn();
			};
		};
	}

	@Override
	public boolean onResultSet(final ResultSet resultSet) throws SQLException {
		final ResultSetMetaData metaData = resultSet.getMetaData();
		final int size = metaData.getColumnCount();
		for (int column = 1; column < size; column++) {
			final String label = metaData.getColumnLabel(column);
			final String name = metaData.getColumnName(column);
			final String className = metaData.getColumnClassName(column);
			final String columnTypeName = metaData.getColumnTypeName(column);

			try {
				final Class<?> c1ass = Class.forName(className);
				final ColType colType;
				if (columnTypeName.equals("LONGBINARY")) {
					colType = ColType.Bytes;
				} else {
					colType = ColType.forClass(c1ass);
				}
				table.addColumn(label, false, colType);
			} catch (final Exception e) {
				throw new RuntimeException(
						String.format(
								"invalid column class '%s' for column '%s' in table '%s'",
								className, name, tableName));
			}
		}
		return false;
	}

	@Override
	public void onNoResults() {
		System.err.println("No data for " + tableName);
	}

	public String getSql() {
		return "SELECT * FROM " + tableName;
	}

	public Table getTable() {
		if (this.table.hasColumns()) {
			return this.table;
		}
		return null;
	}
}