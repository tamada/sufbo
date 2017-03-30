package com.github.sufbo.stats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import com.github.sufbo.stats.entities.Item;

public class NewestVersionFilter extends Stats<IOException>{
    @Override
    public void doStatistics(Stream<Item> stream) throws IOException {
        Item current = null;
        List<Item> items = new ArrayList<>();

        for(Iterator<Item> iterator = stream.iterator(); iterator.hasNext(); ){
            Item item = iterator.next();
            if(current == null){
                current = item;
                items.add(item);
                continue;
            }
            if(current != null && current.sameArtifact(item)){
                if(current.isDifferentVersion(item)){
                    items.clear();
                }
                items.add(item);
            }
            else{
                printItems(items);
            }
            current = item;
        }
        printItems(items);
    }

    private void printItems(List<Item> items){
        System.err.println(items.get(0).version());
        items.stream()
        .forEach(System.out::println);
        items.clear();
    }

    public static void main(String[] args) throws Throwable{
        NewestVersionFilter filter = new NewestVersionFilter();
        filter.run(args[0]);
    }
}
