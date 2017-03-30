package com.github.sufbo.stats.entities;

import com.github.sufbo.entities.java.Bytecode;
import com.github.sufbo.entities.java.ClassName;
import com.github.sufbo.entities.java.MethodInformation;
import com.github.sufbo.stats.entities.visitor.ItemVisitor;

public class ItemInfo {
    private ClassName name;
    private MethodInformation method;
    private Bytecode bytecode;

    public ItemInfo(ClassName name, MethodInformation method, Bytecode bytecode){
        this.name = name;
        this.method = method;
        this.bytecode = bytecode;
    }

    public Bytecode bytecode(){
        return bytecode;
    }

    public int bytecodeLength(){
        return bytecode.length();
    }

    public void accept(ItemVisitor visitor){
        visitor.visit(name, method, bytecode);
    }

    @Override
    public String toString(){
        return new String(new StringBuilder()
                .append(name).append(",")
                .append(method).append(",")
                .append(bytecode));
    }
}
