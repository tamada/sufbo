package com.github.sufbo.db;

public abstract class DBAccessor implements AutoCloseable{
    public DBAccessor(String uri){
        openImpl(uri);
    }

    private void openImpl(String host){
        open(host);
        prepare();
    }

    public abstract void open(String uri);

    public abstract void prepare();

    public abstract void close();
}
