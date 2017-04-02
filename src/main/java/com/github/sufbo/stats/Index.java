package com.github.sufbo.stats;

class Index {
    private int index;

    public Index(){
        this.index = 0;
    }

    public int increment(){
        int current = index;
        index++;
        return current;
    }

    public int refer(){
        return index;
    }
}
