package com.github.sufbo.stats;

import java.io.IOException;
import java.util.stream.Stream;

import com.github.sufbo.stats.entities.Item;

public class Sorter extends Stats<IOException>{
    @Override
    public void doStatistics(Stream<Item> stream) throws IOException {
        stream.sorted((item1, item2) -> item1.bytecodeLength() - item2.bytecodeLength())
        .forEach(System.out::println);
    }

    public static void main(String[] args) throws Throwable{
        Sorter sorter = new Sorter();
        sorter.run(args);
    }
}
