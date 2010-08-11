package com.porpoise.api.generator.gen;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.porpoise.api.generator.model.DomainObject;
import com.porpoise.api.generator.templates.AbstractAccessorServiceTemplate;
import com.porpoise.api.generator.templates.AbstractAssemblerTemplate;
import com.porpoise.api.generator.templates.AbstractDomainObjectTemplate;
import com.porpoise.api.generator.templates.AccessorServiceTemplate;
import com.porpoise.api.generator.templates.AccessorTemplate;
import com.porpoise.api.generator.templates.ApiPomTemplate;
import com.porpoise.api.generator.templates.AssemblerTemplate;
import com.porpoise.api.generator.templates.DomainTemplate;
import com.porpoise.api.generator.templates.RepositoryTemplate;
import com.porpoise.api.generator.templates.ServicesTemplate;
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
	private static final String ASSEMBLER_DIR = "assembler/";
	private static final String DOMAIN_DIR = "domain/";
	private static final String SERVICE_DIR = "service/";

	static {
		final IGenerator accessorService = AccessorServiceTemplate
				.create(newLine());
		final IGenerator accessor = AccessorTemplate.create(newLine());
		final IGenerator assembler = AssemblerTemplate.create(newLine());
		final IGenerator domain = DomainTemplate.create(newLine());

		mainSourceTemplateByFilename = ImmutableMap.of( //
				SERVICE_DIR + "I%sAccessorService", accessorService,//
				ASSEMBLER_DIR + "I%sAssembler", assembler,//
				DOMAIN_DIR + "%s", domain,//
				"I%sAccessor", accessor//
				);

		testSourceTemplateByFilename = ImmutableMap.of(//
				);
	}

	public void generateProject(final ApiProjectDefinition def)
			throws IOException {
		if (def.hasPomDefinition()) {
			generatePom(def.getGroupId(), def.getArtifactId(),
					def.getVersion(), def.getTargetDirectory());
		}

		for (final DomainObject obj : def.getObjects()) {
			final AbstractJavaContext ctxt = new ApiContext(
					def.getPackageName(), obj);
			generateMainJavaSourceForTable(srcDir(def), ctxt);
			generateTestJavaSourceForTable(testDir(def), ctxt);
		}
	}

	@Override
	protected void generateStaticMainClasses(final File destFolder,
			final AbstractJavaContext ctxt) throws IOException {
		generate(destFolder, AbstractAccessorServiceTemplate.create(newLine()),
				ctxt, SERVICE_DIR + "IAccessorService");
		generate(destFolder, AbstractAssemblerTemplate.create(newLine()), ctxt,
				ASSEMBLER_DIR + "AbstractAssembler");
		generate(destFolder, AbstractDomainObjectTemplate.create(newLine()),
				ctxt, DOMAIN_DIR + "AbstractDomainObject");
		generate(destFolder, RepositoryTemplate.create(newLine()), ctxt,
				"Repository");
		generate(destFolder, ServicesTemplate.create(newLine()), ctxt,
				"Services");

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
