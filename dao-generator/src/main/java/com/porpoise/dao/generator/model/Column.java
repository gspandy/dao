package com.porpoise.dao.generator.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.base.CaseFormat;
import com.google.common.base.Function;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.porpoise.generator.model.AbstractField;
import com.porpoise.generator.model.Cardinality;
import com.porpoise.generator.model.FieldType;

public class Column extends AbstractField implements Comparable<Column> {
	private final String name;
	private final boolean required;

	private final Table owningTable;

	private final Multimap<Cardinality, Column> fkReferences = ArrayListMultimap
			.create();
	private final Multimap<Cardinality, Column> referencedBy = ArrayListMultimap
			.create();

	Column(final Table table, final String n, final boolean isRequired,
			final FieldType colType) {
		super(colType);
		this.owningTable = checkNotNull(table);
		this.name = checkNotNull(n);
		this.required = isRequired;
	}

	public boolean fkReferenceTo(final Column other) {
		return fkReferenceTo(other, Cardinality.OneToMany);
	}

	public boolean fkReferenceTo(final Column other,
			final Cardinality cardinality) {
		if (other == null) {
			return false;
		}
		checkArgument(other.getTable() != owningTable,
				"cannot create a foreign key reference to another column in the same table");
		final boolean added = fkReferences.put(cardinality, other);
		if (added) {
			// complete the bi-directional knowledge
			final boolean inverseAdded = other.referencedBy.put(
					cardinality.inverse(), this);
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

	public String getResultSetAccessorName() {
		return getType().getResultSetAccessorName();
	}

	@Override
	public String getNameAsProperty() {
		return CaseFormat.UPPER_UNDERSCORE
				.to(CaseFormat.LOWER_CAMEL, getName());
	}

	/**
	 * @return true if this column is a primary key
	 */
	@Override
	public boolean isPrimaryKey() {
		return getTable().hasIdColumn()
				&& getTable().getIdColumn().equals(this);
	}

	@Override
	public String toString() {
		return getTableColumnName();
	}

	private String getTableColumnName() {
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
	public SortedSet<Reference> getReferencingColumns() {
		final SortedSet<Reference> references = new TreeSet<Reference>();

		for (final Entry<Cardinality, Collection<Column>> entry : referencedBy
				.asMap().entrySet()) {
			for (final Column fk : entry.getValue()) {
				final Cardinality cardinality = entry.getKey();
				references.add(new Reference(fk, this, cardinality));
			}
		}

		return references;
	}

	public SortedSet<Reference> getAllReferencingColumns() {
		final SortedSet<Reference> originalReferences = getReferencingColumns();
		final SortedSet<Reference> references = Sets
				.newTreeSet(originalReferences);
		// These calls may not add all members, as some may have been added
		// as the inverse relation from the above call

		{
			final Collection<Reference> manyToOneReferences = getForeignKeyReferences(Cardinality.ManyToOne);
			final Collection<Reference> manyToOneFlipped = Reference
					.flip(manyToOneReferences);
			references.addAll(manyToOneFlipped);
		}

		{
			final Collection<Reference> manyToManyReferences = getForeignKeyReferences(Cardinality.ManyToMany);
			final Collection<Reference> manyToManyFlipped = Reference
					.flip(manyToManyReferences);
			references.addAll(manyToManyFlipped);
		}
		return references;
	}

	private Collection<Reference> getForeignKeyReferences(
			final Cardinality cardinality) {
		final Function<Column, Reference> function = new Function<Column, Reference>() {
			@Override
			public Reference apply(final Column col) {
				return new Reference(Column.this, col, cardinality);
			}
		};
		return Collections2.transform(fkReferences.get(cardinality), function);
	}

	public boolean isReferenced() {
		return !getReferencingColumns().isEmpty();
	}

	public boolean hasFkReferences() {
		return !fkReferences.isEmpty();
	}

	@Override
	public String getJavaName() {
		return getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getTableColumnName().hashCode();
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Column other = (Column) obj;
		return getTableColumnName().equals(other.getTableColumnName());
	}

	@Override
	public int compareTo(final Column o) {
		return name.compareTo(o.name);
	}

	public String getNameAsJava() {
		return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL,
				getNameAsProperty());
	}

}
