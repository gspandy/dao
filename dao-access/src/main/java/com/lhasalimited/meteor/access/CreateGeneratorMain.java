package com.lhasalimited.meteor.access;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;

import com.porpoise.dao.database.DbConnectionDetails;
import com.porpoise.dao.database.DbConnectionFactory;
import com.porpoise.dao.database.init.Databases;
import com.porpoise.dao.generator.gen.DaoGenerator;
import com.porpoise.dao.generator.gen.GeneratorContext;
import com.porpoise.dao.generator.gen.ProjectDefinition;
import com.porpoise.dao.generator.gen.access.AccessTables;
import com.porpoise.dao.generator.model.Table;

/**
 * This application reads some tables in from an access database,
 * 
 * then generates a generator project which will contain those tables as
 * hard-coded, in-memory values. This will allow you to tweak/change the tables
 * in java code instead of having to make the database reader/inferrer ever more
 * intelligent
 * 
 * @author Aaron
 * 
 */
public class CreateGeneratorMain {

	public static void main(final String[] args) throws IOException {
		if (args.length == 2) {
			final long start = System.currentTimeMillis();

			final File mdbLocation = new File(args[0]);
			final String packageName = args[1];

			final ProjectDefinition def = createProjectDefinition(packageName,
					mdbLocation);

			// generate a generator project
			new DaoGenerator().generateGeneratorProject(def,
					new GeneratorContext(packageName, def.getTables()));

			final long end = System.currentTimeMillis();
			System.out.println(String.format("Completed in %dms", end - start));
		} else {
			usage(System.out);
		}
	}

	private static ProjectDefinition createProjectDefinition(
			final String packageName, final File mdbLocation) {
		final DbConnectionDetails connectionDetails = new DbConnectionDetails();
		connectionDetails.setUrl(mdbLocation.getAbsolutePath());
		final DbConnectionFactory f = Databases.ACCESS.init(connectionDetails);
		final Collection<Table> tables = AccessTables.getTables(f);

		final File dest = new File(System.getProperty("user.dir"));
		final ProjectDefinition def = new ProjectDefinition(tables, dest,
				packageName);
		return def;
	}

	private static void usage(final PrintStream out) {
		out.println("Usage: CreateGeneratorMain [path/to/access/mdb-file] [dot.separated.base.package]");
	}

}
