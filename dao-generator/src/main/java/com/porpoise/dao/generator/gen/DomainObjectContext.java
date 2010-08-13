package com.porpoise.dao.generator.gen;

import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.porpoise.dao.generator.model.Table;
import com.porpoise.generator.AbstractJavaContext;
import com.porpoise.generator.model.IField;

public class DomainObjectContext extends AbstractJavaContext {

	private final Collection<DaoContext> tables;

	public DomainObjectContext(final String pckg, final Collection<Table> tables) {
		super(pckg);
		this.tables = Lists.newArrayList(Collections2.transform(tables,
				new Function<Table, DaoContext>() {
					@Override
					public DaoContext apply(final Table from) {
						return new DaoContext(pckg, from);
					}
				}));
	}

	public Collection<DaoContext> getTables() {
		return tables;
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
