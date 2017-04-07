package com.github.sufbo.entities.maven;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.github.sufbo.entities.ArtifactsBuilder;

public class MavenArtifactsBuilder implements ArtifactsBuilder {
    private ArtifactBuilder builder = new ArtifactBuilder();

    public Artifacts build(Path base){
        if(acceptable(base))
            return new Artifacts(stream(base));
        return Artifacts.empty();
    }

    private Stream<Artifact> stream(Path base){
        try {
            return streamImpl(base);
        } catch (IOException e) {
            return Stream.empty();
        }
    }

    private Stream<Artifact> streamImpl(Path base) throws IOException{
        return Files.walk(base)
                .filter(path -> pathEndsWith(path, ".jar"))
                .map(path -> convert(base, path));
    }

    private Artifact convert(Path base, Path target){
        return builder.build(base, target);
    }

    @Override
    public boolean acceptable(Path path) {
        return path.endsWith(
                Paths.get("repository"));
    }

    private boolean pathEndsWith(Path path, String suffix){
        return path.toString()
                .endsWith(suffix);
    }
}
