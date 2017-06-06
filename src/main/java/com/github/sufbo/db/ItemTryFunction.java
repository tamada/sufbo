package com.github.sufbo.db;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.github.sufbo.entities.ByteArray;
import com.github.sufbo.entities.java.ClassName;
import com.github.sufbo.entities.java.Descriptor;
import com.github.sufbo.entities.java.MethodInformation;
import com.github.sufbo.entities.java.MethodName;
import com.github.sufbo.entities.maven.ArtifactId;
import com.github.sufbo.entities.maven.GroupId;
import com.github.sufbo.entities.maven.Ids;
import com.github.sufbo.entities.maven.Version;
import com.github.sufbo.stats.entities.Item;
import com.github.sufbo.stats.entities.ItemInfo;

public class ItemTryFunction implements TryFunction<ResultSet, Item, SQLException> {

    @Override
    public Item apply(ResultSet rs) throws SQLException {
        return new Item(createIds(rs), createItemInfo(rs));
    }

    private Ids createIds(ResultSet rs) throws SQLException{
        return new Ids(new GroupId(rs.getString("IDS.GROUP_ID")),
                new ArtifactId(rs.getString("IDS.ARTIFACT_ID")),
                new Version(rs.getString("IDS.VERSION")));
    }

    private ItemInfo createItemInfo(ResultSet rs) throws SQLException{
        return new ItemInfo(new ClassName(rs.getString("CLASSES.CLASS_NAME")),
                createMethodInfo(rs),
                createByteArray(rs));
    }

    private MethodInformation createMethodInfo(ResultSet rs) throws SQLException{
        return new MethodInformation(new MethodName(rs.getString("METHODS.METHOD_NAME")),
                new Descriptor(rs.getString("METHODS.DESCRIPTOR")));
    }

    private ByteArray createByteArray(ResultSet rs) throws SQLException{
        Array array = rs.getArray("METHODS.BYTECODES");
        int[] bytecodes = (int[])array.getArray();
        return new ByteArray(bytecodes);
    }
}
