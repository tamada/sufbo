package com.github.sufbo.db;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.bson.Document;

import com.github.sufbo.stats.entities.Item;
import com.github.sufbo.utils.Timer;
import com.mongodb.Function;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

/**
 * インサートが絶望的に遅いので，諦めた．
 *
 * @author tamada
 */
public class MongoStorer extends DatabaseStorer {
    private static final String DATABASE_NAME = "sufbo";
    public static final String COLLECTION_NAME = "opcodes";

    private List<Document> list = new ArrayList<>(1000);
    private MongoClient client;

    public MongoStorer(String hostName){
        super(hostName);
    }

    @Override
    public void prepare(){
    }

    protected void store(Item item){
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
        ItemConverter<Document> converter = new MongoItemConverter();
        return converter.convert(item);
    }

    public void accept(String collectionName, Consumer<MongoCollection<Document>> action){
        MongoCollection<Document> collection = collection(collectionName);
        action.accept(collection);
    }

    public <T> T apply(String collectionName, Function<MongoCollection<Document>, T> mapper){
        MongoCollection<Document> collection = collection(collectionName);
        return mapper.apply(collection);
    }

    public MongoCollection<Document> collection(String collectionName){
        return client.getDatabase(DATABASE_NAME)
                .getCollection(collectionName);
    }

    public void open(String uri) {
        client = new MongoClient(uri);
    }

    @Override
    public void close(){
        client.close();
    }

    public static void main(String[] args) throws IOException{
        try(MongoStorer storer = new MongoStorer("localhost")){
            storer.run(Paths.get(args[0]));
        }
    }
}
