package com.porpoise.api.generator.model;

import com.porpoise.generator.model.AbstractField;
import com.porpoise.generator.model.Cardinality;
import com.porpoise.generator.model.FieldType;
import com.porpoise.generator.model.ICardinalitySupplier;

public class Field extends AbstractField implements ICardinalitySupplier {
	private final DomainObject owner;
	private final String javaName;
	private final boolean required;

	Field(final DomainObject parent, final String name, final FieldType type,
			final boolean required) {
		super(type);
		owner = parent;
		javaName = name;
		this.required = required;
	}

	public DomainObject getOwner() {
		return owner;
	}

	@Override
	public String getJavaTypeName() {
		if (isByteArray()) {
			return "ImmutableList<Byte>";
		}
		return super.getJavaTypeName();
	}

	public boolean isRequired() {
		return required;
	}

	@Override
	public String getNameAsProperty() {
		return javaName;
	}

	@Override
	public boolean isPrimaryKey() {
		return owner.getIdField() == this;
	}

	@Override
	public Cardinality getCardinality() {
		return Cardinality.OneToOne;
	}

}
