package com.github.sufbo.entities.maven;

import static org.junit.Assert.assertThat;

import java.util.Objects;

import static org.hamcrest.Matchers.is;

import org.junit.Test;

import com.github.sufbo.entities.maven.ArtifactId;
import com.github.sufbo.entities.maven.GroupId;
import com.github.sufbo.entities.maven.Ids;
import com.github.sufbo.entities.maven.Version;

public class IdsTest {
    private GroupId    groupId    = new GroupId("g1");
    private ArtifactId artifactId = new ArtifactId("a1");
    private Version    version    = new Version("1.0.0");

    @Test
    public void testIdsEquals(){
        Ids ids1 = new Ids(groupId, artifactId, version);
        Ids ids2 = new Ids(new GroupId("g1"), new ArtifactId("a1"), new Version("1.0.0"));
        Ids ids3 = new Ids(new GroupId("g1"), new ArtifactId("a1"), new Version("2.0.0"));

        assertThat(ids1.sameArtifact(ids3), is(true));
        
        assertThat(ids1.equals(ids2), is(true));
        assertThat(Objects.equals(ids1, ids2), is(true));
        assertThat(Objects.equals(ids1, null), is(false));
        assertThat(Objects.equals(ids1, ids3), is(false));
    }
}
