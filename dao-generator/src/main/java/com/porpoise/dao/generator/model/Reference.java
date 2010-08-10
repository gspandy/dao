package com.porpoise.dao.generator.model;

import com.google.common.base.Preconditions;

public class Reference {

	private final Column from;
	private final Column to;

	public Reference(final Column f, final Column t) {
		from = Preconditions.checkNotNull(f);
		to = Preconditions.checkNotNull(t);
	}

	public Column getFrom() {
		return from;
	}

	public Column getTo() {
		return to;
	}
}
