package com.porpoise.api.generator.gen;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.porpoise.api.generator.templates.AbstractAccessorServiceTemplate;
import com.porpoise.api.generator.templates.AbstractDomainObjectTemplate;
import com.porpoise.api.generator.templates.AbstractObjectTemplate;
import com.porpoise.api.generator.templates.AccessorServiceTemplate;
import com.porpoise.api.generator.templates.AccessorTemplate;
import com.porpoise.api.generator.templates.ApiPomTemplate;
import com.porpoise.api.generator.templates.DomainTemplate;
import com.porpoise.dao.generator.gen.ApiContext;
import com.porpoise.dao.generator.gen.ApiProjectDefinition;
import com.porpoise.dao.generator.model.api.DomainObject;
import com.porpoise.generator.AbstractGenerator;
import com.porpoise.generator.AbstractJavaContext;
import com.porpoise.generator.IGenerator;

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
	private static final String DOMAIN_DIR = "domain/";
	private static final String SERVICE_DIR = "service/";

	static {
		final IGenerator accessorService = AccessorServiceTemplate
				.create(newLine());
		final IGenerator accessor = AccessorTemplate.create(newLine());
		final IGenerator domain = DomainTemplate.create(newLine());

		mainSourceTemplateByFilename = ImmutableMap.of( //
				SERVICE_DIR + "I%sAccessorService", accessorService,//
				DOMAIN_DIR + "%s", domain,//
				"I%s", accessor//
				);

		testSourceTemplateByFilename = ImmutableMap.of(//
				);
	}

	/**
	 * not all domain objects have IDs -- if they do NOT, then we don't
	 * generated certain files for them (e.g. services)
	 */
	@Override
	protected void generate(final File destFolder, final IGenerator template,
			final AbstractJavaContext ctxt, final String javaFileName)
			throws IOException {

		final ApiContext apiContext = (ApiContext) ctxt;
		if (apiContext.hasId()) {
			super.generate(destFolder, template, ctxt, javaFileName);
		} else {
			// NO ID -- should we still generate?
			if (shouldGenerateForNonIdObject(javaFileName)) {
				super.generate(destFolder, template, ctxt, javaFileName);
			}
		}
	}

	private boolean shouldGenerateForNonIdObject(final String javaFileName) {
		return !javaFileName.endsWith("Service");
	}

	public void generateProject(final ApiProjectDefinition def)
			throws IOException {
		if (def.hasPomDefinition()) {
			generatePom(def.getGroupId(), def.getArtifactId(),
					def.getVersion(), def.getTargetDirectory());
		}

		System.out.println("** Generating API project at "
				+ def.getTargetDirectory());

		for (final DomainObject obj : def.getObjects()) {
			final AbstractJavaContext ctxt = new ApiContext(
					def.getPackageName(), obj);
			generateMainJavaSource(srcDir(def), ctxt);
			generateTestJavaSource(testDir(def), ctxt);
		}
	}

	@Override
	protected void generateStaticMainClasses(final File destFolder,
			final AbstractJavaContext ctxt) throws IOException {
		generate(destFolder, AbstractAccessorServiceTemplate.create(newLine()),
				ctxt, SERVICE_DIR + "IAccessorService");
		generate(destFolder, AbstractDomainObjectTemplate.create(newLine()),
				ctxt, DOMAIN_DIR + "AbstractDomainObject");
		generate(destFolder, AbstractObjectTemplate.create(newLine()), ctxt,
				"AbstractObject");

	}

	@Override
	protected void generateStaticTestClasses(final File destFolder,
			final AbstractJavaContext ctxt) throws IOException {
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
	protected IGenerator getPomTemplate() {
		return ApiPomTemplate.create(newLine());
	}

}
