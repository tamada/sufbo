package com.github.sufbo.stats.calculators;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.github.sufbo.stats.SimilarityCalculator;
import com.github.sufbo.stats.entities.Similarity;

public abstract class AbstractSimilarityCalculator implements SimilarityCalculator {
    private String algorithm;

    public AbstractSimilarityCalculator(String algorithm){
        this.algorithm = algorithm;
    }

    public final String algorithm(){
        return algorithm;
    }

    public abstract Similarity calculate(IntStream stream1, IntStream stream2);

    protected Set<Integer> mergeKey(Map<Integer, Integer> freq1, Map<Integer, Integer> freq2){
        Set<Integer> set = new HashSet<>(freq1.keySet());
        set.addAll(freq2.keySet());
        return set;
    }

    protected Map<Integer, Integer> frequency(IntStream stream){
        return stream
                .boxed().collect(Collectors
                        .toMap(Function.identity(), v -> 1, Integer::sum));
    }
}
