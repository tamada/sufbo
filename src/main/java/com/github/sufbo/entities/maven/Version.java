package com.github.sufbo.entities.maven;

import java.nio.file.Path;
import java.util.Objects;

public class Version extends Id implements Comparable<Version> {
    private static final long serialVersionUID = 5088258399769333629L;

    private String[] versions;

    public Version(Path path){
        this(path.toString());
    }

    public Version(String id){
        super(id);
        versions = id.split("\\.-");
    }

    @Override
    public boolean equals(Object other){
        return other instanceof Version
                && super.equals(other);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(versions);
    }

    @Override
    public int compareTo(Version other) {
        if(equals(other))
            return 0;
        return compareVersions(other);
    }

    private int compareVersions(Version other){
        for(int i = 0; i < versions.length && i < other.versions.length; i++){
            int result = compareWith(versions[i], other.versions[i]);
            if(result != 0)
                return result;
        }
        return versions.length - other.versions.length;
    }

    private int compareWith(String item1, String item2){
        try{
            int v1 = Integer.parseInt(item1);
            int v2 = Integer.parseInt(item2);
            return v1 - v2;
        } catch(NumberFormatException e){
            return item1.compareTo(item2);
        }
    }
}
