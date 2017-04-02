package com.github.sufbo.stats;

import java.util.stream.Stream;

import com.github.sufbo.stats.entities.Item;
import com.github.sufbo.utils.Timer;

public abstract class Stats<T extends Throwable> {
    public final void run(String[] files) throws Throwable{
        Argf argf = parseAndGetArgf(files);
        SufboDataStream stream = new SufboDataStream(argf);
        Timer.perform(() -> perform(stream));
    }

    private void perform(SufboDataStream sufbo) throws Throwable{
        try(Stream<Item> stream = sufbo.stream()){
            doStatistics(stream);
        }
    }

    protected Argf parseAndGetArgf(String[] args){
        return new Argf(args);
    }

    public abstract void doStatistics(Stream<Item> stream) throws T;
}
