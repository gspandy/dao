package com.porpoise.dao.generator.gen;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.porpoise.dao.generator.model.Table;
import com.porpoise.generator.AbstractJavaContext;
import com.porpoise.generator.model.IField;

public class GeneratorContext extends AbstractJavaContext {

	private final List<Table> tables;

	public GeneratorContext(final String pckg,
			final Collection<Table> tableCollection) {
		super(pckg);
		tables = ImmutableList.copyOf(tableCollection);
	}

	public List<Table> getTables() {
		return tables;
	}

	@Override
	public String getJavaName() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Iterator<? extends IField> getFields() {
		throw new UnsupportedOperationException();
	}

}
