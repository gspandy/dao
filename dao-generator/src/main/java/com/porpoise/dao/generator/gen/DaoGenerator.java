package com.porpoise.dao.generator.gen;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.porpoise.dao.generator.model.Table;
import com.porpoise.dao.generator.templates.AbstractDaoTestTemplate;
import com.porpoise.dao.generator.templates.AbstractDtoTemplate;
import com.porpoise.dao.generator.templates.AbstractDtoTestTemplate;
import com.porpoise.dao.generator.templates.DaoPomTemplate;
import com.porpoise.dao.generator.templates.DaoTemplate;
import com.porpoise.dao.generator.templates.DaoTestTemplate;
import com.porpoise.dao.generator.templates.DtoTemplate;
import com.porpoise.dao.generator.templates.DtoTestTemplate;
import com.porpoise.dao.generator.templates.GeneratorTemplate;
import com.porpoise.dao.generator.templates.MetadataTemplate;
import com.porpoise.dao.generator.templates.SqlTemplate;
import com.porpoise.generator.AbstractGenerator;
import com.porpoise.generator.AbstractJavaContext;
import com.porpoise.generator.AbstractProjectDefinition;
import com.porpoise.generator.IGenerator;

/**
 * @author Aaron
 */
public class DaoGenerator extends AbstractGenerator {
	private static final Map<String, IGenerator> mainSourceTemplateByFilename;
	private static final Map<String, IGenerator> testSourceTemplateByFilename;

	static {
		mainSourceTemplateByFilename = ImmutableMap.of(//
				"%sDao", new DaoTemplate(),//
				"model/%sDto", new DtoTemplate(),// /
				"model/%sMetadata", new MetadataTemplate(),//
				"%sSql", new SqlTemplate()//
				);

		testSourceTemplateByFilename = ImmutableMap.of(//
				"%sDaoTest", new DaoTestTemplate(),//
				"%sDtoTest", new DtoTestTemplate()//
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

	protected AbstractJavaContext newContext(final String packageName,
			final Table tbl) {
		final AbstractJavaContext c = new DaoContext(packageName, tbl);
		return c;
	}

	@Override
	protected void generateStaticTestClasses(final File destFolder,
			final AbstractJavaContext ctxt) throws IOException {
		generate(destFolder, AbstractDaoTestTemplate.create(newLine()), ctxt,
				"AbstractDaoTest");
		generate(destFolder, AbstractDtoTestTemplate.create(newLine()), ctxt,
				"AbstractDtoTest");
	}

	@Override
	protected IGenerator getPomTemplate() {
		return DaoPomTemplate.create(newLine());
	}

	@Override
	protected void generateStaticMainClasses(final File destFolder,
			final AbstractJavaContext ctxt) throws IOException {
		final IGenerator generator = AbstractDtoTemplate.create(newLine());
		generate(destFolder, generator, ctxt, "model/AbstractDto");
	}

	/**
	 * generate the given tables and pom
	 * 
	 * @param def
	 *            TODO
	 * 
	 * @throws IOException
	 */
	public void generateProject(final ProjectDefinition def) throws IOException {
		generateProject(def.getTables(), def.getTargetDirectory(),
				def.getGroupId(), def.getArtifactId(), srcDir(def),
				testDir(def), def.getPackageName());
	}

	/**
	 * From a given project definition, generate a project which will in-turn be
	 * able to generate other projects based on the tables in the definition.
	 * 
	 * By going about this route, the tables read from the database may be
	 * further fine-tuned within the java-code generator project itself.
	 * 
	 * @param def
	 * @throws IOException
	 */
	public void generateGeneratorProject(final AbstractProjectDefinition def,
			final GeneratorContext ctxt) throws IOException {
		if (def.hasPomDefinition()) {
			generatePom(def.getGroupId(), def.getArtifactId(),
					def.getVersion(), def.getTargetDirectory());
		}

		generate(srcDir(def), new GeneratorTemplate(), ctxt, "BaseGenerator");
	}

	private void generateProject(final Collection<Table> tables,
			final File pomDest, final String group, final String artifact,
			final File srcDest, final File testDest, final String packageName)
			throws IOException {
		if (pomDest != null) {
			generatePom(group, artifact, "1.0.0", pomDest);
		}

		for (final Table table : tables) {
			final AbstractJavaContext ctxt = newContext(packageName, table);
			generateMainJavaSourceForTable(srcDest, ctxt);
			generateTestJavaSourceForTable(testDest, ctxt);
		}
	}
}
