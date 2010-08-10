package com.porpoise.dao.generator.model;

import java.util.Collection;
import java.util.List;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class Table {
	private final String name;
	private final List<Column> columns;
	private final List<Column> oneToMany = Lists.newArrayList();
	private Column idColumn;

	public Table(final String n) {
		this.name = n;
		this.columns = Lists.newArrayList();
	}

	private Column addColumn(final Column c) {
		if (getColumnByName(c.getName()) != null) {
			throw new IllegalArgumentException(String.format(
					"A column with name '%s' is already defined", c.getName()));
		}

		this.columns.add(c);
		return c;
	}

	public Column getColumnByName(final String colName) {
		for (final Column c : this.columns) {
			if (c.getName().equals(colName)) {
				return c;
			}
		}
		return null;
	}

	public Iterable<Column> getColumns() {
		return ImmutableList.copyOf(this.columns);
	}

	public boolean hasIdColumn() {
		return getIdColumn() != null;
	}

	public Column getIdColumn() {
		if (idColumn == null) {
			final String nameLowerCase = name.toLowerCase();
			final Collection<Column> probables = Lists.newArrayList();
			for (final Column c : getColumns()) {
				final String colName = c.getName().toLowerCase();
				if (colName.endsWith("_id")) {
					final String idName = colName.substring(0, "_id".length());
					if (nameLowerCase.startsWith(idName)) {
						probables.add(c);
					}
				}
			}
			if (probables.size() != 1) {
				System.err
						.println("No single possible ID suggestion found for "
								+ name);
			}
			idColumn = Iterables.getOnlyElement(probables);
		}
		return idColumn;
	}

	public String getJavaName() {
		return CaseFormat.UPPER_UNDERSCORE
				.to(CaseFormat.UPPER_CAMEL, this.name);
	}

	public String getTableName() {
		return this.name;
	}

	public void oneToMany(final Column fk) {
		this.oneToMany.add(fk);
	}

	public Column addKeyColumn(final String colName, final boolean required,
			final ColType colType) {
		if (idColumn != null) {
			throw new IllegalStateException(
					String.format(
							"The id column '%s' has already been defined -- only one primary key is currently supported per table",
							idColumn));
		}
		final Column keyColumn = addColumn(new Column(this, colName, required,
				colType));
		idColumn = keyColumn;
		return keyColumn;
	}

	public Column addColumn(final String colName, final boolean required,
			final ColType colType) {
		return addColumn(new Column(this, colName, required, colType));
	}

	/**
	 * @return true if this table has any columns defined
	 */
	public boolean hasColumns() {
		return !this.columns.isEmpty();
	}
}
