package com.github.sufbo.entities;

import java.nio.file.Path;

import com.github.sufbo.entities.maven.Artifacts;

public interface ArtifactsBuilder {
    Artifacts build(Path path);

    boolean acceptable(Path path);
}
