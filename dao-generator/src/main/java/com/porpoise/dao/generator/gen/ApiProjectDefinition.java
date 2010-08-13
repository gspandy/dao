package com.porpoise.dao.generator.gen;

import java.io.File;
import java.util.Collection;

import com.porpoise.dao.generator.model.Table;
import com.porpoise.dao.generator.model.api.DomainObject;
import com.porpoise.generator.AbstractProjectDefinition;

public class ApiProjectDefinition extends AbstractProjectDefinition {

	private final Collection<DomainObject> objects;

	public ApiProjectDefinition(final Collection<DomainObject> objects,
			final File destinationDirectory, final String groupId,
			final String artifactId, final String version,
			final String packageName) {
		super(destinationDirectory, groupId, artifactId, version, packageName);
		this.objects = objects;
	}

	public Collection<DomainObject> getObjects() {
		return objects;
	}

	public static Collection<DomainObject> valueOf(
			final Collection<Table> tables) {
		return DomainHelper.valueOf(tables);
	}

}
