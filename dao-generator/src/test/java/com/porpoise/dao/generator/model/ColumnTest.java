package com.porpoise.dao.generator.model;

import java.util.Iterator;
import java.util.SortedSet;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.porpoise.generator.model.Cardinality;
import com.porpoise.generator.model.FieldType;

public class ColumnTest {

	/**
	 * Test the {@link Column#getAllReferencingColumns()} method, ensuring
	 * 
	 * that it returns unique relationships, even when the column participates
	 * as the inverse of a relationship
	 */
	@Test
	public void test_getAllReferencingColumns() {

		// create a table which will be referenced from two other tables
		final Table tableUnderTest = new Table("A");
		final Column columnUnderTest = tableUnderTest.addColumn("columnA",
				false, FieldType.Long);

		// reference the table from two other tables
		final String[] referencingTableNames = new String[] { "B", "C" };

		// create some fields to store the expected referencing columns
		final SortedSet<Column> expectedReferencingColumns = Sets.newTreeSet();

		// add the tables which reference the target table
		final int colPrefixCountForAlphabeticalOrdering = 1;
		for (final String tableName : referencingTableNames) {
			final Table referencer = new Table(tableName);

			for (final Cardinality c : Cardinality.values()) {

				// reference the target table (column) from another table
				final String name = colPrefixCountForAlphabeticalOrdering + ":"
						+ tableName + "-" + c.name();
				final Column col = referencer.addColumn(name, false,
						FieldType.Long);
				col.fkReferenceTo(columnUnderTest, c);
				expectedReferencingColumns.add(col);

				// here we 'manually' add a reference back from the target to
				// the referencing table, but this should NOT end up in
				// duplicate references in the
				// 'getAllReferencingColumns' call
				//
				// this is trying to test that, given the reference:
				//
				// A -[OneToMany]-> B
				//
				// if we then add
				// B -[ManyToOne]-> A
				//
				// we don't end up with two references in the 'all' references
				// list.
				//
				// B should know it is referenced by A in the first
				// relationship, but then explicitly creating a relationship
				// from B to A DOES NOT result in what would be two
				// relationships of:
				//
				// B -[ManyToOne]-> A (B knows A referenced it in the first
				// case, which looks like this)
				// B -[ManyToOne]-> A (B was explicitly told it had a reference
				// to A, which looks the same as above)
				//
				columnUnderTest.fkReferenceTo(col, c.inverse());
			}
		}

		// call the method under test
		final SortedSet<Reference> actualReferences = columnUnderTest
				.getAllReferencingColumns();

		// assert the referencing values are as expected
		final int expectedSize = referencingTableNames.length
				* Cardinality.values().length;
		Assert.assertEquals(expectedSize, actualReferences.size());
		final Iterator<Column> expectedCols = expectedReferencingColumns
				.iterator();
		for (final Reference r : actualReferences) {
			Assert.assertEquals(expectedCols.next(), r.getFrom());
		}

	}

}
