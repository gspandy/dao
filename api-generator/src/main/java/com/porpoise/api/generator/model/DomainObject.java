package com.porpoise.api.generator.model;

import java.util.List;

import com.google.common.collect.Lists;
import com.porpoise.generator.model.FieldType;

public class DomainObject {
	private final String javaName;
	private final List<Field> primitiveFields = Lists.newArrayList();
	private final List<DomainObjectField> oneToManyFields = Lists
			.newArrayList();
	private final List<DomainObjectField> manyToManyFields = Lists
			.newArrayList();

	public DomainObject(final String name) {
		javaName = name;
	}

	public void addPrimitiveField(final String name, final FieldType type) {
		primitiveFields.add(new Field(this, name, type));
	}

	public void addOneToManyField(final String name, final DomainObject obj) {
		oneToManyFields.add(new DomainObjectField(name, obj));
	}

	public void addManyToManyField(final String name, final DomainObject obj) {
		manyToManyFields.add(new DomainObjectField(name, obj));
	}

	public String getJavaName() {
		return javaName;
	}

	public List<DomainObjectField> getOneToManyFields() {
		return oneToManyFields;
	}

	public List<DomainObjectField> getManyToManyFields() {
		return manyToManyFields;
	}

	public List<Field> getPrimitiveFields() {
		return primitiveFields;
	}
}
