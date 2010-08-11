package com.porpoise.dao.generator.model;

import com.google.common.base.Preconditions;
import com.porpoise.generator.model.Cardinality;

public class Reference {

	private final Column from;
	private final Column to;
	private final Cardinality cardinality;

	public Reference(final Column f, final Column t, final Cardinality c) {
		from = Preconditions.checkNotNull(f);
		to = Preconditions.checkNotNull(t);
		cardinality = Preconditions.checkNotNull(c);
	}

	public Column getFromTablePrimaryKey() {
		if (from.getTable().hasIdColumn()) {
			return from.getTable().getIdColumn();
		}
		return from;
	}

	public Column getFrom() {
		return from;
	}

	public Column getTo() {
		return to;
	}

	public Cardinality getCardinality() {
		return cardinality;
	}
}
