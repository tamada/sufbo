package com.github.sufbo.db;

import com.github.sufbo.stats.entities.Item;
import com.github.sufbo.stats.entities.visitor.ItemVisitor;

public interface ItemConverter<T> extends ItemVisitor {
    default T convert(Item item){
        item.accept(this);
        return build();
    }

    T build();
}
