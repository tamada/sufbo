package com.github.sufbo.db;

import org.bson.Document;

import com.github.sufbo.entities.ByteArray;
import com.github.sufbo.entities.java.Bytecode;
import com.github.sufbo.entities.java.ClassName;
import com.github.sufbo.entities.java.MethodInformation;
import com.github.sufbo.entities.maven.ArtifactId;
import com.github.sufbo.entities.maven.GroupId;
import com.github.sufbo.entities.maven.Version;

public class MongoItemConverter implements ItemConverter<Document> {
    private Document document = new Document();

    @Override
    public Document build(){
        return document;
    }

    @Override
    public void visit(GroupId groupId, ArtifactId id, Version version) {
        document.append("groupId", groupId.toString());
        document.append("artifactId", id.toString());
        document.append("version", version.toString());
    }

    @Override
    public void visit(ClassName name, MethodInformation method, Bytecode bytecode) {
        document.append("className", name.toString());
        appendMethodInformation(method);
        appendBytecode(bytecode);
    }

    private void appendBytecode(Bytecode bytecode){
        ByteArray array = bytecode.buffer();
        document.append("bytecode", array.toString());
        document.append("bytecodeSize", array.size());
    }

    private void appendMethodInformation(MethodInformation method){
        document.append("methodName", method.name().toString());
        document.append("descriptor", method.descriptor().toString());
    }
}
