package com.porpoise.dao.generator.gen;

import java.io.File;
import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.porpoise.dao.generator.templates.DaoTemplate;

/**
 * @author Aaron
 */
public class DaoGenerator
{
    /**
     * @param destFolder
     * @param c
     * @throws IOException
     */
    public static void generate(final File destFolder, final DaoContext c) throws IOException
    {
        final File file = createFile(destFolder, c);
        final CharSequence text = new DaoTemplate().generate(c);
        Files.write(text, file, Charsets.UTF_8);
    }

    private static File createFile(final File destFolder, final DaoContext c)
    {
        final File targetFolder = createSubFolder(destFolder, c.getPackageNameAsPath());
        final File file = new File(targetFolder, c.getName() + ".java");
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
