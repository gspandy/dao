package com.porpoise.generator;

import java.util.Collection;

import com.google.common.collect.Lists;

public class PomContext {
	private final String name;
	private final String artifactId;
	private final String groupId;
	private final String version;

	private final Collection<PomContext> dependencies = Lists.newArrayList();

	public PomContext(final String group, final String artifact,
			final String vers) {
		this(group, artifact, vers, group + "." + artifact);
	}

	public void addDependency(final PomContext dependency) {
		dependencies.add(dependency);
	}

	public Collection<PomContext> getDependencies() {
		return dependencies;
	}

	public PomContext(final String group, final String artifact,
			final String vers, final String nameValue) {
		this.groupId = group;
		this.artifactId = artifact;
		this.version = vers;
		this.name = nameValue;
	}

	/**
	 * @return the artifactId
	 */
	public String getArtifactId() {
		return this.artifactId;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return this.groupId;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return this.version;
	}

	public String getName() {
		return this.name;
	}
}
