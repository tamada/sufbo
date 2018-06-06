package com.github.sufbo.entities.java;

import com.github.sufbo.entities.Name;

public class Descriptor extends Name{
    private static final long serialVersionUID = -6447066572460986215L;

    private Accessor accessor;

    public Descriptor(String name){
        this(new Accessor(0), name);
    }

    public Descriptor(Accessor accessor, String name) {
        super(name);
        this.accessor = accessor;
    }

    public String toString(){
        return String.format("%s,%s",
                             super.toString(),
                             accessor);
    }
}
