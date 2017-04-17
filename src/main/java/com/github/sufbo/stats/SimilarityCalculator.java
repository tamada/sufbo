package com.github.sufbo.stats;

import java.util.stream.IntStream;

import com.github.sufbo.entities.ByteArray;
import com.github.sufbo.entities.java.Bytecode;
import com.github.sufbo.stats.entities.Similarity;

public interface SimilarityCalculator {
    default Similarity calculate(Bytecode bytecode1, Bytecode bytecode2){
        return calculate(bytecode1.buffer(),
                bytecode2.buffer());
    }

    String algorithm();

    default Similarity calculate(ByteArray array1, ByteArray array2){
        return calculate(array1.stream(), array2.stream());
    }

    Similarity calculate(IntStream stream1, IntStream stream2);
}
