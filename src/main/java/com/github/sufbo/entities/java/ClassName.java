package com.github.sufbo.entities.java;

import com.github.sufbo.entities.Name;

public class ClassName extends Name{
    private static final long serialVersionUID = 8446440107355543770L;

    public ClassName(String name) {
        super(name.replace('/', '.'));
    }
}
