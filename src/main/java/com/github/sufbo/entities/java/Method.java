package com.github.sufbo.entities.java;

public class Method {
    private MethodInformation information;
    private Bytecode opcodes;

    public Method(MethodInformation information, Bytecode opcodes){
        this.information = information;
        this.opcodes = opcodes;
    }

    public MethodInformation information(){
        return information;
    }

    public Bytecode bytecode(){
        return opcodes;
    }
}
