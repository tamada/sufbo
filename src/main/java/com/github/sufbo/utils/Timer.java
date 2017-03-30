package com.github.sufbo.utils;

public class Timer {
    public static <T extends Throwable> void perform(ThrowableRunnable<T> runnable) throws T{
        long start = System.nanoTime();
        try{
            runnable.run();
        } finally{
            printTime(start, System.nanoTime());
        }
    }

    private static void printTime(long start, long end){
        long time = end - start;
        System.err.printf("%,f ms%n", time / 1000000d);
    }

    @FunctionalInterface
    public static interface ThrowableRunnable<T extends Throwable>{
        public void run() throws T;
    }
}
