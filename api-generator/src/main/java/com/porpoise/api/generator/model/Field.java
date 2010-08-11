package com.porpoise.api.generator.model;

import com.porpoise.generator.model.FieldType;

public class Field {
	private final DomainObject owner;
	private final String javaName;
	private final FieldType type;
	private final boolean required;

	Field(final DomainObject parent, final String name, final FieldType type,
			final boolean required) {
		owner = parent;
		javaName = name;
		this.type = type;
		this.required = required;
	}

	public FieldType getType() {
		return type;
	}

	public DomainObject getOwner() {
		return owner;
	}

	public String getJavaName() {
		return javaName;
	}
}
