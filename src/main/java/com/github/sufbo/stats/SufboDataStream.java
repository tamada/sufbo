package com.github.sufbo.stats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.github.sufbo.stats.entities.DefaultItemStringifier;
import com.github.sufbo.stats.entities.Item;

public class SufboDataStream {
    private Path path;

    public SufboDataStream(Path path){
        this.path = path;
    }

    public Stream<Item> stream() throws IOException{
        ItemBuilder builder = new ItemBuilder();
        return Files.lines(path)
                .map(builder::build);
    }

    public Stream<Item> filter(Predicate<Item> condition) throws IOException{
        return stream().filter(condition);
    }

    public static void main(String[] args) throws IOException{
        SufboDataStream stream = new SufboDataStream(Paths.get(args[0]));
        stream.stream()
        .sorted((item1, item2) -> item1.bytecodeLength() - item2.bytecodeLength())
        .map(DefaultItemStringifier::toString)
        .forEach(System.out::println);
    }
}
