package com.github.sufbo.extractor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import com.github.sufbo.entities.java.Class;
import com.github.sufbo.entities.java.ClassName;
import com.github.sufbo.entities.java.Methods;

class ClassNameExtractor extends ClassVisitor{
    private ClassName name;

    public ClassNameExtractor() {
        super(Opcodes.ASM5);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.name = new ClassName(name);
    }

    public Class buildJavaClass(Methods methods){
        return new Class(className(), methods);
    }

    public ClassName className(){
        return name;
    }
}
