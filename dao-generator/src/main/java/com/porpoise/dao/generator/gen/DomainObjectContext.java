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
import com.porpoise.dao.generator.model.api.DomainObject;
import com.porpoise.generator.AbstractJavaContext;
import com.porpoise.generator.model.IField;

public class DomainObjectContext extends AbstractJavaContext {

	private final Collection<ApiContext> contexts;

	public DomainObjectContext(final String pckg, final Collection<Table> tables) {
		super(pckg);
		this.contexts = ImmutableList.copyOf(asContext(pckg, tables));
	}

	public Collection<ApiContext> asContext(final String pckg,
			final Collection<Table> tables) {
		final Collection<DomainObject> domainObjects = ApiProjectDefinition
				.valueOf(tables);

		final List<ApiContext> contextsWithNulls = newArrayList(transform(
				domainObjects, new Function<DomainObject, ApiContext>() {
					@Override
					public ApiContext apply(final DomainObject from) {
						return new ApiContext(pckg, from);
					}
				}));

		return filter(contextsWithNulls, notNull());
	}

	public Collection<ApiContext> getTableContexts() {
		return contexts;
	}

	public Collection<ApiContext> getTableContextsWithIds() {
		return Collections2.<ApiContext> filter(getTableContexts(),
				new Predicate<ApiContext>() {
					@Override
					public boolean apply(final ApiContext input) {
						return input.hasId();
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
