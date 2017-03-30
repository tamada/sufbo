package com.github.sufbo.entities.java;

public class MethodInformation {
    private MethodName name;
    private Descriptor descriptor;

    public MethodInformation(MethodName name, Descriptor descriptor){
        this.name = name;
        this.descriptor = descriptor;
    }

    public MethodName name(){
        return name;
    }

    public Descriptor descriptor(){
        return descriptor;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder().append(name())
                .append(",").append(descriptor());
        return new String(sb);
    }
}
