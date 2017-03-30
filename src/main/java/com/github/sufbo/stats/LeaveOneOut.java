package com.github.sufbo.stats;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
        long time = 0l;
        try(BufferedReader in = Files.newBufferedReader(path, Charset.forName("utf-8"))){
            String line;
            for(int index = 0; (line = in.readLine()) != null; index++){
                time += leaveOne(builder.build(line), path, index);
            }
        }
        System.err.printf("total time: %,f ms%n", time / 1000000d);
        creator.close();
    }

    private long leaveOne(Item item, Path path, int ignoreIndex) throws IOException{
        List<Similarity> list = new ArrayList<>();
        long start = System.nanoTime();
        try(BufferedReader in = Files.newBufferedReader(path, Charset.forName("utf-8"))){
            String line;
            for(int index = 0; (line = in.readLine()) != null; index++){
                if(index == ignoreIndex)
                    continue;
                perform(item, line, index, list);
            }
        }
        printList(item, ignoreIndex, list);
        updateHeatMap(ignoreIndex, list);
        long time = System.nanoTime() - start;
        System.err.printf("%,6d/%,6d (%,f ms)%n", ignoreIndex + 1, list.size(), time / 1000000d);
        return time;
    }

    private void updateHeatMap(int index, List<Similarity> similarities) throws IOException{
        if(creator == null)
            creator = new HeatmapImageCreator(similarities.size() + 1, similarities.size() + 1);
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

    private void perform(Item item, String line, int index, List<Similarity> list){
        Item item2 = builder.build(line);
        list.add(calculator.calculate(item.bytecode(), item2.bytecode()));
    }

    public static void main(String[] args) throws IOException{
        LeaveOneOut loo = new LeaveOneOut();
        loo.run(args[0]);
    }
}
