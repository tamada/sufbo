package com.github.sufbo.extractor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.github.sufbo.entities.java.MethodInformation;

public class SufboMethodVisitor extends MethodVisitor {
    private SufboParentMethodVisitor visitor;
    private MethodInformation info;
    private MethodPool pool;

    public SufboMethodVisitor(SufboParentMethodVisitor visitor, MethodInformation info, MethodPool pool){
        super(Opcodes.ASM5, visitor);
        this.visitor = visitor;
        this.info = info;
        this.pool = pool;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        pool.add(visitor.build(info));
    }
}
