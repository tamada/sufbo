package com.github.sufbo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcDataSource;

public class H2DataAccessor extends DBAccessor{
    private Connection connection;

    public H2DataAccessor(String uri){
        super(uri);
    }

    public PreparedStatement statement(String sql) throws SQLException{
        return connection.prepareStatement(sql);
    }

    protected Connection connection(){
        return connection;
    }

    @Override
    public void open(String uri) {
        JdbcDataSource source = new JdbcDataSource();
        source.setURL(uri);
        source.setUser("sa");
        source.setPassword("");
        try{
            this.connection = source.getConnection();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void prepare() {
        // do nothing.
    }

    @Override
    public void close() {
        try{
            connection.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
