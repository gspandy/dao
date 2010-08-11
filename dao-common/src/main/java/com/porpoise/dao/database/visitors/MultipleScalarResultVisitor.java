/**
 * SingleScalarResultVisitor.java

 */
package com.porpoise.dao.database.visitors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A visitor implementation which expects a single object of type 'T' for each
 * row
 */
public class MultipleScalarResultVisitor<T> extends AbstractResultSetVisitor {
	private final List<T> results;

	public MultipleScalarResultVisitor() {
		this(null);
	}

	public MultipleScalarResultVisitor(final List<T> collection) {
		results = collection == null ? new ArrayList<T>() : collection;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean onResultSet(final ResultSet resultSet) throws SQLException {
		results.add((T) resultSet.getObject(1));
		return true;
	}

	/**
	 * @return
	 */
	public List<T> getResults() {
		return results;
	}
}
