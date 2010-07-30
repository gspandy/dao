package com.porpoise.dao.generator.gen;

import java.io.File;
import java.io.IOException;

import com.porpoise.dao.generator.model.Column;
import com.porpoise.dao.generator.model.Table;
import com.porpoise.dao.generator.model.ColType;

public class GeneratorMain
{

    public static void main(final String[] args) throws IOException
    {
        final File dest = new File("/Users/Aaron/dev/sandbox/dao/dao-generator/src/test/java");

        final String packageName = "test.ing";

        final Table tbl = new Table("Aaron");
        tbl.addColumn("ID", true, ColType.Long);
        tbl.addColumn("Name", false, ColType.String);

        final Table tbl2 = new Table("Benjamin");
        tbl2.addColumn("ID", true, ColType.Long);
        final Column fk = tbl2.addColumn("AARON_ID", true, ColType.Long);
        tbl2.addColumn("Name", false, ColType.String);

        tbl.oneToMany(fk);

        generateForTable(dest, packageName, tbl);
        generateForTable(dest, packageName, tbl2);
    }

    private static void generateForTable(final File dest, final String packageName, final Table tbl) throws IOException
    {
        final DaoContext c = new DaoContext(packageName, tbl);
        DaoGenerator.generate(dest, c);
    }
}
