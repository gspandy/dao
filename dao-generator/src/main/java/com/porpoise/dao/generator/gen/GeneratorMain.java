package com.porpoise.dao.generator.gen;

import java.io.File;
import java.io.IOException;

import com.porpoise.dao.generator.model.Table;

public class GeneratorMain
{

    public static void main(final String[] args) throws IOException
    {
        final File dest = new File("/Users/Aaron/dev/sandbox/dao/dao-generator/src/test/java");

        final Table tbl = new Table("Aaron");

        final String packageName = "a.b.c";
        final DaoContext c = new DaoContext(packageName, tbl);

        DaoGenerator.generate(dest, c);
    }
}
