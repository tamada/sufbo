package com.github.sufbo.entities.maven;

public class GroupId extends Id{
    private static final long serialVersionUID = 5088258399769333629L;

    public GroupId(String id){
        super(id.replace('/', '.'));
    }
}
