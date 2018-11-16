package com.github.sufbo.entities.java;

import org.objectweb.asm.Opcodes;

public class Accessor{
    private int access;

    public Accessor(int access){
        this.access = access;
    }

    public boolean isPublic(){
        return check(Opcodes.ACC_PUBLIC);
    }

    public boolean isPackagePrivate(){
        return !isPublic()
            && !isProtected()
            && !isPrivate();
    }

    public boolean isProtected(){
        return check(Opcodes.ACC_PROTECTED);
    }

    public boolean isPrivate(){
        return check(Opcodes.ACC_PRIVATE);
    }

    private boolean check(int target){
        return (access & target) == target;
    }

    public String toString(){
        if(isPublic())
            return "public";
        else if(isPackagePrivate()) 
            return "packageprivate";
        else if(isProtected())
            return "protected";
        else if(isPrivate())
            return "private";
        else
            return "unknown";
    }
}
