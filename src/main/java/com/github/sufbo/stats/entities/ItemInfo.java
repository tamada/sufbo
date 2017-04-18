package com.github.sufbo.stats.entities;

import com.github.sufbo.entities.ByteArray;
import com.github.sufbo.entities.java.Bytecode;
import com.github.sufbo.entities.java.ClassName;
import com.github.sufbo.entities.java.MethodInformation;
import com.github.sufbo.stats.entities.visitor.ItemVisitor;

public class ItemInfo {
    private ClassName name;
    private MethodInformation method;
    private ByteArray bytecode;

    public ItemInfo(ClassName name, MethodInformation method, ByteArray bytecode){
        this.name = name;
        this.method = method;
        this.bytecode = bytecode;
    }

    public ByteArray bytecode(){
        return bytecode;
    }

    public MethodInformation method(){
        return method;
    }

    public int bytecodeLength(){
        return bytecode.size();
    }

    public void accept(ItemVisitor visitor){
        visitor.visit(name, method, new Bytecode(bytecode));
    }

    @Override
    public String toString(){
        return new String(new StringBuilder()
                .append(name).append(",")
                .append(method).append(",")
                .append(bytecode));
    }
}
