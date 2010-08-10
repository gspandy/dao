package com.porpoise.dao.generator.gen;

import com.google.common.base.Preconditions;

public abstract class AbstractJavaContext {

	private final String packageName;

	public AbstractJavaContext(final String pckg) {
		packageName = Preconditions.checkNotNull(pckg);
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return this.packageName;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageNameAsPath() {
		return this.packageName.replace('.', '/');
	}

	public abstract String getJavaName();
}
