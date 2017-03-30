package com.github.sufbo.stats.entities;

import com.github.sufbo.entities.java.Bytecode;
import com.github.sufbo.entities.java.ClassName;
import com.github.sufbo.entities.java.MethodInformation;
import com.github.sufbo.entities.maven.ArtifactId;
import com.github.sufbo.entities.maven.GroupId;
import com.github.sufbo.entities.maven.Version;
import com.github.sufbo.stats.entities.visitor.ItemVisitor;

public class DefaultItemStringifier implements ItemStringifier{
    private static DefaultItemStringifier INSTANCE = new DefaultItemStringifier();

    public static String toString(Item item){
        return INSTANCE.stringify(item);
    }

    public String stringify(Item item){
        StringifyVisitor visitor = new StringifyVisitor();
        item.accept(visitor);
        return visitor.stringify();
    }

    public static class StringifyVisitor implements ItemVisitor{
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
            .append(",").append(bytecode.digest())
            .append(",").append(bytecode);
        }
    }
}
