package com.github.sufbo.entities.maven;

import java.nio.file.Path;

public class ArtifactId extends Id{
    private static final long serialVersionUID = 5088258399769333629L;

    public ArtifactId(Path path){
        this(path.toString());
    }

    public ArtifactId(String id){
        super(id);
    }
}
