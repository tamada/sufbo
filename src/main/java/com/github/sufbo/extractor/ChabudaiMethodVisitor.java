package com.github.sufbo.extractor;

import org.objectweb.asm.MethodVisitor;

import com.github.sufbo.entities.java.MethodInformation;

public class ChabudaiMethodVisitor extends OpcodesExtractingMethodVisitor {
    private MethodInformation info;
    private MethodPool pool;

    public ChabudaiMethodVisitor(MethodVisitor visitor, MethodInformation info, MethodPool pool){
        super(visitor);
        this.info = info;
        this.pool = pool;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        pool.add(build(info));
    }
}
