package com.github.sufbo.stats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.github.sufbo.stats.calculators.SimilarityCalculatorFactory;
import com.github.sufbo.stats.entities.Item;
import com.github.sufbo.stats.entities.Similarity;
import com.github.sufbo.stats.entities.SimpleItemStringifier;

public class LeaveOneOut {
    private ItemBuilder builder = new ItemBuilder();
    private SimilarityCalculator calculator = new SimilarityCalculatorFactory()
            .calculatorOrDefault("levenshtein");
    private SimpleItemStringifier stringifier = new SimpleItemStringifier();
    private HeatmapImageCreator creator;

    public void run(String file) throws IOException{
        Path path = Paths.get(file);
        Index index = new Index();
        TimeHolder holder = new TimeHolder();

        try(Stream<String> stream = Files.lines(path)){
            stream.forEach(
                    string -> holder.append(executeLeaveOne(string, path, index.increment())));
        }
        holder.printf(System.err, "total time: %,f ms%n");
        creator.close();
    }

    private long executeLeaveOne(String itemString, Path path, int ignoreIndex){
        long start = System.nanoTime();
        int size = leaveOne(builder.build(itemString), path, ignoreIndex, new ArrayList<>());
        long time = System.nanoTime() - start;
        System.err.printf("%,6d/%,6d (%,f ms)%n", ignoreIndex + 1, 
                size + ignoreIndex, time / 1000000d);
        return time;
    }

    private int leaveOne(Item item, Path path, int ignoreIndex, List<Similarity> list){
        try(Stream<String> stream = Files.lines(path)){
            leaveOneImpl(stream, item, ignoreIndex, list);
        } catch(IOException e){
            throw new InternalError(e);
        }
        return list.size();
    }

    private void leaveOneImpl(Stream<String> stream, Item item, int ignoreIndex, List<Similarity> list) throws IOException{
        stream.skip(ignoreIndex).forEach(line -> perform(item, line, list));
        printList(item, ignoreIndex, list);
        updateHeatMap(ignoreIndex, list);
    }

    private void updateHeatMap(int index, List<Similarity> similarities) throws IOException{
        if(creator == null)
            creator = new HeatmapImageCreator(similarities.size(), similarities.size());
        creator.update(index, similarities);
    }

    private void printList(Item item, int ignoreIndex, List<Similarity> list){
        System.out.print(stringifier.stringify(item));
        for(int i = 0; i < list.size(); i++){
            System.out.print(",");
            if(i == ignoreIndex)
                System.out.print(",");
            System.out.print(list.get(i));
        }
    }

    private void perform(Item item, String line, List<Similarity> list){
        Item item2 = builder.build(line);
        list.add(calculator.calculate(item.bytecode(), item2.bytecode()));
    }

    public static void main(String[] args) throws IOException{
        LeaveOneOut loo = new LeaveOneOut();
        loo.run(args[0]);
    }
}
