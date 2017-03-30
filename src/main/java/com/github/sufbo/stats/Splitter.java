package com.github.sufbo.stats;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.github.sufbo.stats.entities.Item;

public class Splitter extends Stats<IOException>{
    private RollingWriter writer;

    public Splitter(int count, String baseFileName){
        int index = baseFileName.lastIndexOf('.');
        writer = new RollingWriter(count, baseFileName.substring(0, index), baseFileName.substring(index + 1));
    }

    @Override
    public void doStatistics(Stream<Item> stream) throws IOException {
        stream.forEach(
                item -> writer.println(item.toString()));
    }

    public static void main(String[] args) throws Throwable{
        Splitter splitter = new Splitter(Integer.parseInt(args[1]), args[0]);
        splitter.run(args[0]);
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
