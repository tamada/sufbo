package com.github.sufbo.entities;

public class ArtifactBuildException extends RuntimeException {
    private static final long serialVersionUID = 3430598096243571726L;

    public ArtifactBuildException(String message){
        super(message);
    }
}
