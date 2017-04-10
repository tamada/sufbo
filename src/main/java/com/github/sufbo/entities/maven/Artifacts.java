package com.github.sufbo.entities.maven;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.sufbo.entities.visitor.ArtifactVisitor;

public class Artifacts {
    private List<Artifact> artifacts;

    public Artifacts(Stream<Artifact> stream){
        this.artifacts = stream
                .collect(Collectors.toList());
    }

    public void accept(ArtifactVisitor visitor){
        artifacts.forEach(visitor::visit);
    }

    public Stream<Artifact> stream(){
        return artifacts.stream();
    }

    public static Artifacts empty(){
        return new Artifacts(Stream.empty());
    }

    public String toString(){
        return String.format("%s(%d)", super.toString(), artifacts.size());
    }
}
