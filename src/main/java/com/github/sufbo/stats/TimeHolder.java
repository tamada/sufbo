package com.github.sufbo.stats;

import java.io.PrintStream;

public class TimeHolder {
    private long time;

    public void append(long time){
        this.time += time;
    }

    public long time(){
        return time;
    }

    public void printf(PrintStream stream, String format){
        stream.printf(format, time() / 1000000d);
    }
}
