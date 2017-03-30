package com.github.sufbo.entities.visitor;

import com.github.sufbo.entities.java.Bytecode;
import com.github.sufbo.entities.java.Class;
import com.github.sufbo.entities.java.ClassName;
import com.github.sufbo.entities.java.Classes;
import com.github.sufbo.entities.java.MethodInformation;
import com.github.sufbo.entities.maven.Artifact;
import com.github.sufbo.entities.maven.ArtifactId;
import com.github.sufbo.entities.maven.Artifacts;
import com.github.sufbo.entities.maven.GroupId;
import com.github.sufbo.entities.maven.Ids;
import com.github.sufbo.entities.maven.Version;

public final class DefaultVisitor implements ArtifactVisitor{
    private ArtifactVisitor parent;

    public DefaultVisitor(ArtifactVisitor parent){
        this.parent = parent;
    }

    @Override
    public void visit(Artifact artifact) {
        parent.visit(artifact);
    }

    @Override
    public void visit(Artifacts artifacts) {
        parent.visit(artifacts);
    }


    @Override
    public void visit(Class target) {
        parent.visit(target);
    }

    @Override
    public void visit(ClassName name, MethodInformation method, Bytecode bytecode) {
        parent.visit(name, method, bytecode);
    }

    @Override
    public void visit(GroupId groupId, ArtifactId id, Version version) {
        parent.visit(groupId, id, version);
    }

    @Override
    public void visit(Ids ids, Classes javaClass) {
        parent.visit(ids, javaClass);
    }

    @Override
    public void visitFailed(Ids ids, Exception e) {
        parent.visitFailed(ids, e);
    }
}
