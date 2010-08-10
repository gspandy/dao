package com.porpoise.dao.generator.gen;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.porpoise.dao.generator.model.Table;

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

}
