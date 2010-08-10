package com.porpoise.dao.generator.gen;

import java.io.File;
import java.util.Collection;

import com.google.common.base.Strings;
import com.porpoise.dao.generator.model.Table;

public class ProjectDefinition {
	private final Collection<Table> tables;

	private final File destination;
	private final String artifact;
	private final String group;
	private final String packageName;
	private final String version;
	private final boolean hasPomDefinition;

	public ProjectDefinition(final Collection<Table> tables,
			final File destinationDirectory, final String packageName) {
		this(tables, destinationDirectory, null, null, null, packageName);
	}

	public ProjectDefinition(final Collection<Table> tables,
			final File destinationDirectory, final String group,
			final String artifact, final String version,
			final String packageName) {
		this.tables = tables;
		this.destination = destinationDirectory;
		this.artifact = artifact;
		this.group = group;
		this.version = version;
		this.packageName = packageName;
		hasPomDefinition = hasValue(artifact) && hasValue(group)
				&& hasValue(version);
	}

	private boolean hasValue(final String artifact) {
		return !Strings.isNullOrEmpty(artifact);
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

	public String getVersion() {
		return version;
	}

	public boolean hasPomDefinition() {
		return hasPomDefinition;
	}
}