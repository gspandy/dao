package com.porpoise.dao.generator.gen;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;

import com.google.common.collect.Lists;
import com.porpoise.dao.database.DbConnectionDetails;
import com.porpoise.dao.database.DbConnectionFactory;
import com.porpoise.dao.database.init.Databases;
import com.porpoise.dao.generator.model.ColType;
import com.porpoise.dao.generator.model.Column;
import com.porpoise.dao.generator.model.Table;

public class GeneratorMain
{

	public static void main(final String[] args) throws IOException
	{
		if (args.length == 0)
		{
			test();
		}
		else if (args.length == 1)
		{
			final File mdbLocation = new File(args[0]);

			final DbConnectionDetails connectionDetails = new DbConnectionDetails();
			connectionDetails.setUrl(mdbLocation.getAbsolutePath());
			final DbConnectionFactory f = Databases.ACCESS.init(connectionDetails);
			final Collection<Table> tables = AccessTables.getTables(f);

			final File pom = new File("C:/temp/dao");
			generate(tables, pom, "por.poise");

			System.out.println("Done");
		}
		else
			usage(System.out);
	}

	private static void usage(final PrintStream out)
	{
		out.println("Usage: GeneratorMain [path/to/mdb-file]");
	}

	private static void test() throws IOException
	{

		final Collection<Table> tables = Lists.newArrayList();
		final Table tbl = new Table("Aaron");
		tbl.addColumn("ID", true, ColType.Long);
		tbl.addColumn("Name", false, ColType.String);

		final Table tbl2 = new Table("Benjamin");
		tbl2.addColumn("ID", true, ColType.Long);
		final Column fk = tbl2.addColumn("AARON_ID", true, ColType.Long);
		tbl2.addColumn("Name", false, ColType.String);

		tbl.oneToMany(fk);

		tables.add(tbl);
		tables.add(tbl2);

		final File pom = new File("/Users/Aaron/dev/sandbox/dao/dao-generator");
		final String packageName = "test.ing";
		generate(tables, pom, packageName);
	}

	private static void generate(final Collection<Table> tables, final File pom, final String packageName)
			throws IOException
	{
		generate(tables, pom, new File(pom, "src/main/java"), new File(pom, "src/test/java"), packageName);
	}

	private static void generate(final Collection<Table> tables, final File pomDest, final File srcDest,
			final File testDest, final String packageName) throws IOException
	{
		if (pomDest != null)
			DaoGenerator.generatePom(packageName + ".dao", "dao", "1.0.0", pomDest);

		for (final Table table : tables)
		{
			generateMainJavaSourceForTable(srcDest, packageName, table);
			generateTestJavaSourceForTable(testDest, packageName, table);
		}
	}

	private static void generateMainJavaSourceForTable(final File mainDest, final String packageName, final Table tbl)
			throws IOException
	{
		final DaoContext c = new DaoContext(packageName, tbl);
		DaoGenerator.generateMainJavaSource(mainDest, c);
	}

	private static void generateTestJavaSourceForTable(final File testDest, final String packageName, final Table tbl)
			throws IOException
	{
		final DaoContext c = new DaoContext(packageName, tbl);
		DaoGenerator.generateTestJavaSource(testDest, c);
	}
}
