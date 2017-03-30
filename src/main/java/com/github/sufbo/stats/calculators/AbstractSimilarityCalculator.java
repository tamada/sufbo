package com.github.sufbo.stats.calculators;

import com.github.sufbo.entities.ByteArray;
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

    public abstract Similarity calculate(ByteArray array1, ByteArray array2);
}
