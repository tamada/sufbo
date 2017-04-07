package com.github.sufbo.stats;

class Index {
    private int index;

    public Index(){
        this.index = 0;
    }

    public int increment(){
        index++;
        return index;
    }

    public int incrementAfter(){
        int current = index;
        index++;
        return current;
    }

    public int refer(){
        return index;
    }
}
