package com.github.sufbo.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class FrequencyCollector<T> implements Collector<T, Map<T, Integer>, Frequencies<T>> {

    @Override
    public Supplier<Map<T, Integer>> supplier() {
        return () -> new HashMap<>();
    }

    @Override
    public BiConsumer<Map<T, Integer>, T> accumulator() {
        return (map, key) -> map.merge(key, 1, (v1, v2) -> v1 + v2); 
    }

    @Override
    public BinaryOperator<Map<T, Integer>> combiner() {
        return (map1, map2) -> {
            map2.forEach((key, value) -> map1.merge(key, value, (v1, v2) -> v1 + v2));
            return map1;
        };
    }

    @Override
    public Function<Map<T, Integer>, Frequencies<T>> finisher() {
        return map -> new Frequencies<>(map);
    }

    @Override
    public Set<Collector.Characteristics> characteristics() {
        return new HashSet<>();
    }
    
}
