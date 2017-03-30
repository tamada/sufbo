package com.github.sufbo.entities.java;

import java.util.function.Consumer;

import com.github.sufbo.entities.visitor.Visitor;

public class Class {
    private ClassName name;
    private Methods methods;

    public Class(ClassName name, Methods methods){
        this.name = name;
        this.methods = methods;
    }

    public ClassName name(){
        return name;
    }

    public void forEach(Consumer<Method> action){
        methods.forEach(action);
    }

    public void accept(Visitor visitor){
        methods.forEach(method -> visitor
                .visit(name, method.information(),
                        method.bytecode()));
    }
}
