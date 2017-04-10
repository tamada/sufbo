package com.github.sufbo.traverser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface Traverser {
    Path basePath();

    Stream<Path> stream(Filter streamFilter) throws IOException;

    default Stream<Path> stream() throws IOException{
        return stream(path -> true);
    }
}
