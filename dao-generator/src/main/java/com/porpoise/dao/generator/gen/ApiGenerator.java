package com.porpoise.dao.generator.gen;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.porpoise.dao.generator.model.Table;
import com.porpoise.dao.generator.templates.ApiPomTemplate;

/**
 * This generator functional class intends to keep separate any api classes from
 * the DAO layer
 * 
 * Typical usage is to generate a DAO project separately from an API project,
 * though both could exist within the same project
 * 
 * @author Aaron
 */
public class ApiGenerator extends AbstractGenerator {

	private static final Map<String, IGenerator> mainSourceTemplateByFilename;
	private static final Map<String, IGenerator> testSourceTemplateByFilename;

	static {
		mainSourceTemplateByFilename = ImmutableMap.of(//
				);

		testSourceTemplateByFilename = ImmutableMap.of(//
				);
	}

	@Override
	protected Map<String, IGenerator> getMainSourceTemplateByFilename() {
		return mainSourceTemplateByFilename;
	}

	@Override
	protected Map<String, IGenerator> getTestSourceTemplateByFilename() {
		return testSourceTemplateByFilename;
	}

	@Override
	protected AbstractJavaContext newContext(final String packageName,
			final Table tbl) {
		final ApiContext apiContext = new ApiContext(packageName, tbl);
		return apiContext;
	}

	@Override
	protected void generateStaticTestClasses(final File destFolder,
			final AbstractJavaContext ctxt) throws IOException {
	}

	@Override
	protected IGenerator getPomTemplate() {
		return ApiPomTemplate.create(newLine());
	}

	@Override
	protected PomContext getPomContext(final String groupId,
			final String artifactId, final String version) {
		final PomContext ctxt = new PomContext(groupId, artifactId, version);
		final String apiSuffix = "-api";

		// TODO - this design is flawed -- we shouldn't allow arbitrary
		// configuration of artifacts if we're so heavily reliant on them. We
		// should instead insist we're told the dao project's group and artifact
		// IDs, as well as version instead of just assuming them here. But hey,
		// YAGNI - hopefully

		// this *should* be the case if we're taking defaults
		if (artifactId.endsWith(apiSuffix)) {
			final String daoArtifact = artifactId.substring(0,
					artifactId.length() - apiSuffix.length())
					+ "-dao";
			ctxt.addDependency(new PomContext(groupId, daoArtifact, version));
		} else {
			throw new IllegalArgumentException(
					"Sorry this isn't more clever, or available vai a compile-time check, but this generator is assuming an 'XXX-dao' project on which to depend. This artifact is assumed to match the format 'XXX-api' as well.");
		}
		return ctxt;
	}

	@Override
	protected void generateStaticMainClasses(final File destFolder,
			final AbstractJavaContext ctxt) throws IOException {
	}

}
