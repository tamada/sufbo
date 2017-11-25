package com.github.sufbo.utils;

public class OpcodeUtil {
    private static final OpcodeUtil INSTANCE = new OpcodeUtil();

    private Opcodes opcodes = new Opcodes();

    public static Opcode opcode(int opcode) {
        return INSTANCE.opcodes.of(opcode);
    }
}
