package com.github.sufbo.entities.visitor;

import com.github.sufbo.entities.java.Bytecode;
import com.github.sufbo.entities.java.ClassName;
import com.github.sufbo.entities.java.MethodInformation;
import com.github.sufbo.entities.maven.ArtifactId;
import com.github.sufbo.entities.maven.GroupId;
import com.github.sufbo.entities.maven.Version;

public interface Visitor {
    void visit(GroupId groupId, ArtifactId id, Version version);

    void visit(ClassName name, MethodInformation method, Bytecode bytecode);
}
