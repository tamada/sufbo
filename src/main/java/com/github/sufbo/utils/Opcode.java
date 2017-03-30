package com.github.sufbo.utils;

import java.util.Objects;

public class Opcode {
    public static final Opcode NOT_FOUND = new Opcode(-1, "not_found");
    private int code;
    private String name;

    public Opcode(int code, String name){
        this.code = code;
        this.name = name;
    }

    public String name(){
        return name;
    }

    public int opcode(){
        return code;
    }

    public boolean is(int code){
        return code == this.code;
    }

    public boolean is(String name){
        return Objects.equals(this.name, name);
    }
}
