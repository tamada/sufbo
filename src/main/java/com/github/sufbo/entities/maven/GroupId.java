package com.github.sufbo.entities.maven;

import java.nio.file.Path;

public class GroupId extends Id{
    private static final long serialVersionUID = 5088258399769333629L;

    public GroupId(Path path){
        this(path.toString());
    }

    public GroupId(String id){
        super(id.replace('/', '.'));
    }
}
