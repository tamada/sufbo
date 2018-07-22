package com.github.sufbo.extractor;

import java.io.ByteArrayOutputStream;

import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.github.sufbo.entities.java.Bytecode;
import com.github.sufbo.entities.java.Method;
import com.github.sufbo.entities.java.MethodInformation;
import com.github.sufbo.utils.OpcodeUtil;

class SimpleOpcodesExtractingMethodVisitor extends SufboParentMethodVisitor {
    private ByteArrayOutputStream out = new ByteArrayOutputStream();
    private OpcodeSimplefier simplefier = new OpcodeSimplefier();

    public SimpleOpcodesExtractingMethodVisitor(MethodVisitor visitor){
        super(visitor);
    }

    @Override
    public Method build(MethodInformation info){
        Bytecode bytecode = new Bytecode(out.toByteArray());
        return new Method(info, bytecode);
    }

    @Override
    public void visitInsn(int opcode) {
        out.write(simplefyOpcode(opcode));
        super.visitInsn(opcode);
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        out.write(simplefyOpcode(opcode));
        super.visitIntInsn(opcode, operand);
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        out.write(simplefyOpcode(opcode));
        super.visitVarInsn(opcode, var);
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        out.write(simplefyOpcode(opcode));
        super.visitTypeInsn(opcode, type);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        out.write(simplefyOpcode(opcode));
        super.visitFieldInsn(opcode, owner, name, desc);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        out.write(simplefyOpcode(opcode));
        super.visitMethodInsn(opcode, owner, name, desc, itf);
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
        out.write(simplefyOpcode(Opcodes.INVOKEDYNAMIC));
        super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        out.write(simplefyOpcode(opcode));
        super.visitJumpInsn(opcode, label);
    }

    @Override
    public void visitLdcInsn(Object cst) {
        out.write(simplefyOpcode(Opcodes.LDC));
        super.visitLdcInsn(cst);
    }

    @Override
    public void visitIincInsn(int var, int increment) {
        out.write(simplefyOpcode(Opcodes.IINC));
        super.visitIincInsn(var, increment);
    }

    @Override
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        out.write(simplefyOpcode(Opcodes.TABLESWITCH));
        super.visitTableSwitchInsn(min, max, dflt, labels);
    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        out.write(simplefyOpcode(Opcodes.LOOKUPSWITCH));
        super.visitLookupSwitchInsn(dflt, keys, labels);
    }

    @Override
    public void visitMultiANewArrayInsn(String desc, int dims) {
        out.write(simplefyOpcode(Opcodes.MULTIANEWARRAY));
        super.visitMultiANewArrayInsn(desc, dims);
    }

    private int simplefyOpcode(int opcode) {
        switch(OpcodeUtil.opcode(opcode).type()) {
        case "STACK":
            return simplefier.stack();
        case "CONSTANT":
            return simplefier.constant();
        case "LOAD":
            return simplefier.load();
        case "ARRAY_LOAD":
            return simplefier.arrayLoad();
        case "ARRAY_STORE":
            return simplefier.arrayStore();
        case "STORE":
            return simplefier.store();
        case "POP":
            return simplefier.pop();
        case "DUP":
            return simplefier.dup();
        case "SWAP":
            return simplefier.swap();
        case "ADD":
            return simplefier.add();
        case "SUBTRACT":
            return simplefier.subtract();
        case "MULTIPLY":
            return simplefier.multiply();
        case "DIVIDE":
            return simplefier.divide();
        case "REMAIN":
            return simplefier.remain();
        case "NEGATE":
            return simplefier.negate();
        case "SHIFT_LEFT":
            return simplefier.shiftLeft();
        case "SHIFT_RIGHT":
            return simplefier.shiftRight();
        case "USHIFT_RIGHT":
            return simplefier.ushiftRight();
        case "AND":
            return simplefier.add();
        case "OR":
            return simplefier.or();
        case "XOR":
            return simplefier.xor();
        case "CAST":
            return simplefier.cast();
        case "COMPARE":
            return simplefier.compare();
        case "BRANCH":
            return simplefier.branch();
        case "SWITCH":
            return simplefier.switchInsn();
        case "RETURN":
            return simplefier.returnInsn();
        case "FIELD":
            return simplefier.field();
        case "INVOKE":
            return simplefier.invoke();
        case "NEW":
            return simplefier.newInsn();
        case "NEW_ARRAY":
            return simplefier.newArray();
        case "ARRAYLENGTH":
            return simplefier.arraylength();
        case "THROW":
            return simplefier.throwInsn();
        case "MONITOR":
            return simplefier.monitor();
        }
        return opcode;
    }
}
