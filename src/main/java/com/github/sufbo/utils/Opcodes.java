package com.github.sufbo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Opcodes {
    private Map<Integer, Opcode> opcodes = new HashMap<>();

    public Opcodes(){
        try{
            parseDefinition(getClass().getResource("/resources/bytecode.def"));
        } catch(IOException e){
            throw new InternalError(e);
        }
    }

    public Opcode of(int code){
        return opcodes.getOrDefault(code, Opcode.NOT_FOUND);
    }

    public Opcode of(String name){
        return opcodes.values().stream()
                .filter(opcode -> opcode.is(name))
                .reduce((first, second) -> first)
                .orElse(Opcode.NOT_FOUND);
    }

    private void parseDefinition(URL location) throws IOException{
        try(BufferedReader in = new BufferedReader(new InputStreamReader(location.openStream(), "utf-8"))){
            updateOpcodes(in.lines());
        }
    }

    private void updateOpcodes(Stream<String> lines){
        lines.map(line -> buildOpcode(line.split(",")))
        .forEach(opcode -> opcodes.put(opcode.opcode(), opcode));
    }

    private Opcode buildOpcode(String[] items){
        return new Opcode(Integer.parseInt(items[0]), items[1]);
    }
}
