package com.github.sufbo.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.bson.Document;

import com.github.sufbo.stats.ItemBuilder;
import com.github.sufbo.stats.entities.Item;
import com.github.sufbo.utils.Timer;

public class DatabaseStorer extends DBAccessor {
    public static final String COLLECTION_NAME = "opcodes";
    private List<Document> list = new ArrayList<>(1000);

    public DatabaseStorer(){
        super("localhost");
        open();
    }

    public void run(Path path) throws IOException{
        try(Stream<String> stream = Files.lines(path)){
            store(stream);
        }
    }

    private void store(Stream<String> stream){
        ItemBuilder builder = new ItemBuilder();
        stream.map(line -> builder.build(line))
        .forEach(this::store);
    }

    private void store(Item item){
        if(list.size() < 1000){
            list.add(toDocument(item));
            return;
        }
        Timer.execute(() -> storeImpl());
    }

    private void storeImpl(){
        accept(COLLECTION_NAME, collection -> collection.insertMany(list));
        list.clear();
    }

    private Document toDocument(Item item){
        DocumentConverter converter = new DocumentConverter();
        item.accept(converter);
        return converter.toDocument();
    }

    public static void main(String[] args) throws IOException{
        try(DatabaseStorer storer = new DatabaseStorer()){
            storer.run(Paths.get(args[0]));
        }
    }
}
