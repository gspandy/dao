package com.porpoise.dao.generator.model.api;

import static com.google.common.base.Joiner.on;
import static com.google.common.collect.Collections2.transform;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.porpoise.generator.model.Cardinality;
import com.porpoise.generator.model.FieldType;
import com.porpoise.generator.model.IField;

public class DomainObject {
	private final String javaName;
	private final List<DomainObjectField> oneToOneFields = Lists.newArrayList();
	private final List<Field> primitiveFields = Lists.newArrayList();
	private final List<DomainObjectField> oneToManyFields = Lists
			.newArrayList();
	private final List<DomainObjectField> manyToManyFields = Lists
			.newArrayList();
	private final List<DomainObjectField> manyToOneFields = Lists
			.newArrayList();
	private Field idField;

	@Override
	public String toString() {

		final Function<Object, String> getName = Functions.toStringFunction();
		final String oto = on(String.format(",%n")).join(
				transform(oneToOneFields, getName));
		final String otm = on(String.format(",%n")).join(
				transform(oneToManyFields, getName));
		final String mtm = on(String.format(",%n")).join(
				transform(manyToManyFields, getName));
		final String mto = on(String.format(",%n")).join(
				transform(manyToOneFields, getName));
		return String
				.format("%s%nPrimitives:%nOneToOne:%n%s%nOneToMany:%n%s%nManyToMany:%n%s%nManyToOne:%n%s%n",
						javaName, oto, otm, mtm, mto);
	}

	public DomainObject(final String name) {
		javaName = name;
	}

	public void addIdField(final String name, final FieldType type,
			final boolean required) {
		final Field id = addPrimitiveField(name, type, required);
		idField = id;
	}

	public Field addPrimitiveField(final String name, final FieldType type,
			final boolean required) {
		final Field field = new Field(this, name, type, required);
		primitiveFields.add(field);
		return field;
	}

	public void addOneToManyField(final String name, final DomainObject obj) {
		oneToManyFields.add(new DomainObjectField(name, obj,
				Cardinality.OneToMany));
	}

	public void addManyToOneField(final String name, final DomainObject obj) {
		manyToOneFields.add(new DomainObjectField(name, obj,
				Cardinality.ManyToOne));
	}

	public void addManyToManyField(final String name, final DomainObject obj) {
		manyToManyFields.add(new DomainObjectField(name, obj,
				Cardinality.ManyToMany));
	}

	public Field getIdField() {
		return idField;
	}

	public boolean hasIdField() {
		return getIdField() != null;
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

	public List<DomainObjectField> getManyToOneFields() {
		return manyToOneFields;
	}

	public List<Field> getPrimitiveFields() {
		return primitiveFields;
	}

	public List<DomainObjectField> getObjectFields() {
		return oneToOneFields;
	}

	public void addObjectField(final String name, final DomainObject obj) {
		oneToOneFields.add(new DomainObjectField(name, obj,
				Cardinality.OneToOne));
	}

	public String getIdTypeName() {
		return getIdField().getType().getJavaName();
	}

	public Iterable<? extends IField> getAllFields() {
		final Collection<IField> all = Lists.newArrayList();
		all.addAll(getSingleFields());
		all.addAll(getListFields());
		return all;
	}

	public List<IField> getListFields() {
		final List<IField> fields = Lists.newArrayList();
		fields.addAll(oneToManyFields);
		fields.addAll(manyToManyFields);
		return fields;
	}

	public List<IField> getSingleFields() {
		final List<IField> fields = Lists.newArrayList();
		fields.addAll(oneToOneFields);
		fields.addAll(manyToOneFields);
		fields.addAll(primitiveFields);
		return fields;
	}

	public Cardinality getCardinality(final DomainObjectField dof) {
		if (manyToOneFields.contains(dof)) {
			return Cardinality.ManyToOne;
		}
		if (manyToManyFields.contains(dof)) {
			return Cardinality.ManyToMany;
		}
		if (oneToManyFields.contains(dof)) {
			return Cardinality.OneToMany;
		}
		if (oneToOneFields.contains(dof)) {
			return Cardinality.OneToOne;
		}
		return null;
	}

}
