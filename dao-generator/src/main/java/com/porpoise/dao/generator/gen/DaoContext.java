package com.porpoise.dao.generator.gen;

import java.util.Collection;
import java.util.Iterator;

import com.google.common.base.CaseFormat;
import com.porpoise.dao.generator.model.Column;
import com.porpoise.dao.generator.model.Reference;
import com.porpoise.dao.generator.model.Table;
import com.porpoise.generator.AbstractJavaContext;
import com.porpoise.generator.model.IField;

public class DaoContext extends AbstractJavaContext {

	private final Table table;

	public DaoContext(final String packageName, final Table t) {
		super(packageName);
		this.table = t;

	}

	/**
	 * @return the table
	 */
	public Table getTable() {
		return this.table;
	}

	public boolean hasIdField() {
		return getIdField() != null;
	}

	public Column getIdField() {
		return this.table.getIdColumn();
	}

	@Override
	public String getJavaName() {
		return this.table.getJavaName();
	}

	public String getTableName() {
		return this.table.getTableName();
	}

	@Override
	protected Iterator<Column> getFields() {
		return this.table.getColumns().iterator();
	}

	public String getTestValues() {
		return traverse(new CommasSeparatedBufferVisitor() {
			@Override
			protected void onField(final IField f) {
				final Column c = (Column) f;
				if (c.isPrimaryKey()) {
					append("id");
				} else {
					append(newTestValue(c, true));
				}
			}
		}).toString();
	}

	public String getOtherTestValues() {
		return traverse(new CommasSeparatedBufferVisitor() {
			@Override
			protected void onField(final IField f) {
				final Column c = (Column) f;
				if (c.isPrimaryKey()) {
					append("id");
				} else {
					append(newTestValue(c, false));
				}
			}
		}).toString();
	}

	private String newTestValue(final Column c, final boolean firstValue) {
		final String seed = firstValue ? "1" : "2";
		switch (c.getType()) {
		case BigDecimal: {
			return String.format("Integer.valueOf(%s)", seed);
		}
		case String:
		case Text:
			String string = String.format("\"%s\"",
					c.getJavaName().substring(0, 3));
			if (!firstValue) {
				string = new StringBuilder(string).reverse().toString();
			}
			return string;
		case Boolean:
			return firstValue ? "Boolean.TRUE" : "Boolean.FALSE";
		case Long:
			return String.format("Long.valueOf(%s)", seed);
		case Integer:
			return String.format("Integer.valueOf(%s)", seed);
		case Short:
			return String.format("Short.valueOf((short) %s)", seed);
		case Bytes:
			return firstValue ? "new byte[0]" : "new byte[1]";
		case Timestamp:
		case Date:
			return firstValue ? "new Date()" : "new Date(100000)";
		}
		throw new IllegalArgumentException(String.format(
				"Unknown column type for %s: %s", c.toString(), c.getType()));
	}

	public Iterable<Column> getColumns() {
		return this.table.getColumns();
	}

	public Collection<Reference> getReferencesToThisTable() {
		return table.getReferencesToThisTable();
	}

	public String asProperty(final String name) {
		return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,
				name.toUpperCase());
	}

	public String getReferenceName(final Reference r) {
		return r.getFrom().getTable().getJavaName() + "Id";
	}

}
