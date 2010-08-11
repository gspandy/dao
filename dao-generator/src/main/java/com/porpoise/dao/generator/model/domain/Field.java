package com.porpoise.dao.generator.model.domain;

import com.porpoise.generator.model.FieldType;

public class Field {
	private final DomainObject owner;
	private final String javaName;
	private final FieldType type;

	Field(final DomainObject parent, final String name, final FieldType type) {
		owner = parent;
		javaName = name;
		this.type = type;
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
