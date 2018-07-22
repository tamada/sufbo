package com.github.sufbo.extractor;

import org.objectweb.asm.Opcodes;

public class OpcodeSimplefier {
    public int add() {
        return Opcodes.IADD;
    }

    public int and() {
        return Opcodes.IAND;
    }

    public int arraylength() {
        return Opcodes.ARRAYLENGTH;
    }

    public int arrayLoad() {
        return Opcodes.IALOAD;
    }

    public int arrayStore() {
        return Opcodes.IASTORE;
    }

    public int branch() {
        return Opcodes.GOTO;
    }

    public int cast() {
        return Opcodes.I2D;
    }

    public int checkcast() {
        return Opcodes.CHECKCAST;
    }

    public int compare() {
        return Opcodes.LCMP;
    }

    public int constant() {
        return Opcodes.ACONST_NULL;
    }

    public int divide() {
        return Opcodes.IDIV;
    }
    
    public int dup() {
        return Opcodes.DUP;
    }

    public int field() {
        return Opcodes.GETFIELD;
    }

    public int instanceOf() {
        return Opcodes.INSTANCEOF;
    }

    public int invoke() {
        return Opcodes.INVOKEVIRTUAL;
    }

    public int load() {
        return Opcodes.ALOAD;
    }

    public int monitor() {
        return Opcodes.MONITORENTER;
    }

    public int multiply() {
        return Opcodes.IMUL;
    }

    public int negate() {
        return Opcodes.INEG;
    }

    public int newArray() {
        return Opcodes.NEWARRAY;
    }

    public int newInsn() {
        return Opcodes.NEW;
    }

    public int or() {
        return Opcodes.IOR;
    }

    public int pop() {
        return Opcodes.POP;
    }

    public int remain() {
        return Opcodes.IREM;
    }

    public int returnInsn() {
        return Opcodes.RETURN;
    }

    public int shiftLeft() {
        return Opcodes.ISHL;
    }

    public int shiftRight() {
        return Opcodes.ISHR;
    }

    public int stack() {
        return Opcodes.NOP;
    }

    public int store() {
        return Opcodes.ISTORE;
    }

    public int subtract() {
        return Opcodes.ISUB;
    }

    public int swap() {
        return Opcodes.SWAP;
    }

    public int switchInsn() {
        return Opcodes.LOOKUPSWITCH;
    }

    public int throwInsn() {
        return Opcodes.ATHROW;
    }

    public int ushiftRight() {
        return Opcodes.IUSHR;
    }

    public int xor() {
        return Opcodes.IXOR;
    }
}
