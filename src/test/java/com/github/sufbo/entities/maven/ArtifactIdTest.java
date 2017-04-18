package com.github.sufbo.entities.maven;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Objects;

import org.junit.Test;

import com.github.sufbo.entities.Name;

public class ArtifactIdTest {
    @Test
    public void testEquals(){
        ArtifactId a10 = new ArtifactId("a1");
        ArtifactId a20 = new ArtifactId("a2");
        ArtifactId a12 = new ArtifactId("a1");

        assertThat(Objects.equals(a10, a12), is(true));
        assertThat(a10.equals(a12), is(true));
        assertThat(a10.equals(a20), is(false));
        assertThat(a10.equals(new Name("a1")), is(false));
    }
}
