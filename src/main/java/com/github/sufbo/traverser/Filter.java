package com.github.sufbo.traverser;

import java.nio.file.Path;
import java.util.Arrays;

@FunctionalInterface
public interface Filter {
    default boolean accept(Path path){
        return accept(path.toString());
    }

    boolean accept(String path);

    default boolean accept(String... strings){
        return Arrays.stream(strings)
                .anyMatch(item -> accept(item));
    }
}
