package com.github.sufbo.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import com.github.sufbo.stats.ItemBuilder;
import com.github.sufbo.stats.entities.Item;

public interface DatabaseStorer{
    public static final String COLLECTION_NAME = "opcodes";

    default void run(Path path) throws IOException{
        try(Stream<String> stream = Files.lines(path)){
            store(stream);
        }
    }

    default void store(Stream<String> stream){
        ItemBuilder builder = new ItemBuilder();
        stream.map(line -> builder.build(line))
        .forEach(this::store);
        // .max((item1, item2) -> item1.method().descriptor().toString().length() - item2.method().descriptor().toString().length())
        // .ifPresent(item -> System.out.printf("%s (%d)%n", item, item.method().descriptor().toString().length()));
    }

    void store(Item item);
}
