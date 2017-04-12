package com.github.sufbo.stats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.sufbo.Arguments;
import com.github.sufbo.Key;
import com.github.sufbo.Options;
import com.github.sufbo.OptionsArgumentsBuilder;
import com.github.sufbo.entities.ByteArray;
import com.github.sufbo.stats.calculators.SimilarityCalculatorFactory;
import com.github.sufbo.stats.entities.Item;
import com.github.sufbo.stats.entities.Similarity;
import com.github.sufbo.stats.entities.SimpleItemStringifier;

public class LeaveOneOut {
    public static final Key PARALLEL = new Key("-p", "--parallel", false);
    public static final Key OUTPUT_RESULT = new Key("-o", "--output-result", false);
    public static final Key OUTPUT_HEATMAP = new Key("-H", "--heatmap", false);

    private ItemBuilder builder = new ItemBuilder();
    private SimilarityCalculator calculator = new SimilarityCalculatorFactory()
            .calculatorOrDefault("levenshtein");
    private SimpleItemStringifier stringifier = new SimpleItemStringifier();
    private HeatmapImageCreator creator = new HeatmapImageCreator();
    private Arguments arguments;
    private Options options;

    public LeaveOneOut(String[] args){
        OptionsArgumentsBuilder optionArguments = OptionsArgumentsBuilder.builder(Arrays.asList(PARALLEL, OUTPUT_RESULT, OUTPUT_HEATMAP), args);
        arguments = optionArguments.arguments();
        options = optionArguments.options();
    }

    public void run() throws IOException{
        arguments.forEach(file -> { 
            try{
                execute(file);
            } catch(IOException e){
                throw new InternalError(e);
            }
        });
    }

    public void execute(String file) throws IOException{
        Path path = Paths.get(file);
        Index index = new Index();
        TimeHolder holder = new TimeHolder();

        try(Stream<String> stream = Files.lines(path)){
            stream.forEach(
                    string -> holder.append(executeLeaveOne(string, path, index.incrementAfter())));
        }
        holder.printf(System.err, "total time: %,f ms%n");
        creator.close();
    }

    private long executeLeaveOne(String itemString, Path path, int ignoreIndex){
        long start = System.nanoTime();
        int size = leaveOne(builder.build(itemString), path, ignoreIndex);
        long time = System.nanoTime() - start;
        System.err.printf("%,6d/%,6d (%,f ms)%n", ignoreIndex + 1, 
                size + ignoreIndex, time / 1000000d);
        return time;
    }

    private int leaveOne(Item item, Path path, int ignoreIndex){
        try(Stream<String> stream = Files.lines(path)){
            if(options.has(PARALLEL))
                return leaveOneImpl(stream.parallel(), item, ignoreIndex);
            return leaveOneImpl(stream, item, ignoreIndex);
        } catch(IOException e){
            throw new InternalError(e);
        }
    }

    private int leaveOneImpl(Stream<String> stream, Item item, int ignoreIndex) throws IOException{
        List<Similarity> list = compareThem(stream, item.bytecode(), ignoreIndex);
        options.ifKeyPresent(OUTPUT_RESULT,  value -> printList(item, ignoreIndex, list));
        options.ifKeyPresent(OUTPUT_HEATMAP, value -> updateHeatMap(ignoreIndex, list));
        return list.size();
    }

    private List<Similarity> compareThem(Stream<String> stream, ByteArray array, int ignoreIndex){
        return stream.skip(ignoreIndex)
                .map(line -> calculate(array, line))
                .collect(Collectors.toList());
    }

    private void updateHeatMap(int index, List<Similarity> similarities) throws IOException{
        if(index == 0)
            creator.constructImage(similarities.size(), similarities.size());
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
        System.out.println();
    }

    private Similarity calculate(ByteArray array, String line){
        ByteArray array2 = builder.buildArray(line);
        return calculator.calculate(array, array2);
    }

    public static void main(String[] args) throws IOException{
        LeaveOneOut loo = new LeaveOneOut(args);
        loo.run();
    }
}
