package com.porpoise.api.generator.model;

public class DomainObjectField {

	private final String name;
	private final DomainObject type;

	public DomainObjectField(final String name, final DomainObject obj) {
		this.name = name;
		this.type = obj;
	}

	public String getJavaName() {
		return name;
	}

	public DomainObject getType() {
		return type;
	}

	public String getJavaTypeName() {
		return getType().getJavaName();
	}

	@Override
	public String toString() {
		return String.format("%s [%s]", getJavaName(), getJavaTypeName());
	}
}
