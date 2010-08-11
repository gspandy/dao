package com.lhasalimited.meteor.access;

import java.io.File;
import java.io.IOException;
import java.util.Collection;


import com.porpoise.api.generator.gen.ApiGenerator;
import com.porpoise.api.generator.gen.ApiProjectDefinition;
import com.porpoise.api.generator.model.DomainObject;
import com.porpoise.dao.generator.gen.DaoGenerator;
import com.porpoise.dao.generator.gen.ProjectDefinition;
import com.porpoise.dao.generator.model.Table;

public class MeteorGeneratorMain {

	public static void main(final String[] args) throws IOException {
		final BaseGenerator generator = new BaseGenerator();
		final Collection<Table> tables = generator.getTables();

		final File dest = new File("c:/sandbox/dao/generated");
		final File dao = new File(dest, "dao");
		final File api = new File(dest, "api");
		final String packageName = "com.lhasalimited.meteor";

		final String group = packageName + ".knowledge";
		new DaoGenerator().generateProject(new ProjectDefinition(tables, dao,
				group, "knowledge-dao", "1.0.0", packageName));

		final Collection<DomainObject> objects = ApiProjectDefinition
				.valueOf(tables);
		new ApiGenerator().generateProject(new ApiProjectDefinition(objects,
				api, group, "knowledge-api", "1.0.0", packageName));

		System.out.println("Done");
	}
}
