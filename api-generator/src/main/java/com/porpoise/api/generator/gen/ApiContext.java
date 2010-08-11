package com.porpoise.api.generator.gen;

import java.util.Iterator;
import java.util.List;

import com.porpoise.api.generator.model.DomainObject;
import com.porpoise.api.generator.model.DomainObjectField;
import com.porpoise.generator.AbstractJavaContext;
import com.porpoise.generator.model.Cardinality;
import com.porpoise.generator.model.ICardinalitySupplier;
import com.porpoise.generator.model.IField;

public class ApiContext extends AbstractJavaContext {

	private final DomainObject obj;

	public ApiContext(final String packageName, final DomainObject obj) {
		super(packageName);
		this.obj = obj;
	}

	public DomainObject getDomainObject() {
		return obj;
	}

	@Override
	public String getJavaName() {
		return obj.getJavaName();
	}

	public String getIdType() {
		return obj.getIdTypeName();
	}

	public boolean hasId() {
		return obj.hasIdField();
	}

	@Override
	protected Iterator<? extends IField> getFields() {
		return obj.getAllFields();
	}

	public List<IField> getListFields() {
		return obj.getListFields();
	}

	public List<IField> getSingleFields() {
		return obj.getSingleFields();
	}

	@Override
	public String getDeclarations() {

		return traverse(new CommasSeparatedBufferVisitor() {
			@Override
			protected void onField(final IField field) {
				boolean isList = false;
				if (field instanceof ICardinalitySupplier) {
					final ICardinalitySupplier cs = (ICardinalitySupplier) field;
					isList = cs.getCardinality() != Cardinality.OneToOne;
				}

				append("final ");
				if (isList) {
					append("Collection<");
				}
				if (field.isByteArray()) {
					append("byte[]");
				} else {
					append(field.getJavaTypeName());
				}
				if (isList) {
					append(">");
				}
				append(" ").append(field.getNameAsProperty());
			}
		}).toString();
	}

	public String getJavaKeyTypeForField(final IField f) {
		if (f instanceof DomainObjectField) {
			final DomainObjectField domainObjectField = (DomainObjectField) f;
			final DomainObject type = domainObjectField.getType();
			if (!type.hasIdField()) {
				return type.getJavaName();
			}
			return type.getIdField().getJavaTypeName();

		}
		return f.getJavaTypeName();
	}

	@Override
	public String getParameterListAsToString() {
		return traverse(new CommasSeparatedBufferVisitor() {
			@Override
			protected void onField(final IField c) {
				final boolean isList = isList(c);
				if (!isList) {
					append(c.getNameAsProperty()).append("=%s");
				}
			}
		}).toString();
	}

	private boolean isList(final IField c) {
		boolean isList = false;
		if (c instanceof ICardinalitySupplier) {
			final ICardinalitySupplier cs = (ICardinalitySupplier) c;
			isList = cs.getCardinality() != Cardinality.OneToOne;
		}
		return isList;
	}

	public String getToStringAccessorMethods(final String varName) {
		return traverse(new CommasSeparatedBufferVisitor() {
			@Override
			protected void onField(final IField c) {
				if (!isList(c)) {
					append(varName).append(".").append(c.getNameAsAccessor())
							.append("()");
				}
			}
		}).toString();
	}

}
