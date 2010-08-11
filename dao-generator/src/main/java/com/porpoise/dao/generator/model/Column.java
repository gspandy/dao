package com.porpoise.dao.generator.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.Map.Entry;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.porpoise.generator.model.Cardinality;
import com.porpoise.generator.model.FieldType;

public class Column {
	private final String name;
	private final boolean required;
	private final FieldType type;
	private final Table owningTable;

	private final Multimap<Cardinality, Column> fkReferences = ArrayListMultimap
			.create();
	private final Multimap<Cardinality, Column> referencedBy = ArrayListMultimap
			.create();

	Column(final Table table, final String n, final boolean isRequired,
			final FieldType colType) {
		this.owningTable = checkNotNull(table);
		this.name = checkNotNull(n);
		this.required = isRequired;
		this.type = checkNotNull(colType);
	}

	public boolean fkReferenceTo(final Column other) {
		return fkReferenceTo(Cardinality.OneToMany, other);
	}

	public boolean fkReferenceTo(final Cardinality cardinality,
			final Column other) {
		if (other == null) {
			return false;
		}
		checkArgument(other.getTable() != owningTable,
				"cannot create a foreign key reference to another column in the same table");
		final boolean added = fkReferences.put(cardinality, other);
		if (added) {
			// complete the bi-directional knowledge
			final boolean inverseAdded = other.referencedBy.put(cardinality,
					this);
			assert inverseAdded;
		}
		return added;
	}

	public Table getTable() {
		return owningTable;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the required
	 */
	public boolean isRequired() {
		return this.required;
	}

	/**
	 * @return the type
	 */
	public FieldType getType() {
		return this.type;
	}

	public String getJavaTypeName() {
		return this.type.getJavaName();
	}

	public String getResultSetAccessorName() {
		return type.getResultSetAccessorName();
	}

	public String getNameAsProperty() {
		return CaseFormat.UPPER_UNDERSCORE
				.to(CaseFormat.LOWER_CAMEL, getName());
	}

	public String getNameAsAccessor() {
		return "get"
				+ CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
						getName());
	}

	/**
	 * @return true if this column is a primary key
	 */
	public boolean isPrimaryKey() {
		return getTable().hasIdColumn()
				&& getTable().getIdColumn().equals(this);
	}

	@Override
	public String toString() {
		return String.format("%s.%s", getTable().getTableName(), getName());
	}

	public Collection<Reference> getForeignKeyReferences() {
		final Collection<Reference> references = Lists.newArrayList();

		for (final Entry<Cardinality, Collection<Column>> entry : fkReferences
				.asMap().entrySet()) {
			for (final Column fk : entry.getValue()) {
				references.add(new Reference(this, fk, entry.getKey()));
			}
		}

		return references;
	}

	/**
	 * @return a collection of references from other tables -- return the tables
	 *         which reference this column
	 */
	public Collection<Reference> getReferencingColumns() {
		final Collection<Reference> references = Lists.newArrayList();

		for (final Entry<Cardinality, Collection<Column>> entry : referencedBy
				.asMap().entrySet()) {
			for (final Column fk : entry.getValue()) {
				references.add(new Reference(fk, this, entry.getKey()));
			}
		}

		return references;
	}

	public boolean isReferenced() {
		return !getReferencingColumns().isEmpty();
	}

	public boolean hasFkReferences() {
		return !fkReferences.isEmpty();
	}

	public boolean isBigDecimal() {
		return getType() == FieldType.BigDecimal;
	}

	public boolean isDate() {
		return getType() == FieldType.Date || getType() == FieldType.Timestamp;
	}

	public boolean isByteArray() {
		return getType() == FieldType.Bytes;
	}

}
