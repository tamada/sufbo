package com.github.sufbo.entities;

import com.github.sufbo.entities.maven.Ids;

@FunctionalInterface
public interface IdsProvider {
    Ids provide();
}
