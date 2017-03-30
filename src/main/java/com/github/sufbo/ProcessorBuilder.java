package com.github.sufbo;

import java.util.Arrays;
import java.util.stream.Stream;

public abstract class ProcessorBuilder {
    public static Processor build(String[] args){
        return findSuitableBuilder(args)
                .map(builder -> builder.construct(args))
                .reduce(new HelpPrintingProcessor(), (first, second) -> second);
    }

    private static Stream<ProcessorBuilder> builders(){
        return Stream.of(new ExtractingProcessorBuilder());
    }

    private static String findCommand(String[] args){
        return Arrays.stream(args)
                .reduce((first, second) -> first)
                .orElse("help");
    }

    private static Stream<ProcessorBuilder> findSuitableBuilder(String[] args){
        String command = findCommand(args);
        return builders()
                .filter(builder -> builder.isTarget(command));
    }

    public abstract Processor construct(String[] args);

    public abstract boolean isTarget(String flag);
}
