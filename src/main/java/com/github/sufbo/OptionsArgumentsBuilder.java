package com.github.sufbo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class OptionsArgumentsBuilder {
    private Map<Key, String> options = new HashMap<>();
    private List<String> arguments = new ArrayList<>();

    public static OptionsArgumentsBuilder builder(List<Key> list, String[] args){
        return new OptionsArgumentsBuilder(list, args);
    }

    private OptionsArgumentsBuilder(List<Key> keys, String[] args){
        for(int i = 0; i < args.length; i++){
            final String current = args[i];
            Optional<Key> key = findAndPutOption(keys.stream(), current, i, args);
            ifNotPresent(key, () -> arguments.add(current));
            i = i + key.map(item -> item.hasArgument()? 1: 0).orElse(0);
        }
    }

    public Arguments arguments(){
        return new Arguments(arguments);
    }

    public Options options(){
        return new Options(options);
    }

    private void ifNotPresent(Optional<Key> optional, Runnable runnable){
        if(!optional.isPresent())
            runnable.run();
    }

    private Optional<Key> findAndPutOption(Stream<Key> stream, String current, int index, String[] args){
        Optional<Key> optional = findKey(stream, current);
        optional.ifPresent(key -> putItem(key, args, index));
        return optional;
    }

    private Optional<Key> findKey(Stream<Key> stream, String current){
        return stream
                .filter(key -> key.isSpecified(current))
                .reduce((first, second) -> first);
    }

    private void putItem(Key key, String[] args, int index){
        if(key.hasArgument()){
            options.put(key, args[index + 1]);
            return;
        }
        options.put(key, "true");
    }
}
