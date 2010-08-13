package com.porpoise.generator;

import java.util.Iterator;

import com.google.common.base.Preconditions;
import com.porpoise.generator.model.ICardinalitySupplier;
import com.porpoise.generator.model.IField;

public abstract class AbstractJavaContext {

	public static interface Visitor {
		void onField(IField c, boolean hasNext);
	}

	public static abstract class BufferVisitor implements Visitor {
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

		protected int length() {
			return buffer.length();
		}
	}

	public static String getDeclarationForField(final IField field) {
		final StringBuilder decl = new StringBuilder();
		boolean isList = false;
		if (field instanceof ICardinalitySupplier) {
			final ICardinalitySupplier cs = (ICardinalitySupplier) field;
			isList = cs.getCardinality().isList();
		}

		decl.append("final ");
		if (isList) {
			decl.append("Collection<");
		}
		if (field.isByteArray()) {
			decl.append("byte[]");
		} else {
			decl.append(field.getJavaTypeName());
		}
		if (isList) {
			decl.append(">");
		}
		decl.append(" ").append(field.getNameAsProperty());

		final String declaration = decl.toString();
		return declaration;
	}

	public String getDeclarationDefinitions(final String varPrefix,
			final Iterable<? extends IField> fields) {
		final StringBuilder b = new StringBuilder();
		final String delim = String.format("%n");

		for (final IField f : fields) {
			b.append(getDeclarationForField(f)).append(delim);
		}

		return b.toString();
	}

	public static abstract class CommasSeparatedBufferVisitor extends
			BufferVisitor {
		private static final String DELIM = ", ";

		public CommasSeparatedBufferVisitor() {
			this("");
		}

		public CommasSeparatedBufferVisitor(final String value) {
			super(value);
		}

		@Override
		public String toString() {
			final String str = super.toString();
			if (str.endsWith(DELIM)) {
				return str.substring(0, str.length() - DELIM.length());
			}
			return str;
		}

		@Override
		public final void onField(final IField fld, final boolean hasNext) {

			final int b4 = length();
			onField(fld);
			final int after = length();
			if (hasNext && after > b4) {
				append(DELIM);
			}
		}

		protected abstract void onField(final IField fld);
	}

	private final String packageName;

	protected <T extends Visitor> T traverse(final T visitor) {
		for (final Iterator<? extends IField> iter = getFields().iterator(); iter
				.hasNext();) {
			final IField field = iter.next();
			visitor.onField(field, iter.hasNext());
		}
		return visitor;
	}

	protected abstract Iterable<? extends IField> getFields();

	public String getDeclarations() {

		return traverse(new CommasSeparatedBufferVisitor() {
			@Override
			protected void onField(final IField c) {
				append("final ").append(c.getJavaTypeName()).append(" ")
						.append(c.getNameAsProperty());
			}
		}).toString();
	}

	public String getParameterListAsToString() {
		return traverse(new CommasSeparatedBufferVisitor() {
			@Override
			protected void onField(final IField c) {
				append(c.getNameAsProperty()).append("=%s");
			}
		}).toString();
	}

	public String getAccessorMethods(final String varName) {

		return traverse(new CommasSeparatedBufferVisitor() {
			@Override
			protected void onField(final IField c) {
				append(varName).append(".").append(c.getNameAsAccessor())
						.append("()");
			}
		}).toString();
	}

	public String getParameterList() {
		return traverse(new CommasSeparatedBufferVisitor() {
			@Override
			protected void onField(final IField c) {
				append(c.getNameAsProperty());
			}
		}).toString();
	}

	public String getFieldNames() {
		return traverse(new CommasSeparatedBufferVisitor() {
			@Override
			protected void onField(final IField c) {
				append(c.getJavaName());
			}
		}).toString();
	}

	public AbstractJavaContext(final String pckg) {
		packageName = Preconditions.checkNotNull(pckg);
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return this.packageName;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageNameAsPath() {
		return this.packageName.replace('.', '/');
	}

	public abstract String getJavaName();
}
