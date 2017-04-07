package com.github.sufbo.entities;

import java.util.Map;

public class Frequencies<T> {
    private Map<T, Integer> frequencies;

    Frequencies(Map<T, Integer> frequencies){
        this.frequencies = frequencies;
    }

    public int frequency(T key){
        return frequencies.get(key);
    }

    public int size(){
        return frequencies.size();
    }
}
