package com.porpoise.dao.generator.gen;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.porpoise.dao.generator.templates.DaoTemplate;
import com.porpoise.dao.generator.templates.DtoTemplate;
import com.porpoise.dao.generator.templates.MetadataTemplate;
import com.porpoise.dao.generator.templates.SqlTemplate;

/**
 * @author Aaron
 */
public class DaoGenerator
{
    private static final Map<String, IGenerator> generatorByExtension = Maps.newHashMap();

    static
    {
        generatorByExtension.put("%sDao", new DaoTemplate());
        generatorByExtension.put("model/%sDto", new DtoTemplate());
        generatorByExtension.put("model/%sMetadata", new MetadataTemplate());
        generatorByExtension.put("%sSql", new SqlTemplate());

    }

    /**
     * @param destFolder
     * @param ctxt
     * @throws IOException
     */
    public static void generate(final File destFolder, final DaoContext ctxt) throws IOException
    {
        for (final Entry<String, IGenerator> entry : generatorByExtension.entrySet())
        {
            final IGenerator generator = entry.getValue();
            final String fileName = String.format(entry.getKey(), ctxt.getName());
            generate(destFolder, generator, ctxt, fileName);
        }
    }

    private static void generate(final File destFolder, final IGenerator template, final DaoContext ctxt, final String javaFileName)
            throws IOException
    {
        final String basePackagePath = ctxt.getPackageNameAsPath();
        final File file = createJavaFile(destFolder, basePackagePath, javaFileName);
        final CharSequence text = template.generate(ctxt);

        // check our 'subpackages' exist
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs())
        {
            throw new IllegalStateException("Could not create sub-package directory " + file.getParentFile().getAbsolutePath());
        }
        Files.write(text, file, Charsets.UTF_8);
    }

    private static File createJavaFile(final File destFolder, final String packageAsPath, final String fileName)
    {
        final File targetFolder = createSubFolder(destFolder, packageAsPath);
        final File file = new File(targetFolder, fileName + ".java");
        return file;
    }

    private static File createSubFolder(final File destFolder, final String path)
    {
        final File targetFolder = new File(checkFolder(destFolder), path);
        if (!targetFolder.exists())
        {
            if (!targetFolder.mkdirs())
            {
                throw new IllegalStateException("Couldn't create " + targetFolder);
            }
        }
        return targetFolder;
    }

    private static File checkFolder(final File destFolder)
    {
        if (!destFolder.exists())
        {
            throw new IllegalArgumentException(destFolder + " does not exit");
        }
        if (!destFolder.isDirectory())
        {
            throw new IllegalArgumentException(destFolder + " is not a directory");
        }
        return destFolder;
    }
}