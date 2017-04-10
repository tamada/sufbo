package com.github.sufbo.entities.maven;

import java.io.Serializable;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.sufbo.entities.visitor.Visitor;

public class Ids implements Serializable, Comparable<Ids>{
    private static final long serialVersionUID = 2026372107254911573L;

    GroupId groupId;
    ArtifactId artifactId;
    Version version;

    public Ids(GroupId groupId, ArtifactId artifactId, Version version){
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public int compareTo(Ids other){
        if(sameArtifact(other)){
            return new VersionComparator().compare(version, other.version);
        }
        return compareIds(other.groupId, other.artifactId);
    }

    private int compareIds(GroupId otherGroupId, ArtifactId otherArtifactId){
        int v1 = groupId.toString().compareTo(otherGroupId.toString());
        if(v1 != 0)
            return v1;
        return artifactId.toString().compareTo(otherArtifactId.toString());
    }

    public boolean sameArtifact(Ids other){
        return Objects.equals(groupId, other.groupId)
                && Objects.equals(artifactId, other.artifactId);
    }

    public boolean isDifferentVersion(Ids other){
        return sameArtifact(other)
                && !Objects.equals(version, other.version);
    }

    public void accept(Visitor visitor){
        visitor.visit(groupId, artifactId, version);
    }

    @Override
    public int hashCode(){
        return Objects.hash(groupId, artifactId, version);
    }

    @Override
    public boolean equals(Object object){
        return object instanceof Ids
                && sameArtifact((Ids)object)
                && Objects.equals(version, ((Ids)object).version);
    }

    private Stream<Id> stream(){
        return Stream.of(groupId, artifactId, version);
    }

    @Override
    public String toString(){
        return stream().map(Id::toString)
                .collect(Collectors.joining(","));
    }

    public static Ids empty(){
        return new Ids(new GroupId(""), new ArtifactId(""), new Version(""));
    }
}
