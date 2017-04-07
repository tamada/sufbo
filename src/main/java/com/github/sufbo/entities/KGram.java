package com.github.sufbo.entities;

import java.util.Arrays;

public class KGram {
    private byte[] kgram;

    public KGram(byte[] data, int from, int to){
        kgram = new byte[to - from];
        System.arraycopy(data, from, kgram, 0, kgram.length);
    }

    public int size(){
        return kgram.length;
    }

    public boolean equals(Object object){
        return object instanceof KGram
                && Arrays.equals(kgram, ((KGram)object).kgram);
    }
}
