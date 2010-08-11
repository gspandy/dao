package com.porpoise.generator.model;

import com.google.common.base.CaseFormat;
import com.google.common.base.Preconditions;

public abstract class AbstractField implements IField {

	private final FieldType type;

	public AbstractField(final FieldType t) {
		type = Preconditions.checkNotNull(t);
	}

	@Override
	public String getJavaTypeName() {
		return this.type.getJavaName();
	}

	@Override
	public String getJavaName() {
		return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL,
				getNameAsProperty());
	}

	public FieldType getType() {
		return type;
	}

	@Override
	public String getNameAsAccessor() {
		return "get"
				+ CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL,
						getNameAsProperty());
	}

	@Override
	public boolean isDate() {
		return getType() == FieldType.Date || getType() == FieldType.Timestamp;
	}

	@Override
	public boolean isBigDecimal() {
		return getType() == FieldType.BigDecimal;
	}

	@Override
	public boolean isByteArray() {
		return getType() == FieldType.Bytes;
	}
}
