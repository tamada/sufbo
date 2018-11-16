package com.github.sufbo.extractor.result;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.github.sufbo.entities.java.Bytecode;
import com.github.sufbo.entities.java.ClassName;
import com.github.sufbo.entities.java.MethodInformation;
import com.github.sufbo.entities.maven.ArtifactId;
import com.github.sufbo.entities.maven.GroupId;
import com.github.sufbo.entities.maven.Ids;
import com.github.sufbo.entities.maven.Version;
import com.github.sufbo.entities.visitor.ArtifactVisitor;

public class CsvResultWriter implements ArtifactVisitor{
    private Ids ids;
    private PrintWriter out;

    public CsvResultWriter(PrintWriter out){
        this.out = out;
    }

    @Override
    public void visit(GroupId groupId, ArtifactId id, Version version) {
        ids = new Ids(groupId, id, version);
    }

    @Override
    public void visit(ClassName name, MethodInformation method, Bytecode bytecode) {
        print(ids, name, method, bytecode.length(), bytecode);
        out.println();
    }

    private void print(Object... objects){
        out.print(Arrays.stream(objects)
                .map(item -> String.valueOf(item))
                .collect(Collectors.joining(",")));
    }
}
