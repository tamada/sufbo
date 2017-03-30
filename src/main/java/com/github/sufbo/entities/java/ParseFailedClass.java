package com.github.sufbo.entities.java;

import com.github.sufbo.entities.visitor.ArtifactVisitor;

public class ParseFailedClass extends Class {
    private Exception failure;

    public ParseFailedClass(ClassName name, Exception exception){
        super(name, Methods.empty());
        this.failure = exception;
    }

    public void accept(ArtifactVisitor visitor){
        visitor.visitFailed(name(), failure);
    }
}
