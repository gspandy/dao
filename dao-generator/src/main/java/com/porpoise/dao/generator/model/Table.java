package com.porpoise.dao.generator.model;

import java.util.Collection;
import java.util.List;

import com.google.common.base.CaseFormat;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.porpoise.generator.model.FieldType;

public class Table {
	private final String name;
	private final List<Column> columns;
	private Column idColumn;
	private boolean idComputed = false;

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
		if (idColumn == null && !idComputed) {
			idComputed = true;
			final Column pk = computePrimaryKey();
			idColumn = pk;
		}
		return idColumn;
	}

	private Column computePrimaryKey() {
		final String tableNameLowerCase = name.toLowerCase();
		final Collection<Column> probables = Lists.newArrayList();

		for (final Column c : getColumns()) {
			final String colName = c.getName().toLowerCase();
			if (colName.endsWith("_id")) {
				final String idName = colName.substring(0, colName.length()
						- "_id".length());
				if (tableNameLowerCase.startsWith(idName)) {
					final int subsequentUnderscore = tableNameLowerCase
							.indexOf('_', idName.length());
					final boolean tableNameHasSubsequentUnderscore = subsequentUnderscore >= 0;
					if (tableNameHasSubsequentUnderscore) {
						System.out.println(String.format(
								"Ignoring possible PK '%s' for table '%s'",
								colName, tableNameLowerCase));
					} else {
						probables.add(c);
					}
				}
			}
		}
		Column pk;
		if (probables.size() != 1) {
			System.err.println("No single possible ID suggestion found for "
					+ name);
			pk = null;
		} else {
			pk = Iterables.getOnlyElement(probables);
		}
		return pk;
	}

	public String getJavaName() {
		return CaseFormat.UPPER_UNDERSCORE
				.to(CaseFormat.UPPER_CAMEL, this.name);
	}

	public String getTableName() {
		return this.name;
	}

	public Column addKeyColumn(final String colName, final boolean required,
			final FieldType colType) {
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
			final FieldType colType) {
		return addColumn(new Column(this, colName, required, colType));
	}

	/**
	 * @return true if this table has any columns defined
	 */
	public boolean hasColumns() {
		return !this.columns.isEmpty();
	}

	public Collection<Reference> getReferencesToThisTable() {
		final Collection<Reference> references = Lists.newArrayList();
		for (final Column c : columns) {
			references.addAll(c.getReferencingColumns());
		}

		return references;
	}

	public Collection<Reference> getForeignKeyReferences() {
		final Collection<Reference> references = Lists.newArrayList();
		for (final Column c : columns) {
			references.addAll(c.getForeignKeyReferences());
		}

		return references;
	}

	/**
	 * This table is a join table if it only consists of two columns, each of
	 * which is a FK to another table
	 * 
	 * @return
	 */
	public boolean isJoinTable() {
		if (columns.size() != 2) {
			return false;
		}
		for (final Column c : columns) {
			if (!isRefCol(c)) {
				return false;
			}
		}
		return true;
	}

	protected boolean isRefCol(final Column col) {
		if (col.isPrimaryKey()) {
			return false;
		}
		if (!col.hasFkReferences()) {
			return false;
		}
		return true;
	}

	/**
	 * @return true if there are any FK references
	 */
	public boolean hasForeignKeyReferences() {
		return !getForeignKeyReferences().isEmpty();
	}

	/**
	 * If this table is a join table, then we assume it has two FK fields, in
	 * addition to perhaps some optional 'created' data fields
	 * 
	 * @param from
	 * @return
	 */
	public Column findOtherFkField(final Column from) {
		if (!isJoinTable()) {
			throw new IllegalStateException("this is not a join table");
		}

		boolean foundFirstFkColumn = false;
		Column otherFk = null;
		for (final Column col : getColumns()) {
			if (col == from) {
				foundFirstFkColumn = true;
			} else {
				if (!col.isDate()) {
					otherFk = col;
				}
			}
		}
		if (!foundFirstFkColumn) {
			throw new IllegalStateException(
					"The first FK field was not found in the table!");
		}
		if (otherFk == null) {
			throw new IllegalStateException(
					"Couldn't find the other fk field for " + from);
		}

		return otherFk;
	}

	@Override
	public String toString() {
		final Iterable<?> names = Collections2.transform(this.columns,
				new Function<Column, String>() {

					@Override
					public String apply(final Column from) {
						return from.getName();
					}
				});
		final String cols = Joiner.on(",").join(names);
		return String.format("%s [%s]", this.name, cols);
	}
}
