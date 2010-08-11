package com.porpoise.api.generator.gen;

import com.porpoise.api.generator.model.DomainObject;
import com.porpoise.generator.AbstractJavaContext;

public class ApiContext extends AbstractJavaContext {

	private final DomainObject obj;

	public ApiContext(final String packageName, final DomainObject obj) {
		super(packageName);
		this.obj = obj;
	}

	@Override
	public String getJavaName() {
		return obj.getJavaName();
	}

}
