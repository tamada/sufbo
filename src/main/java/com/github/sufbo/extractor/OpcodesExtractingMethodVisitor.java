package com.github.sufbo.extractor;

import java.io.ByteArrayOutputStream;

import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.github.sufbo.entities.java.Bytecode;
import com.github.sufbo.entities.java.Method;
import com.github.sufbo.entities.java.MethodInformation;

class OpcodesExtractingMethodVisitor extends MethodVisitor {
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    public OpcodesExtractingMethodVisitor(MethodVisitor visitor){
        super(Opcodes.ASM5, visitor);
    }

    public Method build(MethodInformation info){
        Bytecode bytecode = new Bytecode(out.toByteArray());
        return new Method(info, bytecode);
    }

    @Override
    public void visitInsn(int opcode) {
        out.write(opcode);
        super.visitInsn(opcode);
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        out.write(opcode);
        super.visitIntInsn(opcode, operand);
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        out.write(opcode);
        super.visitVarInsn(opcode, var);
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        out.write(opcode);
        super.visitTypeInsn(opcode, type);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        out.write(opcode);
        super.visitFieldInsn(opcode, owner, name, desc);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        out.write(opcode);
        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
        out.write(Opcodes.INVOKEDYNAMIC);
        super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        out.write(opcode);
        super.visitJumpInsn(opcode, label);
    }

    @Override
    public void visitLdcInsn(Object cst) {
        out.write(Opcodes.LDC);
        super.visitLdcInsn(cst);
    }

    @Override
    public void visitIincInsn(int var, int increment) {
        out.write(Opcodes.IINC);
        super.visitIincInsn(var, increment);
    }

    @Override
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        out.write(Opcodes.TABLESWITCH);
        super.visitTableSwitchInsn(min, max, dflt, labels);
    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        out.write(Opcodes.LOOKUPSWITCH);
        super.visitLookupSwitchInsn(dflt, keys, labels);
    }

    @Override
    public void visitMultiANewArrayInsn(String desc, int dims) {
        out.write(Opcodes.MULTIANEWARRAY);
        super.visitMultiANewArrayInsn(desc, dims);
    }
}
