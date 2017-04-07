package com.github.sufbo.stats.calculators;

import java.util.Map;

import com.github.sufbo.entities.ByteArray;
import com.github.sufbo.stats.entities.Similarity;

public class JaccardIndexSimilarityCalculator extends AbstractSimilarityCalculator {
    public JaccardIndexSimilarityCalculator(){
        super("jaccard_index");
    }

    @Override
    public Similarity calculate(ByteArray array1, ByteArray array2) {
        Map<Integer, Integer> freq1 = frequency(array1.stream());
        Map<Integer, Integer> freq2 = frequency(array2.stream());
        return calculate(freq1, freq2);
    }

    private Similarity calculate(Map<Integer, Integer> freq1, Map<Integer, Integer> freq2){
        long unionSize = unionSize(freq1, freq1);
        long intersectSize = intersectSize(freq1, freq2);
        return new Similarity(1.0 * intersectSize / unionSize);
    }

    private long unionSize(Map<Integer, Integer> freq1, Map<Integer, Integer> freq2){
        return mergeKey(freq1, freq2).size();
    }

    private long intersectSize(Map<Integer, Integer> freq1, Map<Integer, Integer> freq2){
        return freq1.keySet().stream()
                .filter(key -> freq2.containsKey(key))
                .count();
    }
}
