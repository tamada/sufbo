package com.github.sufbo.extractor;

public class NotSupportedArtifactsException extends RuntimeException {
    private static final long serialVersionUID = 1566721630664257843L;

    public NotSupportedArtifactsException(String message){
        super(message);
    }
}
