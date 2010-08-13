package com.porpoise.dao.generator.gen;

import static com.google.common.base.Predicates.notNull;
import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.porpoise.dao.generator.model.Table;
import com.porpoise.generator.AbstractJavaContext;
import com.porpoise.generator.model.IField;

public class DomainObjectContext extends AbstractJavaContext {

	private final Collection<DaoContext> contexts;

	public DomainObjectContext(final String pckg, final Collection<Table> tables) {
		super(pckg);
		this.contexts = ImmutableList.copyOf(asContext(pckg, tables));
	}

	public Collection<DaoContext> asContext(final String pckg,
			final Collection<Table> tables) {
		final List<DaoContext> contextsWithNulls = newArrayList(transform(
				tables, new Function<Table, DaoContext>() {
					@Override
					public DaoContext apply(final Table from) {
						if (from.isJoinTable()) {
							return null;
						}
						return new DaoContext(pckg, from);
					}
				}));

		return filter(contextsWithNulls, notNull());
	}

	public Collection<DaoContext> getTableContexts() {
		return contexts;
	}

	public Collection<DaoContext> getTableContextsWithIds() {
		return Collections2.<DaoContext> filter(getTableContexts(),
				new Predicate<DaoContext>() {
					@Override
					public boolean apply(final DaoContext input) {
						return input.hasIdField();
					}
				});
	}

	@Override
	public Iterable<? extends IField> getFields() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getJavaName() {
		throw new UnsupportedOperationException();
	}

}
