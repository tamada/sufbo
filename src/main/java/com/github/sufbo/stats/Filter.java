package com.github.sufbo.stats;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.sufbo.stats.entities.Item;

public class Filter extends Stats<IOException>{
    private Predicate<Item> condition;

    @Override
    public void doStatistics(Stream<Item> stream) throws IOException {
        stream.filter(condition)
        .forEach(System.out::println);
    }

    @Override
    protected Argf parseAndGetArgf(String[] args){
        Predicate<String> predicate = item -> item.startsWith("-");
        List<String> options = filter(args, predicate);
        List<String> arguments = filter(args, predicate.negate());
        return buildArgf(arguments, options);
    }

    private Argf buildArgf(List<String> args, List<String> options){
        this.condition = parseOptions(options);
        return new Argf(args.toArray(new String[args.size()]));
    }

    private Predicate<Item> parseOptions(List<String> options){
        return options.stream()
                .map(item -> mapToPredicate(item))
                .reduce(item -> true, (predicate1, predicate2) -> predicate1.and(predicate2));
    }

    private Predicate<Item> mapToPredicate(String value){
        String mark = value.substring(1, 2);
        int number = Integer.parseInt(value.substring(2));
        if(mark.equals("<"))
            return item -> item.bytecodeLength() < number;
        else if(mark.equals(">"))
            return item -> item.bytecodeLength() > number;
        else if(mark.equals("="))
            return item -> item.bytecodeLength() == number;
        return item -> true;
    }

    private List<String> filter(String[] args, Predicate<String> condition){
        return Arrays.stream(args)
                .filter(condition)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) throws Throwable{
        Filter filter = new Filter();
        filter.run(args);
    }
}
