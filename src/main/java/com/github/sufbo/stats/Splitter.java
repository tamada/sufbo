package com.github.sufbo.stats;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.github.sufbo.stats.entities.Item;

public class Splitter extends Stats<IOException>{
    private RollingWriter writer;

    public Splitter(){
    }

    @Override
    public void doStatistics(Stream<Item> stream) throws IOException {
        stream.forEach(
                item -> writer.println(item.toString()));
    }

    protected Argf parseAndGetArgf(String[] args){
        Predicate<String> filter = string -> string.startsWith("-");
        List<String> options = filter(args, filter);
        List<String> argfList = filter(args, filter.negate());
        return createArgfAndParseOption(options, argfList);
    }

    private Argf createArgfAndParseOption(List<String> options, List<String> argfList){
        String baseName = argfList.size() == 0? "stdin.csv": argfList.get(0);
        int count = options.size() == 0? 2: parseCount(options.get(0));
        this.writer = createRollingWriter(count, baseName);
        return new Argf(argfList.toArray(new String[argfList.size()]), !options.contains("-h"));
    }

    private int parseCount(String countString){
        try{
            return Integer.parseInt(countString);
        } catch(NumberFormatException e){
            return 2;
        }
    }

    private RollingWriter createRollingWriter(int count, String baseName){
        int index = baseName.lastIndexOf('.');
        return new RollingWriter(count, baseName.substring(0, index),
                baseName.substring(index + 1));
    }

    private List<String> filter(String[] args, Predicate<String> filter){
        return Arrays.stream(args)
                .filter(filter)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) throws Throwable{
        Splitter splitter = new Splitter();
        splitter.run(args);
    }

    public static class RollingWriter implements AutoCloseable{
        private List<Target> targets;
        private int currentIndex = 0;

        public RollingWriter(int count, String prefix, String extension){
            targets = IntStream.range(0, count)
                    .mapToObj(index -> new Target(String.format("%s-%02d.%s", prefix, index + 1, extension)))
                    .collect(Collectors.toList());
        }

        public synchronized void println(String line){
            Target target = targets.get(currentIndex++);
            target.println(line);
            if(currentIndex == targets.size())
                currentIndex = 0;
        }

        public void close() throws IOException{
            targets.stream().forEach(Target::close);
        }
    }

    public static class Target{
        private PrintWriter out;

        public Target(String fileName){
            try {
                out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));
            } catch (IOException e) {
                throw new InternalError(e);
            }
        }

        public void println(String line){
            out.println(line);
        }

        public void close(){
            out.close();
        }
    }
}
