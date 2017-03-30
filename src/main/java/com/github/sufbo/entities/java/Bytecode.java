package com.github.sufbo.entities.java;

import com.github.sufbo.entities.ByteArray;
import com.github.sufbo.entities.Digest;

public class Bytecode {
    private ByteArray buffer;

    public Bytecode(ByteArray array){
        this.buffer = new ByteArray(array);
    }

    public Bytecode(byte[] data){
        this.buffer = new ByteArray(data);
    }

    public ByteArray buffer(){
        return buffer;
    }

    public Digest digest(){
        return new Digest(this);
    }

    public int length(){
        return buffer.size();
    }

    @Override
    public String toString(){    
        return buffer.toString();
    }
}
