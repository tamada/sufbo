package com.github.sufbo.entities.java;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Methods {
    private List<Method> methods;

    public Methods(Stream<Method> stream){
        methods = stream.collect(
                Collectors.toList());
    }

    public void forEach(Consumer<Method> action){
        methods.forEach(action);
    }

    public static Methods empty(){
        return new Methods(Stream.empty());
    }
}
