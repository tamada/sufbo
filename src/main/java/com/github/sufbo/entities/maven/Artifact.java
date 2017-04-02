package com.github.sufbo.entities.maven;

import java.io.IOException;
import java.nio.file.Path;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.objectweb.asm.ClassReader;

import com.github.sufbo.entities.java.Class;
import com.github.sufbo.entities.java.ClassName;
import com.github.sufbo.entities.java.Classes;
import com.github.sufbo.entities.java.ParseFailedClass;
import com.github.sufbo.entities.visitor.ArtifactVisitor;
import com.github.sufbo.extractor.SufboClassVisitor;

public class Artifact {
    private Ids ids;
    private Path path;

    public Artifact(Ids ids, Path path){
        this.ids = ids;
        this.path = path;
    }

    public void accept(ArtifactVisitor visitor){
        try{
            visitor.visit(ids, parse());
        } catch(IOException e){
            visitor.visitFailed(ids, e);
        }
    }

    public Ids ids(){
        return ids;
    }

    public Classes parse() throws IOException{
        try(JarFile jarFile = new JarFile(path.toFile())){
            return new Classes(jarFile.stream()
                    .filter(entry -> entry.getName().endsWith(".class"))
                    .map(entry -> parse(jarFile, entry)));
        }
    }

    private Class parse(JarFile file, JarEntry entry){
        try {
            // System.err.printf("%s: %s%n", file.getName(), entry.getName());
            return parse(new ClassReader(file.getInputStream(entry)));
        } catch (Exception e) {
            return new ParseFailedClass(new ClassName(entry.getName()), e);
        }
    }

    private Class parse(ClassReader reader) throws IOException{
        SufboClassVisitor visitor = new SufboClassVisitor();
        reader.accept(visitor, 0);
        return visitor.buildJavaClass();
    }
}
