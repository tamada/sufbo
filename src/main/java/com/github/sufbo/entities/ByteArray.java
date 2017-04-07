package com.github.sufbo.entities;

import java.security.MessageDigest;
import java.util.stream.IntStream;

public class ByteArray {
    private byte[] array;

    public ByteArray(ByteArray array){
        this(array.array);
    }

    public ByteArray(int... source){
        array = new byte[source.length];
        for(int i = 0; i < source.length; i++)
            array[i] = (byte)(source[i] & 0xff);
    }

    public ByteArray(byte[] source){
        this.array = new byte[source.length];
        System.arraycopy(source, 0, array, 0, source.length);
    }

    public ByteArray digest(MessageDigest digest){
        digest.update(array);
        return new ByteArray(digest.digest());
    }

    public KGrams toKGrams(int size){
        return KGrams.build(size, array);
    }

    public int size(){
        return array.length;
    }

    public IntStream stream(){
        return IntStream.range(0, size())
                .map(index -> array[index] & 0xff);
    }

    @Override
    public String toString(){
        return new String(stream().mapToObj(value -> format(value))
                .reduce(new StringBuilder(), (first, second) -> first.append(second)));
    }

    private StringBuilder format(int value){
        StringBuilder sb = new StringBuilder(value < 16? "0": "");
        return sb.append(Integer.toHexString(value));
    }

    public static ByteArray parse(String string){
        byte[] data = new byte[string.length() / 2];
        for(int i = 0; i < data.length; i++)
            data[i] = (byte)Integer.parseInt(string.substring(i * 2, i * 2 + 2), 16);
        return new ByteArray(data);
    }
}
