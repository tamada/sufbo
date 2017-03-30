package com.github.sufbo.entities;

import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

import com.github.sufbo.entities.maven.Artifact;
import com.github.sufbo.entities.maven.Artifacts;
import com.github.sufbo.entities.maven.Ids;

public class SimpleArtifactsBuilder implements ArtifactsBuilder {
    private IdsProvider ids = () -> { throw new EmptyIdsException("empty ids"); };

    public SimpleArtifactsBuilder(){
    }

    public SimpleArtifactsBuilder(Optional<Ids> optional){
        ids = () -> optional.orElseThrow(() -> new EmptyIdsException("empty ids"));
    }

    public void update(IdsProvider provider){
        this.ids = provider;
    }

    @Override
    public Artifacts build(Path path){
        if(acceptable(path))
            return new Artifacts(Stream.of(new Artifact(ids.provide(), path)));
        return Artifacts.empty();
    }

    @Override
    public boolean acceptable(Path path) {
        String pathString = path.toString();
        return pathString.endsWith(".jar")
                || pathString.endsWith(".zip");
    }
}
