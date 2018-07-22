package com.github.sufbo.extractor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.github.sufbo.entities.java.Method;
import com.github.sufbo.entities.java.MethodInformation;

public abstract class SufboParentMethodVisitor extends MethodVisitor {
    public SufboParentMethodVisitor(MethodVisitor parent) {
        super(Opcodes.ASM5, parent);
    }

    public abstract Method build(MethodInformation methodInfo);
}
