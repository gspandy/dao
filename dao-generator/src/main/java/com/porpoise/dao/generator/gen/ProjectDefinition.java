package com.porpoise.dao.generator.gen;

import java.io.File;
import java.util.Collection;

import com.porpoise.dao.generator.model.Table;

public class ProjectDefinition {
	private final Collection<Table> tables;

	private final File destination;
	private final String artifact;
	private final String group;
	private final String packageName;

	public ProjectDefinition(final Collection<Table> tables, final File destinationDirectory,
			final String artifact, final String group, final String packageName) {
		this.tables = tables;
		this.destination = destinationDirectory;
		this.artifact = artifact;
		this.group = group;
		this.packageName = packageName;
	}

	public Collection<Table> getTables() {
		return tables;
	}

	public File getTargetDirectory() {
		return destination;
	}

	public String getArtifactId() {
		return artifact;
	}

	public String getGroupId() {
		return group;
	}

	public String getPackageName() {
		return packageName;
	}

}