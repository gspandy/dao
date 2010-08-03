package com.porpoise.dao.generator.gen;

public class PomContext
{
    private final String name;
    private final String artifactId;
    private final String groupId;
    private final String version;

    public PomContext(final String artifact, final String group, final String vers)
    {
        this(artifact, group, vers, artifact + "." + group);
    }

    public PomContext(final String artifact, final String group, final String vers, final String nameValue)
    {
        this.artifactId = artifact;
        this.groupId = group;
        this.version = vers;
        this.name = nameValue;
    }

    /**
     * @return the artifactId
     */
    public String getArtifactId()
    {
        return this.artifactId;
    }

    /**
     * @return the groupId
     */
    public String getGroupId()
    {
        return this.groupId;
    }

    /**
     * @return the version
     */
    public String getVersion()
    {
        return this.version;
    }

    public String getName()
    {
        return this.name;
    }
}
