package com.github.sufbo.entities.maven;

import java.nio.file.Path;

import com.github.sufbo.entities.ArtifactBuildException;

public class IdBuilder {
    public Artifact build(Path base, Path target){
        Ids ids = createIds(base.relativize(target));
        return new Artifact(ids, target);
    }

    private Ids createIds(Path relative){
        if(relative.getNameCount() < 4)
            throw new ArtifactBuildException(relative + ": not enough information");
        return createIdsImpl(relative);
    }

    private Ids createIdsImpl(Path relative){
        Path version = relative.getParent();
        Path artifact = version.getParent();
        return buildIds(artifact.getParent(), artifact.getFileName(), version.getFileName());
    }

    private Ids buildIds(Path group, Path artifact, Path version){
        return buildIds(group.toString(),
                artifact.toString(),
                version.toString());
    }

    private Ids buildIds(String groupId, String artifactId, String version){
        return new Ids(new GroupId(groupId), new ArtifactId(artifactId), new Version(version));
    }
}
