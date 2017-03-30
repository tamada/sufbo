package com.github.sufbo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Arguments {
    private List<String> arguments;

    Arguments(List<String> args){
        arguments = new ArrayList<>(args);
    }

    public void forEach(Consumer<String> action){
        arguments.forEach(action);
    }

    public Stream<String> stream(){
        return arguments.stream();
    }
}
