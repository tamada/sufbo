package com.github.sufbo.stats;

import com.github.sufbo.entities.ByteArray;
import com.github.sufbo.entities.java.Bytecode;
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

public class ItemBuilder {
    public Item build(String line){
        String[] items = line.split(",");
        return new Item(buildIds(items[0], items[1], items[2]), buildItemInfo(items));
    }

    private ItemInfo buildItemInfo(String[] items){
        String bytecode = items.length > 8? items[8]: "";
        MethodInformation method = buildMethodInformation(items[4], items[5]);
        return new ItemInfo(new ClassName(items[3]), method, new Bytecode(ByteArray.parse(bytecode)));
    }

    private MethodInformation buildMethodInformation(String methodName, String descriptor){
        return new MethodInformation(new MethodName(methodName),
                new Descriptor(descriptor));
    }

    private Ids buildIds(String group, String artifact, String version){
        return new Ids(new GroupId(group), new ArtifactId(artifact), new Version(version));
    }
}
