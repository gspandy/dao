package com.porpoise.dao.generator.model.api;

import com.google.common.base.CaseFormat;
import com.porpoise.generator.model.Cardinality;
import com.porpoise.generator.model.ICardinalitySupplier;
import com.porpoise.generator.model.IField;

public class DomainObjectField implements IField, ICardinalitySupplier {

	private final String name;
	private final DomainObject type;
	private final Cardinality cardinality;

	public String getIdAsAccessor() {
		return getType().getIdField().getNameAsAccessor();
	}

	public DomainObjectField(final String name, final DomainObject obj,
			final Cardinality cardinality) {
		this.name = name;
		this.type = obj;
		this.cardinality = cardinality;
	}

	@Override
	public String getJavaName() {
		return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL,
				getNameAsProperty());
	}

	@Override
	public String getJavaTypeName() {
		return type.getJavaName();
	}

	public DomainObject getType() {
		return type;
	}

	@Override
	public String toString() {
		return String.format("%s [%s]", getJavaName(), getJavaTypeName());
	}

	@Override
	public String getNameAsProperty() {
		return name;
	}

	@Override
	public boolean isPrimaryKey() {
		return false;
	}

	@Override
	public String getNameAsAccessor() {
		return "get" + getJavaName();
	}

	@Override
	public boolean isDate() {
		return false;
	}

	@Override
	public boolean isByteArray() {
		return false;
	}

	@Override
	public Cardinality getCardinality() {
		return cardinality;
	}

	@Override
	public boolean isBigDecimal() {
		return false;
	}

	@Override
	public String getJavaInterfaceName() {
		return "I" + getJavaTypeName();
	}

}
