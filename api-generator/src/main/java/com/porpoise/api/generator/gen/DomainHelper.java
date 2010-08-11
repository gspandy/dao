package com.porpoise.api.generator.gen;

import java.util.Collection;
import java.util.Map;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.porpoise.api.generator.model.DomainObject;
import com.porpoise.dao.generator.model.Column;
import com.porpoise.dao.generator.model.Reference;
import com.porpoise.dao.generator.model.Table;
import com.porpoise.generator.model.Cardinality;

enum DomainHelper {
	;// uninstantiable

	public static Collection<DomainObject> valueOf(
			final Collection<Table> tables) {

		final Collection<Table> joinTables = Sets.newHashSet();

		final Map<String, DomainObject> objByName = createInitialObjects(
				tables, joinTables);

		populateObjects(objByName, tables);

		return objByName.values();
	}

	private static void populateObjects(
			final Map<String, DomainObject> objByName,
			final Collection<Table> tables) {
		// second pass - create the objects' fields
		for (final Table t : tables) {
			if (!t.isJoinTable()) {
				final DomainObject sourceObject = objByName
						.get(t.getJavaName());
				for (final Column col : t.getColumns()) {
					addPropertyForColumn(sourceObject, objByName, col);
				}
			}
		}
	}

	private static void addPropertyForColumn(final DomainObject sourceObject,
			final Map<String, DomainObject> objByName, final Column col) {
		final boolean isPrimitive = isPrimitive(col);
		// if (isPrimitive) {
		sourceObject.addPrimitiveField(col.getName(), col.getType(),
				col.isRequired());
		// }

		addReferencesProperties(sourceObject, objByName, col);
	}

	private static void addReferencesProperties(
			final DomainObject sourceObject,
			final Map<String, DomainObject> objByName, final Column col) {
		addReferencedProperties(sourceObject, objByName, col);
		addReferencingProperties(sourceObject, objByName, col);
	}

	private static void addReferencedProperties(
			final DomainObject sourceObject,
			final Map<String, DomainObject> objByName, final Column col) {
		final Collection<Reference> referencedColumns = col
				.getForeignKeyReferences();
		for (final Reference r : referencedColumns) {
			assert r.getFrom() == col;

			final DomainObject objType;
			Cardinality cardinality;
			{
				final Column referencedColumn = r.getTo();
				Table targetTable = referencedColumn.getTable();

				if (targetTable.isJoinTable()) {
					final Column to = getOtherSideOfJoinTable(targetTable,
							referencedColumn);

					targetTable = to.getTable();
					cardinality = Cardinality.ManyToMany;
				} else {
					cardinality = r.getCardinality();
				}
				objType = objByName.get(targetTable.getJavaName());
			}

			addComplexField(sourceObject, objType, cardinality);
		}
	}

	private static Column getOtherSideOfJoinTable(final Table joinTable,
			final Column firstKey) {
		final Column otherFk = joinTable.findOtherFkField(firstKey);

		final Collection<Reference> foreignKeyReferences = otherFk
				.getForeignKeyReferences();
		if (foreignKeyReferences.size() != 1) {
			throw new IllegalStateException(
					"A join table references multiple tables in a single field: "
							+ otherFk);
		}
		final Reference otherSideOfRefTable = Iterables
				.getOnlyElement(foreignKeyReferences);
		final Column to = otherSideOfRefTable.getTo();
		return to;
	}

	private static void addReferencingProperties(
			final DomainObject sourceObject,
			final Map<String, DomainObject> objByName, final Column col) {
		final Collection<Reference> referencingColumns = col
				.getReferencingColumns();
		for (final Reference r : referencingColumns) {
			assert r.getTo() == col;

			final DomainObject objType;
			Cardinality cardinality;
			{
				final Column referencedFromColumn = r.getFrom();
				Table fromTable = referencedFromColumn.getTable();

				if (fromTable.isJoinTable()) {
					final Column to = getOtherSideOfJoinTable(fromTable,
							referencedFromColumn);
					fromTable = to.getTable();
					cardinality = Cardinality.ManyToMany;
				} else {
					cardinality = r.getCardinality();
				}
				objType = objByName.get(fromTable.getJavaName());
			}

			addComplexField(sourceObject, objType, cardinality);
		}
	}

	private static Map<String, DomainObject> createInitialObjects(
			final Collection<Table> tables, final Collection<Table> joinTables) {
		final Map<String, DomainObject> objByName = Maps.newHashMap();

		// first pass - just create the objects
		for (final Table t : tables) {
			if (!t.isJoinTable()) {
				final DomainObject obj = new DomainObject(t.getJavaName());
				objByName.put(obj.getJavaName(), obj);
			} else {
				joinTables.add(t);
			}
		}

		return objByName;
	}

	private static void addComplexField(final DomainObject srcObject,
			final DomainObject propertyObject, final Cardinality cardinality) {
		final String name = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL,
				propertyObject.getJavaName());

		switch (cardinality) {
		case OneToOne: {
			srcObject.addObjectField(name, propertyObject);
			break;
		}
		case OneToMany: {
			srcObject.addOneToManyField(name, propertyObject);
			break;
		}
		case ManyToMany: {
			srcObject.addManyToManyField(name, propertyObject);
			break;
		}
		default:
			throw new IllegalArgumentException();
		}
	}

	private static boolean isPrimitive(final Column col) {
		final boolean primitive = !col.hasFkReferences();
		if (!primitive) {
			System.out.println("not primitive: " + col);
		}
		return primitive;
	}
}
