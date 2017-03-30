package com.github.sufbo.entities;

import java.io.Serializable;
import java.util.Objects;

public class Name implements Serializable {
    private static final long serialVersionUID = 434649330621153219L;

    private String name;

    public Name(String name){
        this.name = name;
    }

    public int hashCode(){
        return Objects.hash(name, getClass());
    }

    public boolean equals(Object object){
        return object != null
                && equals(getClass(), object.getClass())
                && equals(name, ((Name)object).name);
    }

    private boolean equals(Class<?> class1, Class<?> class2){
        return Objects.equals(class1, class2);
    }

    private boolean equals(String name1, String name2){
        return Objects.equals(name1, name2);
    }

    @Override
    public String toString(){
        return name;
    }
}
