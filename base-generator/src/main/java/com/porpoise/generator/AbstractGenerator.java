package com.porpoise.generator;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public abstract class AbstractGenerator {

	private boolean mainStaticClassesGenerated = false;
	private boolean testStaticClassesGenerated = false;

	/**
	 * @param destFolder
	 * @param ctxt
	 * @param mainSourceTemplateByFilename
	 * @throws IOException
	 */
	public void generateMainJavaSource(final File destFolder,
			final AbstractJavaContext ctxt) throws IOException {
		if (!mainStaticClassesGenerated) {
			mainStaticClassesGenerated = true;
			generateStaticMainClasses(destFolder, ctxt);
		}

		generateAll(destFolder, ctxt, getMainSourceTemplateByFilename());
	}

	protected abstract void generateStaticMainClasses(final File destFolder,
			final AbstractJavaContext ctxt) throws IOException;

	protected abstract Map<String, IGenerator> getMainSourceTemplateByFilename();

	protected abstract Map<String, IGenerator> getTestSourceTemplateByFilename();

	public void generateTestJavaSource(final File destFolder,
			final AbstractJavaContext ctxt) throws IOException {
		if (!testStaticClassesGenerated) {
			testStaticClassesGenerated = true;
			generateStaticTestClasses(destFolder, ctxt);
		}

		generateAll(destFolder, ctxt, getTestSourceTemplateByFilename());
	}

	protected abstract void generateStaticTestClasses(final File destFolder,
			final AbstractJavaContext ctxt) throws IOException;

	private void generateAll(final File destFolder,
			final AbstractJavaContext ctxt,
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

	public void generatePom(final String groupId, final String artifactId,
			final String version, final File destDir) throws IOException {
		final PomContext ctxt = getPomContext(groupId, artifactId, version);
		generatePom(destDir, ctxt);
	}

	public void generatePom(final File destDir, final PomContext ctxt)
			throws IOException {
		final IGenerator template = getPomTemplate();
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

	protected PomContext getPomContext(final String groupId,
			final String artifactId, final String version) {
		final PomContext ctxt = new PomContext(groupId, artifactId, version);
		return ctxt;
	}

	protected abstract IGenerator getPomTemplate();

	protected static String newLine() {
		return String.format("%n");
	}

	protected void generate(final File destFolder, final IGenerator template,
			final AbstractJavaContext ctxt, final String javaFileName)
			throws IOException {
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

		if (file.exists() && !canOverwrite(file)) {
			System.out.println("\tNot clobbering file " + file);

		} else {
			Files.write(text, file, Charsets.UTF_8);
		}
	}

	protected boolean canOverwrite(final File file) {
		return false;
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

	protected static File testDir(
			final AbstractProjectDefinition parameterObject) {
		return new File(parameterObject.getTargetDirectory(), "src/test/java");
	}

	protected static File srcDir(final AbstractProjectDefinition parameterObject) {
		return new File(parameterObject.getTargetDirectory(), "src/main/java");
	}

}
