package com.porpoise.dao.generator.gen.access;

import com.porpoise.dao.generator.model.Column;
import com.porpoise.dao.generator.model.Table;
import com.porpoise.generator.model.FieldType;

/**
 * special case of a table which optionally has 'created on' and 'last modified'
 * columns on its join tables
 * 
 * @author Aaron
 * 
 */
public final class AccessTable extends Table {
	public AccessTable(final String n) {
		super(n);
	}

	@Override
	public boolean isJoinTable() {

		int refCount = 0;
		for (final Column c : getColumns()) {
			if (isRefCol(c)) {
				refCount++;
			} else {
				// there may be 'created on' and 'modified on' columns
				// in addition to the fk columns
				if (c.getType() != FieldType.Timestamp) {
					return false;
				}
			}
		}
		return refCount == 2;
	}
}