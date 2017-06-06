package com.github.sufbo.db;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.github.sufbo.stats.entities.Item;
import com.github.sufbo.utils.LogHelper;
import com.github.sufbo.utils.Timer;

public class H2DataStorer extends H2DataAccessor implements DatabaseStorer{
    private int count;
    private long time;
    private double total = 0d;
    private ItemConverter<Boolean> converter;

    public H2DataStorer(String host){
        super(host);
    }

    @Override
    public void store(Item item){
        converter.convert(item);
        count++;
        if(count % 10000 == 0)
            peek();
    }

    private void peek(){
        try{
            connection().commit();
            printLog();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void printLog(){
        long now = System.nanoTime();
        double spentTime = (now - time) / 1000_000d;
        total += spentTime;
        LogHelper.info(getClass(),
                () -> String.format("%s data was inserted. (spend: %,6.3f ms, total: %,6.3f ms)", count, spentTime, total));
        time = now;
    }

    @Override
    public void prepare() {
        try{
            createTables();
            connection().setAutoCommit(false);
            converter = new H2StoringConverter(connection());            
        } catch(SQLException e){
            e.printStackTrace();
        }
        time = System.nanoTime();
    }

    private void createTables() throws SQLException{
        execute("CREATE TABLE IF NOT EXISTS IDS (ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, GROUP_ID VARCHAR(255), ARTIFACT_ID VARCHAR(255), VERSION VARCHAR(255));");
        execute("CREATE TABLE IF NOT EXISTS CLASSES (ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, IDS_REF INT, CLASS_NAME VARCHAR(512));");
        execute("CREATE TABLE IF NOT EXISTS METHODS (ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, IDS_REF INT, CLASS_REF INT, METHOD_NAME VARCHAR(255), DESCRIPTOR VARCHAR(8192), BYTECODE_SIZE INT, BYTECODES ARRAY);");
        LogHelper.info(getClass(), "create table done.");
    }

    private void execute(String sql) throws SQLException{
        try(PreparedStatement statement = statement(sql)){
            statement.executeUpdate();
        }
    }

    public static void main(String[] args) throws IOException{
        // try(DatabaseStorer store = new H2DataStorer("jdbc:h2:tcp://localhost/~/test")){
        //     store.run(Paths.get(args[0]));
        // }
        try(H2DataStorer store = new H2DataStorer("jdbc:h2:/Volumes/maven2/h2")){
            Timer.perform(() -> store.run(Paths.get(args[0])));
        }
    }
}
