package com.github.sufbo.entities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import com.github.sufbo.entities.java.Bytecode;

public class Digest {
    private Optional<ByteArray> buffer;

    public Digest(Bytecode buffer){
        this.buffer = digest(buffer.buffer());
    }

    public Optional<ByteArray> digest(ByteArray source){
        Optional<MessageDigest> digest = algorithm("MD5");
        return digest.map(algorithm -> source.digest(algorithm));
    }

    @Override
    public String toString(){
        return buffer
                .map(bytes -> bytes.toString())
                .orElse("");
    }

    private Optional<MessageDigest> algorithm(String algorithm){
        try {
            return Optional.of(MessageDigest.getInstance(algorithm));
        } catch (NoSuchAlgorithmException e) {
            return Optional.empty();
        }
    }
}
