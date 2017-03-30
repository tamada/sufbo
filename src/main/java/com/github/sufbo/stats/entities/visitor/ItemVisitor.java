package com.github.sufbo.stats.entities.visitor;

import com.github.sufbo.entities.maven.Ids;
import com.github.sufbo.entities.visitor.Visitor;
import com.github.sufbo.stats.entities.Item;
import com.github.sufbo.stats.entities.ItemInfo;

public interface ItemVisitor extends Visitor{
    default void visit(Item item){
        item.accept(this);
    }

    default void visit(ItemInfo info){
        info.accept(this);
    }

    default void visit(Ids ids){
        ids.accept(this);
    }
}
