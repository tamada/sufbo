package com.github.sufbo.entities;

import java.util.Arrays;

public class KGram {
    private byte[] kgram;

    public KGram(byte[] data, int from, int to) {
        kgram = new byte[to - from];
        System.arraycopy(data, from, kgram, 0, kgram.length);
    }

    public int toInt() {
        int value = 0;
        for (int i = kgram.length - 1; i >= 0; i--)
            value = (value << 8) | (int) (kgram[i] & 0xff);
        return value;
    }

    public int size() {
        return kgram.length;
    }

    public boolean equals(Object object) {
        return object instanceof KGram
                && Arrays.equals(kgram, ((KGram) object).kgram);
    }
}
