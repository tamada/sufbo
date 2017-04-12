package com.github.sufbo;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.github.sufbo.utils.stream.ThrowableConsumer;

public class Options {
    private Map<Key, String> options = new HashMap<>();

    Options(Map<Key, String> options){
        this.options.putAll(options);
    }

    public Charset charset(){
        return Charset.forName("utf-8");
    }

    public boolean has(Key key){
        return options.containsKey(key);
    }

    public boolean hasAll(Key... keys){
        return Arrays.stream(keys)
                .map(options::containsKey)
                .reduce(true, (first, second) -> first && second);
    }

    public boolean hasAny(Key... keys){
        return Arrays.stream(keys)
                .map(options::containsKey)
                .reduce(false, (first, second) -> first || second);
    }

    public <E extends Exception> void ifKeyPresent(Key key, ThrowableConsumer<Optional<String>, E> action) throws E{
        if(has(key))
            action.accept(optionalValue(key));
    }

    public Optional<String> optionalValue(Key key){
        if(key.hasArgument())
            return Optional.of(options.get(key));
        return Optional.empty();
    }

    public String value(Key key){
        if(key.hasArgument())
            return options.get(key);
        throw new IllegalArgumentException(key + " has not argument");
    }
}
