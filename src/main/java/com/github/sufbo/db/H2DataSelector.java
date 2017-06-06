package com.github.sufbo.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Stream;

import com.github.sufbo.stats.entities.Item;

public class H2DataSelector extends H2DataAccessor{

    public H2DataSelector(String uri) {
        super(uri);
    }

    public Stream<Item> stream(int bytecodeSizeStart, int bytecodeSizeEnd){
        try{
            PreparedStatement ps = statement(
                    "SELECT * FROM ids, classes, methods WHERE ids.id = classes.ids_ref AND ids.id = methods.ids_ref AND " +
                    "methods.class_ref = classes.id AND methods.bytecode_size > ? AND methods.bytecode_size < ?");
            ps.setInt(1, bytecodeSizeStart);
            ps.setInt(2, bytecodeSizeEnd);

            return new ResultSetSpliterator<>(ps, new ItemTryFunction()).stream();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        try(H2DataSelector selector = new H2DataSelector("jdbc:h2:/Volumes/maven2/h2")){
            try(Stream<Item> stream = selector.stream(100, 150)){
                stream.forEach(System.out::println);
            }
        }
    }
}
