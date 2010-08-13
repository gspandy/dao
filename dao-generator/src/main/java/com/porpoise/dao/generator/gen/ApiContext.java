package com.porpoise.dao.generator.gen;

import java.util.List;

import com.google.common.base.CaseFormat;
import com.porpoise.dao.generator.model.api.DomainObject;
import com.porpoise.dao.generator.model.api.DomainObjectField;
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

	public String getPropertyName() {
		return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, getJavaName());
	}

	public String getIdType() {
		return obj.getIdTypeName();
	}

	public boolean hasId() {
		return obj.hasIdField();
	}

	@Override
	protected Iterable<? extends IField> getFields() {
		return obj.getAllFields();
	}

	public List<IField> getListFields() {
		return obj.getListFields();
	}

	public List<IField> getSingleFields() {
		return obj.getSingleFields();
	}

	public String getDeclarationVariables() {
		return traverse(new DelimSeparatedBufferVisitor(String.format(",%n")) {
			@Override
			protected void onField(final IField field) {
				append(field.getNameAsProperty());
			}

		}).toString();

	}

	@Override
	public String getDeclarations() {

		return traverse(new DelimSeparatedBufferVisitor() {
			@Override
			protected void onField(final IField field) {
				final String declaration = getDeclarationForField(field);
				append(declaration);
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
		return traverse(new DelimSeparatedBufferVisitor() {
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
		return traverse(new DelimSeparatedBufferVisitor() {
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
