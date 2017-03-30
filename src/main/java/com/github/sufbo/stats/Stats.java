package com.github.sufbo.stats;

import java.nio.file.Paths;
import java.util.stream.Stream;

import com.github.sufbo.stats.entities.Item;
import com.github.sufbo.utils.Timer;

public abstract class Stats<T extends Throwable> {
    public final void run(String file) throws Throwable{
        SufboDataStream stream = new SufboDataStream(Paths.get(file));
        Timer.perform(() -> doStatistics(stream.stream()));
    }

    public abstract void doStatistics(Stream<Item> stream) throws T;
}
