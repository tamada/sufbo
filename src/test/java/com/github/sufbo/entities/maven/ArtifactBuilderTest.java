package com.github.sufbo.entities.maven;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.nio.file.Paths;

import org.junit.Test;

public class ArtifactBuilderTest {
    private ArtifactBuilder builder = new ArtifactBuilder();

    @Test
    public void testBasic(){
        Artifact artifact = builder.build(Paths.get("repository"), Paths.get("repository/some/groupId/artifactId/version/artifactId-version.jar"));
        Ids ids = artifact.ids();

        assertThat(ids.groupId, is(new GroupId("some.groupId")));
        assertThat(ids.artifactId, is(new ArtifactId("artifactId")));
        assertThat(ids.version, is(new Version("version")));
    }

    @Test
    public void testTryToBuildIds(){
        Artifact artifact = builder.build(Paths.get("repository"), Paths.get("repository/some-groupId/artifactId-version.jar"));
        Ids ids = artifact.ids();

        assertThat(ids.groupId, is(new GroupId("some-groupId")));
        assertThat(ids.artifactId, is(new ArtifactId("artifactId")));
        assertThat(ids.version, is(new Version("version")));
    }
}
