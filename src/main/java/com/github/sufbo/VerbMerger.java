package com.github.sufbo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class VerbMerger{
    public void run(String file) throws IOException{
        run(Paths.get(file));
    }

    private void run(Path path) throws IOException{
        try(BufferedReader in = Files.newBufferedReader(path)){
            processLines(in.lines());
        }
    }

    private void processLines(Stream<String> stream){
        Map<String, Integer> map = eachLine(stream);
        map.forEach((k, v) -> System.out.printf("%s,%d%n", k, v));
    }

    private Map<String, Integer> eachLine(Stream<String> stream){
        return stream.map(string -> string.split(","))
            .map(array -> verb(array[4]))
            .collect(Collectors.toMap(item -> item, item -> 1, (d1, d2) -> d1 + d2));
    }

    private String verb(String methodName){
        return methodName.replaceAll("[A-Z][a-z0-9]*", "_$0")
            .split("_")[0];
    }

    public static void main(String[] args) throws IOException{
        VerbMerger merger = new VerbMerger();
        merger.run(args[0]);
    }
}
