package com.github.sufbo.stats;

import com.github.sufbo.entities.ByteArray;
import com.github.sufbo.entities.java.Bytecode;
import com.github.sufbo.stats.entities.Similarity;

public interface SimilarityCalculator {
    default Similarity calculate(Bytecode bytecode1, Bytecode bytecode2){
        return calculate(bytecode1.buffer(),
                bytecode2.buffer());
    }

    String algorithm();

    Similarity calculate(ByteArray array1, ByteArray array2);
}
