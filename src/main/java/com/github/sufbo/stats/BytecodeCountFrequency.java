package com.github.sufbo.stats;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.sufbo.stats.entities.Item;

public class BytecodeCountFrequency extends Stats<IOException>{
    @Override
    public void doStatistics(Stream<Item> stream) throws IOException{
        Map<Integer, Integer> frequency = stream
                .collect(Collectors.toMap(Item::bytecodeLength, item -> 1, (d1, d2) -> d1 + d2));
        printMap(frequency);
    }

    private void printMap(Map<Integer, Integer> map){
        map.entrySet().stream()
        .sorted((entry1, entry2) -> entry1.getKey() - entry2.getKey())
        .forEach(entry -> System.out.printf("%d,%d%n", entry.getKey(), entry.getValue()));
    }

    public static void main(String[] args) throws Throwable{
        BytecodeCountFrequency freq = new BytecodeCountFrequency();
        freq.run(args[0]);
    }
}
