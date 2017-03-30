package com.github.sufbo.stats.calculators;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.github.sufbo.stats.SimilarityCalculator;

public class SimilarityCalculatorFactory {
    private Map<String, SimilarityCalculator> calculators = new HashMap<>();

    public SimilarityCalculatorFactory(){
        register(new LevenshteinCalculator());
    }

    public Optional<SimilarityCalculator> calculator(String algorithm){
        return Optional.ofNullable(calculators.get(algorithm));
    }

    public SimilarityCalculator calculator(String algorithm, SimilarityCalculator defaultCalculator){
        return calculators.getOrDefault(algorithm, defaultCalculator);
    }

    public SimilarityCalculator calculatorOrDefault(String algorithm){
        return calculators.getOrDefault(algorithm, new LevenshteinCalculator());
    }

    private void register(SimilarityCalculator calculator){
        calculators.put(calculator.algorithm(), calculator);
    }
}
