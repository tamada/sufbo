package com.github.sufbo.utils.stream;

@FunctionalInterface
public interface ThrowableConsumer<S, E extends Throwable> {
    void accept(S value) throws E;
}
