package com.porpoise.dao.generator.gen;

import java.io.File;
import java.io.IOException;
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
import com.porpoise.dao.generator.templates.MetadataTemplate;
import com.porpoise.dao.generator.templates.SqlTemplate;

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

	@Override
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
}
