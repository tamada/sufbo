package com.github.sufbo.extractor;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.MethodVisitor;

import com.github.sufbo.entities.java.Class;
import com.github.sufbo.entities.java.Descriptor;
import com.github.sufbo.entities.java.Method;
import com.github.sufbo.entities.java.MethodInformation;
import com.github.sufbo.entities.java.MethodName;
import com.github.sufbo.entities.java.Methods;

public class SufboClassVisitor extends ClassNameExtractor{
    private List<Method> methods = new ArrayList<>();
    private MethodVisitorFactory factory;

    public SufboClassVisitor(boolean simplefy) {
        this.factory = new MethodVisitorFactory(simplefy);
    }

    public SufboClassVisitor() {
        this(true);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodInformation info = new MethodInformation(new MethodName(name), new Descriptor(desc));
        return new SufboMethodVisitor(factory.create(
                super.visitMethod(access, name, desc, signature, exceptions)),
                info, MethodPool.wrap(methods));
    }

    public Class buildJavaClass(){
        Methods javaMethods = new Methods(methods.stream());
        return buildJavaClass(javaMethods);
    }
}
