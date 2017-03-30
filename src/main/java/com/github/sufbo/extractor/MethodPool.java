package com.github.sufbo.extractor;

import java.util.List;

import com.github.sufbo.entities.java.Method;

public interface MethodPool {
    void add(Method method);

    public static MethodPool wrap(List<Method> list){
        return method -> list.add(method);
    }
}
