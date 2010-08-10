package com.porpoise.dao.generator.gen;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import com.porpoise.dao.generator.model.Table;
import com.porpoise.dao.generator.templates.AbstractDaoTestTemplate;
import com.porpoise.dao.generator.templates.AbstractDtoTemplate;
import com.porpoise.dao.generator.templates.DaoTemplate;
import com.porpoise.dao.generator.templates.DaoTestTemplate;
import com.porpoise.dao.generator.templates.DtoTemplate;
import com.porpoise.dao.generator.templates.GeneratorTemplate;
import com.porpoise.dao.generator.templates.MetadataTemplate;
import com.porpoise.dao.generator.templates.PomTemplate;
import com.porpoise.dao.generator.templates.SqlTemplate;

/**
 * @author Aaron
 */
public class DaoGenerator {
	private static final Map<String, IGenerator> mainSourceTemplateByFilename;
	private static final Map<String, IGenerator> testSourceTemplateByFilename;

	static {
		mainSourceTemplateByFilename = ImmutableMap.of(//
				"%sDao", new DaoTemplate(),//
				"model/%sDto", new DtoTemplate(),// /
				"model/%sMetadata", new MetadataTemplate(),//
				"%sSql", new SqlTemplate()//
				);

		final IGenerator testGen = new DaoTestTemplate();
		testSourceTemplateByFilename = ImmutableMap.of(//
				"%sDaoTest", testGen//
				);
	}

	public static void generatePom(final String artifactId,
			final String groupId, final String version, final File destDir)
			throws IOException {
		final IGenerator template = PomTemplate.create(newLine());
		final PomContext ctxt = new PomContext(artifactId, groupId, version);
		final CharSequence pom = template.generate(ctxt);
		File target;
		if (destDir.getName().endsWith("pom.xml")) {
			assert destDir.isFile();
			target = destDir;
		} else {
			if (!destDir.exists() && !destDir.mkdirs()) {
				throw new IllegalArgumentException(
						"Couldn't create destination directory " + destDir);
			}
			assert destDir.isDirectory();
			target = new File(destDir, "pom.xml");
		}
		Files.write(pom, target, Charsets.UTF_8);
	}

	private static String newLine() {
		return String.format("%n");
	}

	/**
	 * @param destFolder
	 * @param ctxt
	 * @throws IOException
	 */
	public static void generateMainJavaSource(final File destFolder,
			final DaoContext ctxt) throws IOException {
		final IGenerator generator = AbstractDtoTemplate.create(newLine());
		generate(destFolder, generator, ctxt, "model/AbstractDto");

		generateAll(destFolder, ctxt, mainSourceTemplateByFilename);
	}

	public static void generateTestJavaSource(final File destFolder,
			final DaoContext ctxt) throws IOException {
		generateAll(destFolder, ctxt, testSourceTemplateByFilename);

		generateStaticTestClasses(destFolder, ctxt);
	}

	private static void generateStaticTestClasses(final File destFolder,
			final DaoContext ctxt) throws IOException {
		final IGenerator generator = AbstractDaoTestTemplate.create(newLine());
		generate(destFolder, generator, ctxt, "AbstractDaoTest");
	}

	private static void generateAll(final File destFolder,
			final DaoContext ctxt,
			final Map<String, IGenerator> templateByFilename)
			throws IOException {
		for (final Entry<String, IGenerator> entry : templateByFilename
				.entrySet()) {
			final IGenerator generator = entry.getValue();
			final String fileName = String.format(entry.getKey(),
					ctxt.getJavaName());
			generate(destFolder, generator, ctxt, fileName);
		}
	}

	private static void generate(final File destFolder,
			final IGenerator template, final AbstractJavaContext ctxt,
			final String javaFileName) throws IOException {
		final String basePackagePath = ctxt.getPackageNameAsPath();
		final File file = createJavaFile(destFolder, basePackagePath,
				javaFileName);
		final CharSequence text = template.generate(ctxt);

		// check our 'subpackages' exist
		if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
			throw new IllegalStateException(
					"Could not create sub-package directory "
							+ file.getParentFile().getAbsolutePath());
		}
		Files.write(text, file, Charsets.UTF_8);
	}

	private static File createJavaFile(final File destFolder,
			final String packageAsPath, final String fileName) {
		final File targetFolder = createSubFolder(destFolder, packageAsPath);
		final File file = new File(targetFolder, fileName + ".java");
		return file;
	}

	private static File createSubFolder(final File destFolder, final String path) {
		final File targetFolder = new File(checkFolder(destFolder), path);
		if (!targetFolder.exists()) {
			if (!targetFolder.mkdirs()) {
				throw new IllegalStateException("Couldn't create "
						+ targetFolder);
			}
		}
		return targetFolder;
	}

	private static File checkFolder(final File destFolder) {
		if (!destFolder.exists()) {
			if (!destFolder.mkdirs()) {
				throw new IllegalArgumentException(destFolder
						+ " does not exit and could not create the directory");
			}
		}
		if (!destFolder.isDirectory()) {
			throw new IllegalArgumentException(destFolder
					+ " is not a directory");
		}
		return destFolder;
	}

	public static void generateGeneratorProject(final ProjectDefinition def)
			throws IOException {
		if (def.hasPomDefinition()) {
			generatePom(def.getArtifactId(), def.getGroupId(),
					def.getVersion(), def.getTargetDirectory());
		}

		final AbstractJavaContext ctxt = new GeneratorContext(
				def.getPackageName(), def.getTables());
		generate(srcDir(def), new GeneratorTemplate(), ctxt, "Generator");
	}

	/**
	 * generate the given tables and pom
	 * 
	 * @param def
	 *            TODO
	 * 
	 * @throws IOException
	 */
	public static void generateProject(final ProjectDefinition def)
			throws IOException {
		generateProject(def.getTables(), def.getTargetDirectory(),
				def.getArtifactId(), def.getGroupId(), srcDir(def),
				testDir(def), def.getPackageName());
	}

	private static File testDir(final ProjectDefinition parameterObject) {
		return new File(parameterObject.getTargetDirectory(), "src/test/java");
	}

	private static File srcDir(final ProjectDefinition parameterObject) {
		return new File(parameterObject.getTargetDirectory(), "src/main/java");
	}

	private static void generateProject(final Collection<Table> tables,
			final File pomDest, final String artifact, final String group,
			final File srcDest, final File testDest, final String packageName)
			throws IOException {
		if (pomDest != null) {
			generatePom(artifact, group, "1.0.0", pomDest);
		}

		for (final Table table : tables) {
			generateMainJavaSourceForTable(srcDest, packageName, table);
			generateTestJavaSourceForTable(testDest, packageName, table);
		}
	}

	private static void generateMainJavaSourceForTable(final File mainDest,
			final String packageName, final Table tbl) throws IOException {
		final DaoContext c = new DaoContext(packageName, tbl);
		generateMainJavaSource(mainDest, c);
	}

	private static void generateTestJavaSourceForTable(final File testDest,
			final String packageName, final Table tbl) throws IOException {
		final DaoContext c = new DaoContext(packageName, tbl);
		generateTestJavaSource(testDest, c);
	}

}
