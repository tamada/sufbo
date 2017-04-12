package com.github.sufbo.entities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import com.github.sufbo.entities.java.Bytecode;

public class Digest {
    private Optional<ByteArray> digest = Optional.empty();
    private Bytecode digestFrom;

    public Digest(Bytecode buffer){
        digestFrom = buffer;
    }

    public Optional<ByteArray> digest(){
        if(!digest.isPresent())
            digest = digestImpl(digestFrom.buffer());
        return digest;
    }

    private Optional<ByteArray> digestImpl(ByteArray source){
        Optional<MessageDigest> digest = algorithm("MD5");
        return digest.map(algorithm -> source.digest(algorithm));
    }

    @Override
    public String toString(){
        return digest
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
