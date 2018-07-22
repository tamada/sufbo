package com.github.sufbo.extractor;

import org.objectweb.asm.MethodVisitor;

public class MethodVisitorFactory {
    private boolean simplefy = true;

    public MethodVisitorFactory(boolean simplefy) {
        this.simplefy = simplefy;
    }

    public SufboParentMethodVisitor create(MethodVisitor parent) {
        SufboParentMethodVisitor visitor = createImpl(parent);
        // System.out.printf("%s %s%n", simplefy, visitor.getClass().getName());
        return visitor;
    }

    private SufboParentMethodVisitor createImpl(MethodVisitor parent) {
        if(simplefy)
            return new SimpleOpcodesExtractingMethodVisitor(parent);
        return new OpcodesExtractingMethodVisitor(parent);
    }
}
