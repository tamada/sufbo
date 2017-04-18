package com.github.sufbo.entities.maven;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Objects;

import org.junit.Test;

public class GroupIdTest {
    @Test
    public void testEquals(){
        GroupId g10 = new GroupId("g1");
        GroupId g20 = new GroupId("g2");
        GroupId g12 = new GroupId("g1");

        assertThat(Objects.equals(g10, g12), is(true));
        assertThat(g10.equals(g12), is(true));
        assertThat(g10.equals(g20), is(false));
    }
}
