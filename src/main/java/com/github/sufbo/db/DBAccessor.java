package com.github.sufbo.db;

import java.util.function.Consumer;

import org.bson.Document;

import com.mongodb.Function;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

public class DBAccessor implements AutoCloseable{
    private static final String DATABASE_NAME = "sufbo";
    private String uri;
    private MongoClient client;

    public DBAccessor(String uri){
        this.uri = uri;
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

    public void open() {
        client = new MongoClient(uri);
    }

    @Override
    public void close(){
        client.close();
    }
}
