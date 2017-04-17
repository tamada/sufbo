package com.github.sufbo.stats.calculators;

import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import com.github.sufbo.stats.entities.Similarity;

public class CosineSimilarityCalculator extends AbstractSimilarityCalculator {
    public CosineSimilarityCalculator(){
        super("cosine");
    }

    @Override
    public Similarity calculate(IntStream stream1, IntStream stream2){
        Map<Integer, Integer> freq1 = frequency(stream1);
        Map<Integer, Integer> freq2 = frequency(stream2);
        return calculate(freq1, freq2);
    }

    private Similarity calculate(Map<Integer, Integer> freq1, Map<Integer, Integer> freq2){
        Set<Integer> keys = mergeKey(freq1, freq2);
        return new Similarity(innerProduct(keys, freq1, freq2), (norm(freq1) * norm(freq2)));
    }

    private int innerProduct(Set<Integer> keys, Map<Integer, Integer> freq1, Map<Integer, Integer> freq2){
        return keys.stream()
                .map(key -> freq1.getOrDefault(key, 0) * freq2.getOrDefault(key, 0))
                .reduce(0, (v1, v2) -> v1 + v2);
    }

    private double norm(Map<Integer, Integer> freq){
        return Math.sqrt(freq.values().stream()
                .map(value -> value * value)
                .reduce(0, (value1, value2) -> value1 + value2));
    }
}
