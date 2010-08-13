package com.porpoise.dao.generator.gen;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.porpoise.dao.generator.model.Table;
import com.porpoise.dao.generator.templates.AbstractDaoServiceTemplate;
import com.porpoise.dao.generator.templates.AbstractDaoTestTemplate;
import com.porpoise.dao.generator.templates.AbstractDtoTemplate;
import com.porpoise.dao.generator.templates.AbstractDtoTestTemplate;
import com.porpoise.dao.generator.templates.DaoApiImplTemplate;
import com.porpoise.dao.generator.templates.DaoPomTemplate;
import com.porpoise.dao.generator.templates.DaoTemplate;
import com.porpoise.dao.generator.templates.DaoTestTemplate;
import com.porpoise.dao.generator.templates.DomainObjectsTemplate;
import com.porpoise.dao.generator.templates.DtoTemplate;
import com.porpoise.dao.generator.templates.DtoTestTemplate;
import com.porpoise.dao.generator.templates.FindKeyTemplate;
import com.porpoise.dao.generator.templates.GeneratorTemplate;
import com.porpoise.dao.generator.templates.MetadataTemplate;
import com.porpoise.dao.generator.templates.SqlTemplate;
import com.porpoise.generator.AbstractGenerator;
import com.porpoise.generator.AbstractJavaContext;
import com.porpoise.generator.AbstractProjectDefinition;
import com.porpoise.generator.IGenerator;
import com.porpoise.generator.PomContext;

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
				"impl/%sServiceImpl", new DaoApiImplTemplate(),//
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
	protected PomContext getPomContext(final String groupId,
			final String artifactId, final String version) {
		final PomContext ctxt = super.getPomContext(groupId, artifactId,
				version);
		return ctxt;
	}

	@Override
	protected void generateStaticMainClasses(final File destFolder,
			final AbstractJavaContext ctxt) throws IOException {
		generate(destFolder, new FindKeyTemplate(), ctxt, "assembler/FindKey");
		generate(destFolder, new AbstractDtoTemplate(), ctxt,
				"model/AbstractDto");
		generate(destFolder, new AbstractDaoServiceTemplate(), ctxt,
				"impl/AbstractDaoService");
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
		final File srcDir = srcDir(def);
		generateProject(def.getTables(), def.getTargetDirectory(),
				def.getGroupId(), def.getArtifactId(), srcDir, testDir(def),
				def.getPackageName());

		final IGenerator generator = new DomainObjectsTemplate();
		final DomainObjectContext context = new DomainObjectContext(
				def.getPackageName(), def.getTables());
		generate(srcDir, generator, context, "assembler/DomainObjects");
	}

	/**
	 * not all domain objects have IDs -- if they do NOT, then we don't
	 * generated certain files for them (e.g. services)
	 */
	@Override
	protected void generate(final File destFolder, final IGenerator template,
			final AbstractJavaContext ctxt, final String javaFileName)
			throws IOException {

		boolean proceed = true;
		if (ctxt instanceof DaoContext) {
			final DaoContext daoContext = (DaoContext) ctxt;
			{
				proceed = daoContext.hasIdField()
						|| shouldGenerateForNonIdObject(javaFileName);
			}
		}

		if (proceed) {
			super.generate(destFolder, template, ctxt, javaFileName);
		}
	}

	private boolean shouldGenerateForNonIdObject(final String javaFileName) {
		return !javaFileName.endsWith("ServiceImpl");
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
			System.out.println("** Generating pom at " + pomDest);
			generatePom(group, artifact, "1.0.0", pomDest);
		}
		System.out.println("** Generating DAO source at " + pomDest);

		for (final Table table : tables) {
			final AbstractJavaContext ctxt = newContext(packageName, table);
			generateMainJavaSource(srcDest, ctxt);
			generateTestJavaSource(testDest, ctxt);
		}
	}
}
