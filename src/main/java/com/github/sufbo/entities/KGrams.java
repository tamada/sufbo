package com.github.sufbo.entities;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class KGrams {
    private List<KGram> kgrams;

    /**
     * @see http://stackoverflow.com/questions/35164919/mapping-a-stream-of-tokens-to-a-stream-of-n-grams-in-java-8
     */
    private KGrams(int size, byte[] array){
        kgrams = IntStream.range(0, array.length - size + 1)
                .mapToObj(i -> new KGram(array, i, i + size))
                .collect(Collectors.toList());
    }

    public Stream<KGram> stream(){
        return kgrams.stream();
    }

    public static KGrams build(int size, byte[] array){
        return new KGrams(size, array);
    }
}
