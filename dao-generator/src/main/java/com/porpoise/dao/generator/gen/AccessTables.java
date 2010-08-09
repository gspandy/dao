package com.porpoise.dao.generator.gen;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.porpoise.dao.database.DbConnectionFactory;
import com.porpoise.dao.database.IResultSetVisitor;
import com.porpoise.dao.database.visitors.MultipleResultToMapVisitor;
import com.porpoise.dao.generator.model.ColType;
import com.porpoise.dao.generator.model.Table;

public enum AccessTables
{
	;// uninstantiable

	private static final class ColumnBuilderVisitor implements IResultSetVisitor
	{
		private final String tableName;
		private final Table table;

		private ColumnBuilderVisitor(final String tableName, final Table table)
		{
			this.tableName = tableName;
			this.table = table;
		}

		@Override
		public boolean onResultSet(final ResultSet resultSet) throws SQLException
		{
			final ResultSetMetaData metaData = resultSet.getMetaData();
			final int size = metaData.getColumnCount();
			for (int column = 1; column < size; column++)
			{
				final String label = metaData.getColumnLabel(column);
				final String name = metaData.getColumnName(column);
				final int t = metaData.getColumnType(column);
				final String className = metaData.getColumnClassName(column);
				final String columnTypeName = metaData.getColumnTypeName(column);

				try
				{
					final Class<?> c1ass = Class.forName(className);
					final ColType colType;
					if (columnTypeName.equals("LONGBINARY"))
					{
						colType = ColType.Bytes;
					}
					else
						colType = ColType.forClass(c1ass);
					table.addColumn(label, false, colType);
				}
				catch (final Exception e)
				{
					throw new RuntimeException(String.format("invalid column class '%s' for column '%s' in table '%s'",
							className, name, tableName));
				}
			}
			return false;
		}

		@Override
		public void onNoResults()
		{
			System.err.println("No data for " + tableName);
		}

		public String getSql()
		{
			return "SELECT * FROM " + tableName;
		}
	}

	static Collection<String> listTables(final DbConnectionFactory f)
	{
		final MultipleResultToMapVisitor visitor = f.executeQueryInSingleTransaction(new MultipleResultToMapVisitor(),
				"SELECT Name from MSysObjects where Type=1");

		final List<Map<String, Object>> ts = visitor.getRows();
		final List<String> tableNames = Lists.transform(ts, new Function<Map<String, Object>, String>()
		{
			@Override
			public String apply(final Map<String, Object> from)
			{
				final Object name = from.get("Name");
				return name.toString();
			}
		});
		final Collection<String> filtered = Collections2.filter(tableNames, new Predicate<String>()
		{
			@Override
			public boolean apply(final String input)
			{
				final String upperCase = input.toUpperCase();
				final boolean disallow = upperCase.endsWith("DELETED") || upperCase.startsWith("MSYS");
				return !disallow;
			}
		});
		return filtered;
	}

	public static Collection<Table> getTables(final DbConnectionFactory f)
	{
		final Collection<String> tableNames = listTables(f);
		final Collection<Table> tables = Lists.newArrayListWithExpectedSize(tableNames.size());
		for (final String tableName : tableNames)
		{
			final Table table = new Table(tableName.toUpperCase());
			tables.add(table);

			final ColumnBuilderVisitor visitor = new ColumnBuilderVisitor(tableName, table);
			f.executeQueryInSingleTransaction(visitor, visitor.getSql());
		}
		return tables;
	}
}
