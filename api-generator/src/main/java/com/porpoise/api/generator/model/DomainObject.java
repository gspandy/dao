package com.porpoise.api.generator.model;

import static com.google.common.base.Joiner.on;
import static com.google.common.collect.Collections2.transform;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.porpoise.generator.model.FieldType;

public class DomainObject {
	private final String javaName;
	private final List<DomainObjectField> objectFields = Lists.newArrayList();
	private final List<Field> primitiveFields = Lists.newArrayList();
	private final List<DomainObjectField> oneToManyFields = Lists
			.newArrayList();
	private final List<DomainObjectField> manyToManyFields = Lists
			.newArrayList();

	@Override
	public String toString() {

		final Function<Object, String> getName = Functions.toStringFunction();
		final String oto = on(String.format(",%n")).join(
				transform(objectFields, getName));
		final String otm = on(String.format(",%n")).join(
				transform(oneToManyFields, getName));
		final String mtm = on(String.format(",%n")).join(
				transform(manyToManyFields, getName));
		return String
				.format("%s%nPrimitives:%n%s%nOneToOne:%n%s%nOneToMany:%n%s%nManyToMany:%n%s%n",
						javaName, oto, otm, mtm);
	}

	public DomainObject(final String name) {
		javaName = name;
	}

	public void addPrimitiveField(final String name, final FieldType type,
			final boolean required) {
		primitiveFields.add(new Field(this, name, type, required));
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

	public void addObjectField(final String name, final DomainObject obj) {
		objectFields.add(new DomainObjectField(name, obj));
	}
}
