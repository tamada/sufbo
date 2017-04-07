package com.github.sufbo.entities.maven;

import java.nio.file.Path;

import com.github.sufbo.entities.ArtifactBuildException;

public class ArtifactBuilder {
    public Artifact build(Path base, Path target){
        Ids ids = createIds(base.relativize(target));
        return new Artifact(ids, target);
    }

    private Ids createIds(Path relative){
        if(relative.getNameCount() < 4)
            return tryToCreateIds(relative);
        return createIdsImpl(relative);
    }

    private Ids tryToCreateIds(Path path){
        try{
            return tryToCreateIdsImpl(new GroupId(path.getParent()),
                    path.getFileName().toString());
        } catch(Exception e){
            throw new ArtifactBuildException(path + ": not enough information");
        }
    }

    private Ids tryToCreateIdsImpl(GroupId groupId, String fileName){
        int extension = fileName.lastIndexOf('.');
        int versionDelimitor = fileName.lastIndexOf('-');
        return new Ids(groupId, new ArtifactId(fileName.substring(0, versionDelimitor)),
                    new Version(fileName.substring(versionDelimitor + 1, extension)));
    }

    private Ids createIdsImpl(Path relative){
        Path version = relative.getParent();
        Path artifact = version.getParent();
        return buildIds(artifact.getParent(), artifact, version);
    }

    private Ids buildIds(Path group, Path artifact, Path version){
        return new Ids(new GroupId(group),
                new ArtifactId(artifact.getFileName()),
                new Version(version.getFileName()));
    }
}
