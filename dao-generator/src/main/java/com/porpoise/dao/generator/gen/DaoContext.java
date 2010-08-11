package com.porpoise.dao.generator.gen;

import java.util.Collection;
import java.util.Iterator;

import com.google.common.base.CaseFormat;
import com.porpoise.dao.generator.model.Column;
import com.porpoise.dao.generator.model.Reference;
import com.porpoise.dao.generator.model.Table;
import com.porpoise.generator.AbstractJavaContext;

public class DaoContext extends AbstractJavaContext {

	private final Table table;

	private static interface Visitor {
		void onColumn(Column c, boolean hasNext);
	}

	private static abstract class BufferVisitor implements Visitor {
		private final StringBuilder buffer = new StringBuilder();

		BufferVisitor(final String value) {
			buffer.append(value);
		}

		@Override
		public String toString() {
			return buffer.toString();
		}

		public StringBuilder append(final Object str) {
			buffer.append(str);
			return buffer;
		}
	}

	private static abstract class CommasSeparatedBufferVisitor extends
			BufferVisitor {
		CommasSeparatedBufferVisitor() {
			this("");
		}

		CommasSeparatedBufferVisitor(final String value) {
			super(value);
		}

		@Override
		public final void onColumn(final Column c, final boolean hasNext) {
			onColumn(c);
			if (hasNext) {
				append(", ");
			}
		}

		protected abstract void onColumn(Column c);
	}

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

	private <T extends Visitor> T traverse(final T visitor) {
		for (final Iterator<Column> iter = this.table.getColumns().iterator(); iter
				.hasNext();) {
			final Column c = iter.next();
			visitor.onColumn(c, iter.hasNext());
		}
		return visitor;
	}

	public String getColumnDeclarations() {

		return traverse(new CommasSeparatedBufferVisitor() {
			@Override
			protected void onColumn(final Column c) {
				append("final ").append(c.getJavaTypeName()).append(" ")
						.append(c.getNameAsProperty());
			}
		}).toString();
	}

	public String getColumnAccessorMethods(final String varName) {

		return traverse(new CommasSeparatedBufferVisitor() {
			@Override
			protected void onColumn(final Column c) {
				append(varName).append(".").append(c.getNameAsAccessor())
						.append("()");
			}
		}).toString();
	}

	public String getColumnParameterList() {
		return traverse(new CommasSeparatedBufferVisitor() {
			@Override
			protected void onColumn(final Column c) {
				append(c.getNameAsProperty());
			}
		}).toString();
	}

	public String getColumnParameterListAsToString() {
		return traverse(new CommasSeparatedBufferVisitor() {
			@Override
			protected void onColumn(final Column c) {
				append(c.getNameAsProperty()).append("=%s");
			}
		}).toString();
	}

	public String getColumnNames() {
		return traverse(new CommasSeparatedBufferVisitor() {
			@Override
			protected void onColumn(final Column c) {
				append(c.getName());
			}
		}).toString();
	}

	public String getTestValues() {
		return traverse(new CommasSeparatedBufferVisitor() {
			@Override
			protected void onColumn(final Column c) {
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
			protected void onColumn(final Column c) {
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
			String string = String
					.format("\"%s\"", c.getName().substring(0, 3));
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
