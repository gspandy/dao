package com.porpoise.dao.generator.gen.access;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.porpoise.dao.database.DbConnectionFactory;
import com.porpoise.dao.database.visitors.MultipleResultToMapVisitor;
import com.porpoise.dao.generator.model.Column;
import com.porpoise.dao.generator.model.Table;

/**
 * Utility for reading metadata from MS Access databases
 * 
 * @author Aaron
 * 
 */
public enum AccessTables {
	;// uninstantiable

	static Collection<String> listTables(final DbConnectionFactory f) {
		final MultipleResultToMapVisitor visitor = f
				.executeQueryInSingleTransaction(
						new MultipleResultToMapVisitor(),
						"SELECT Name from MSysObjects where Type=1");

		final List<Map<String, Object>> ts = visitor.getRows();
		final List<String> tableNames = Lists.transform(ts,
				new Function<Map<String, Object>, String>() {
					@Override
					public String apply(final Map<String, Object> from) {
						final Object name = from.get("Name");
						return name.toString();
					}
				});
		final Collection<String> filtered = Collections2.filter(tableNames,
				new Predicate<String>() {
					@Override
					public boolean apply(final String input) {
						final String upperCase = input.toUpperCase();
						final boolean disallow = upperCase.endsWith("DELETED")
								|| upperCase.startsWith("MSYS");
						return !disallow;
					}
				});
		return filtered;
	}

	public static Collection<Table> getTables(final DbConnectionFactory f) {
		final Collection<Table> tables = getRawTables(f);

		inferJoins(tables);

		return tables;
	}

	private static Collection<Table> getRawTables(final DbConnectionFactory f) {
		final Collection<String> tableNames = listTables(f);
		final Collection<Table> tables = Lists
				.newArrayListWithExpectedSize(tableNames.size());
		for (final String tableName : tableNames) {
			final ColumnBuilderVisitor visitor = new ColumnBuilderVisitor(
					tableName);
			f.executeQueryInSingleTransaction(visitor, visitor.getSql());
			final Table table = visitor.getTable();
			if (table != null) {
				tables.add(table);
			}
		}
		return tables;
	}

	/**
	 * assume relationships between tables based on naming conventions, such as:
	 * 
	 * <pre>
	 * TABLE ORDER (ORDER_ID, CREATED_ON)
	 * 
	 * TABLE ORDER_LINE(ORDER_LINE_ID, ORDER_ID, ITEM_NAME, AMOUNT)
	 * </pre>
	 * 
	 * If we infer that ORDER_ID is the PK of the ORDER table, and we see that
	 * ORDER_LINE has a column with the same name, then we infer that ORDER_LINE
	 * references the ORDER table
	 * 
	 * @param tables
	 */
	public static void inferJoins(final Collection<Table> tables) {
		final Multimap<String, Column> filteredColumnsByName = findMultipleIdColumns(tables);

		for (final Entry<String, Collection<Column>> entry : filteredColumnsByName
				.asMap().entrySet()) {

			final Collection<Column> columns = entry.getValue();
			final Column primaryKey = findPrimaryKey(columns);

			int pkCount = 0;
			for (final Column col : columns) {
				if (!col.isPrimaryKey()) {
					col.fkReferenceTo(primaryKey);
				} else {
					if (pkCount > 0) {
						throw new IllegalStateException("multiple PKs found "
								+ primaryKey);
					}
					pkCount++;
				}
			}
		}

	}

	private static Column findPrimaryKey(final Collection<Column> columns) {
		Column primaryKey = null;
		for (final Column col : columns) {
			if (col.isPrimaryKey()) {
				if (primaryKey != null) {
					throw new IllegalStateException(String.format(
							"Multiple primary keys found: %s and %s",
							primaryKey, col));
				} else {
					primaryKey = col;
				}
			}
		}
		return primaryKey;
	}

	private static Multimap<String, Column> findMultipleIdColumns(
			final Collection<Table> tables) {
		final Multimap<String, Column> columnsByName = ArrayListMultimap
				.create();

		for (final Table t : tables) {
			for (final Column c : t.getColumns()) {
				columnsByName.put(c.getName().toUpperCase(), c);
			}
		}

		// we're only interested in matching columns - filter out singles
		final Multimap<String, Column> filteredColumnsByName = ArrayListMultimap
				.create();
		for (final Entry<String, Collection<Column>> entry : columnsByName
				.asMap().entrySet()) {
			if (entry.getValue().size() > 1) {
				if (entry.getKey().endsWith("ID")) {
					filteredColumnsByName.putAll(entry.getKey(),
							entry.getValue());
				} else {
					System.out
							.println("PK search is ignoring multiple columns with names: "
									+ entry.getKey());
				}
			}
		}
		return filteredColumnsByName;
	}

}
