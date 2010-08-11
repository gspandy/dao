package com.porpoise.dao.generator.gen;

import java.io.File;
import java.util.Collection;

import com.porpoise.dao.generator.model.Table;
import com.porpoise.generator.AbstractProjectDefinition;

public class ProjectDefinition extends AbstractProjectDefinition {
	private final Collection<Table> tables;

	public ProjectDefinition(final Collection<Table> tables,
			final File destinationDirectory, final String packageName) {
		this(tables, destinationDirectory, null, null, null, packageName);
	}

	public ProjectDefinition(final Collection<Table> tables,
			final File destinationDirectory, final String group,
			final String artifact, final String version,
			final String packageName) {
		super(destinationDirectory, group, artifact, version, packageName);
		this.tables = tables;
	}

	public Collection<Table> getTables() {
		return tables;
	}
}