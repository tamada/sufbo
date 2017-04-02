package com.github.sufbo.stats;

import java.io.IOException;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.github.sufbo.stats.entities.DefaultItemStringifier;
import com.github.sufbo.stats.entities.Item;

public class SufboDataStream {
    private Argf argf;

    public SufboDataStream(Argf argf){
        this.argf = argf;
    }

    public Stream<Item> stream() throws IOException{
        ItemBuilder builder = new ItemBuilder();
        return argf.stream()
                .map(builder::build);
    }

    public Stream<Item> filter(Predicate<Item> condition) throws IOException{
        return stream().filter(condition);
    }

    public static void main(String[] args) throws IOException{
        SufboDataStream sufboStream = new SufboDataStream(new Argf(args));
        try(Stream<Item> stream = sufboStream.stream()){
            stream.map(DefaultItemStringifier::toString)
            .forEach(System.out::println);
        }
    }
}
