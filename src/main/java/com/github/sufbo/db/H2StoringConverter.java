package com.github.sufbo.db;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import com.github.sufbo.entities.ByteArray;
import com.github.sufbo.entities.java.Bytecode;
import com.github.sufbo.entities.java.ClassName;
import com.github.sufbo.entities.java.MethodInformation;
import com.github.sufbo.entities.maven.ArtifactId;
import com.github.sufbo.entities.maven.GroupId;
import com.github.sufbo.entities.maven.Ids;
import com.github.sufbo.entities.maven.Version;
import com.github.sufbo.stats.Index;

public class H2StoringConverter implements ItemConverter<Boolean> {
    private Connection connection;
    private Ids lastIds;
    private String lastClassName;
    private Index idIndex = new Index();
    private Index classRefIndex = new Index();
    private Index methodRef = new Index();
    private boolean success = true;

    public H2StoringConverter(Connection connection){
        this.connection = connection;
    }

    @Override
    public void visit(GroupId groupId, ArtifactId id, Version version) {
        Ids ids = new Ids(groupId, id, version);
        if(!Objects.equals(lastIds, ids)){
            ids(groupId, id, version);
            this.lastIds = ids;
        }
    }

    private int ids(GroupId groupId, ArtifactId id, Version version) {
        try{
            return insertIds(groupId.toString(), id.toString(), version.toString());
        } catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void visit(ClassName name, MethodInformation method, Bytecode bytecode) {
        if(!Objects.equals(lastClassName, name.toString())){
            classRef(name.toString());
        }
        insertMethodRef(method.name().toString(), method.descriptor().toString(), bytecode);
    }

    private int classRef(String className){
        try{
            return insertClassRef(className);
        } catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    private void insertMethodRef(String methodName, String descriptor, Bytecode bytecode){
        try{
            insertMethodRefImpl(methodName, descriptor, bytecode);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void insertMethodRefImpl(String methodName, String descriptor, Bytecode bytecode) throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO METHODS (ID, IDS_REF, CLASS_REF, METHOD_NAME, DESCRIPTOR, BYTECODE_SIZE, BYTECODES) VALUES (?, ?, ?, ?, ?, ?, ?)")){
            statement.setInt(1, methodRef.increment());
            statement.setInt(2, idIndex.refer());
            statement.setInt(3, classRefIndex.refer());
            statement.setString(4, methodName);
            statement.setString(5, descriptor);
            statement.setInt(6, bytecode.length());
            statement.setArray(7, convertToArray(bytecode.buffer()));
            success = success && statement.executeUpdate() > 0;
        }
    }

    private Array convertToArray(ByteArray array) throws SQLException{
        Integer[] intArray = array.stream().mapToObj(Integer::new).toArray(Integer[]::new);
        return connection.createArrayOf("INT", intArray);
    }

    private int insertClassRef(String className) throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO CLASSES (ID, IDS_REF, CLASS_NAME) VALUES (?, ?, ?)")){
            statement.setInt(1, classRefIndex.increment());
            statement.setInt(2, idIndex.refer());
            statement.setString(3, className);
            statement.executeUpdate();
            return classRefIndex.refer();
        }
    }

    private int insertIds(String groupId, String artifactId, String version) throws SQLException{
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO IDS (ID, GROUP_ID, ARTIFACT_ID, VERSION) VALUES (?, ?, ?, ?);")){
            statement.setInt(1, idIndex.increment());
            statement.setString(2, groupId);
            statement.setString(3, artifactId);
            statement.setString(4, version);
            statement.executeUpdate();
            return idIndex.refer();
        }
    }

    @Override
    public Boolean build() {
        return success;
    }
}
