package com.github.sufbo.stats.entities;

import com.github.sufbo.utils.Stringifier;

public interface ItemStringifier extends Stringifier<Item>{
    String stringify(Item item);
}
