package com.porpoise.generator;

import java.io.File;

import com.google.common.base.Strings;

public abstract class AbstractProjectDefinition {
	private final File destination;
	private final String artifact;
	private final String group;
	private final String packageName;
	private final String version;
	private final boolean hasPomDefinition;

	public AbstractProjectDefinition(final File destinationDirectory,
			final String packageName) {
		this(destinationDirectory, null, null, null, packageName);
	}

	public AbstractProjectDefinition(final File destinationDirectory,
			final String group, final String artifact, final String version,
			final String packageName) {
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
