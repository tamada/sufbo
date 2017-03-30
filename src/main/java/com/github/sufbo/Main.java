package com.github.sufbo;

import java.io.IOException;

public class Main {
    private Processor processor;

    public Main(String[] args){
        this.processor = ProcessorBuilder.build(args);
    }

    public void run() throws IOException {
        long start = System.nanoTime();
        processor.run();
        printTime(start, System.nanoTime());
    }

    private void printTime(long from, long to){
        long time = to - from;
        System.out.printf("%,f ms%n", time / 1000000d);
    }

    public static void main(String[] args) throws IOException {
        new Main(args).run();
    }
}
