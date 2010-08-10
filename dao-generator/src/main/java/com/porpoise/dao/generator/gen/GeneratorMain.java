package com.porpoise.dao.generator.gen;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;

import com.google.common.collect.Lists;
import com.porpoise.dao.generator.model.ColType;
import com.porpoise.dao.generator.model.Table;

public class GeneratorMain {

	public static void main(final String[] args) throws IOException {
		if (args.length == 0) {
			test();
		} else {
			usage(System.out);
		}
	}

	private static void usage(final PrintStream out) {
		out.println("Usage: GeneratorMain");
	}

	private static void test() throws IOException {

		final Collection<Table> tables = Lists.newArrayList();
		final Table tbl = new Table("Aaron");
		tbl.addColumn("ID", true, ColType.Long);
		tbl.addColumn("Name", false, ColType.String);

		final Table tbl2 = new Table("Benjamin");
		tbl2.addColumn("ID", true, ColType.Long);
		tbl2.addColumn("AARON_ID", true, ColType.Long);
		tbl2.addColumn("Name", false, ColType.String);

		tables.add(tbl);
		tables.add(tbl2);

		final File pom = new File(System.getProperty("user.dir"),
				"generator-main");
		final String packageName = "test.ing";
		new DaoGenerator().generateProject(new ProjectDefinition(tables, pom,
				packageName + ".dao", "main-test-dao", "1.0.0", packageName));
	}

}
