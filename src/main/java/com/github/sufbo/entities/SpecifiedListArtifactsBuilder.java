package com.github.sufbo.entities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.github.sufbo.entities.maven.Artifact;
import com.github.sufbo.entities.maven.ArtifactBuilder;
import com.github.sufbo.entities.maven.Artifacts;
import com.github.sufbo.utils.LogHelper;

public class SpecifiedListArtifactsBuilder implements ArtifactsBuilder{
    private Path basePath;
    private ArtifactBuilder builder = new ArtifactBuilder();

    @Override
    public Artifacts build(Path path) {
        try(Stream<String> lines = Files.lines(path)){
            return new Artifacts(lines.map(line -> createArtifact(line)));
        } catch(IOException e){
            LogHelper.warning(getClass(), "error on building artifacts", e);
        }
        return new Artifacts(Stream.empty());
    }

    private Artifact createArtifact(String line){
        Path basePath = parseBasePath(line);
        return builder.build(basePath, Paths.get(line));
    }

    private Path parseBasePath(String path){
        if(basePath != null && path.startsWith(basePath.toString()))
            return basePath;
        return parseBasePathImpl(Paths.get(path), Paths.get("repository"));
    }

    private Path parseBasePathImpl(Path path, Path base){
        Path parent = path;
        while(parent != null && !parent.endsWith(base))
            parent = parent.getParent();
        // System.out.printf("parseBasePathImpl(%s, %s): %s%n", path, base, parent);
        return parent;
    }

    @Override
    public boolean acceptable(Path path) {
        return path.toString()
                .endsWith(".list");
    }
}
