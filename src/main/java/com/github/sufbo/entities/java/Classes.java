package com.github.sufbo.entities.java;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.sufbo.entities.visitor.ArtifactVisitor;

public class Classes {
    private List<Class> classes;

    public Classes(Stream<Class> stream){
        classes = stream
                .collect(Collectors.toList());
    }

    public void accept(ArtifactVisitor visitor){
        classes.forEach(target -> visitor.visit(target));
    }
}
