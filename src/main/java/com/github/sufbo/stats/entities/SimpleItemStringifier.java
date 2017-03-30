package com.github.sufbo.stats.entities;

import com.github.sufbo.entities.java.Bytecode;
import com.github.sufbo.entities.java.ClassName;
import com.github.sufbo.entities.java.MethodInformation;
import com.github.sufbo.entities.maven.ArtifactId;
import com.github.sufbo.entities.maven.GroupId;
import com.github.sufbo.entities.maven.Version;
import com.github.sufbo.stats.entities.visitor.ItemVisitor;

public class SimpleItemStringifier implements ItemStringifier{
    @Override
    public String stringify(Item item) {
        Visitor visitor = new Visitor();
        item.accept(visitor);
        return visitor.stringify();
    }

    private static final class Visitor implements ItemVisitor{
        private StringBuilder builder = new StringBuilder();

        public String stringify(){
            return new String(builder);
        }

        @Override
        public void visit(GroupId groupId, ArtifactId id, Version version) {
            builder.append(groupId).append(",")
            .append(id).append(",")
            .append(version);
        }

        @Override
        public void visit(ClassName name, MethodInformation method, Bytecode bytecode) {
            builder.append(",").append(name)
            .append(",").append(method).append(",");
            appendBytecode(bytecode);
        }

        public void appendBytecode(Bytecode bytecode) {
            builder.append(bytecode.length())
            .append(",").append(bytecode);
        }
    }
}
