package com.porpoise.dao.generator.gen;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.porpoise.dao.database.DbConnectionFactory;
import com.porpoise.dao.database.visitors.MultipleResultToMapVisitor;
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

}
