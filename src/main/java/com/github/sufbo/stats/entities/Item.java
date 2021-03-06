package com.github.sufbo.stats.entities;

import com.github.sufbo.entities.ByteArray;
import com.github.sufbo.entities.java.MethodInformation;
import com.github.sufbo.entities.maven.Ids;
import com.github.sufbo.stats.entities.visitor.ItemVisitor;

public class Item {
    private Ids ids;
    private ItemInfo info;

    public Item(Ids ids, ItemInfo info){
        this.ids = ids;
        this.info = info;
    }

    public Ids version(){
        return ids;
    }

    public boolean sameArtifact(Item item){
        return ids.sameArtifact(item.ids);
    }

    public boolean isDifferentVersion(Item other){
        return ids.isDifferentVersion(other.ids);
    }

    public ByteArray bytecode(){
        return info.bytecode();
    }

    public int bytecodeLength(){
        return info.bytecodeLength();
    }

    public MethodInformation method(){
        return info.method();
    }

    public void accept(ItemVisitor visitor){
        visitor.visit(ids);
        visitor.visit(info);
    }

    @Override
    public String toString(){
        return DefaultItemStringifier.toString(this);
    }
}
