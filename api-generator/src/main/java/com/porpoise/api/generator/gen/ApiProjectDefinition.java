package com.porpoise.api.generator.gen;

import java.io.File;
import java.util.Collection;

import com.porpoise.api.generator.model.DomainObject;
import com.porpoise.dao.generator.model.Table;
import com.porpoise.generator.AbstractProjectDefinition;

public class ApiProjectDefinition extends AbstractProjectDefinition {

	private final Collection<DomainObject> objects;

	public ApiProjectDefinition(final Collection<DomainObject> objects,
			final File api, final String groupId, final String artifactId,
			final String version, final String packageName) {
		super(api, groupId, artifactId, version, packageName);
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
