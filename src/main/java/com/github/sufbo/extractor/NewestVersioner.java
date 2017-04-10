package com.github.sufbo.extractor;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

import com.github.sufbo.entities.maven.Artifact;
import com.github.sufbo.entities.maven.ArtifactComparator;
import com.github.sufbo.entities.maven.Artifacts;
import com.github.sufbo.entities.maven.MavenArtifactsBuilder;

public class NewestVersioner {
    public void run(String[] args) throws IOException{
        Path basePath = Paths.get(args[0]);
        MavenArtifactsBuilder builder = new MavenArtifactsBuilder();
        Artifacts artifacts = builder.build(basePath);
        printNewestVersion(artifacts.stream());
    }

    private void printNewestVersion(Stream<Artifact> stream) throws IOException{
        List<Artifact> list = stream.filter(artifact -> accept(artifact.path()))
                .collect(new NewestVersionCollector());
        list.stream().map(artifact -> artifact.path())
        .forEach(System.out::println);
    }

    private boolean accept(Path targetPath){
        String target = targetPath.toString();
        return target.endsWith(".jar") 
                && !target.endsWith("-sources.jar")
                && !target.endsWith("-javadoc.jar");
    }

    public static void main(String[] args) throws IOException{
        NewestVersioner versioner = new NewestVersioner();
        versioner.run(args);
    }

    private static class NewestVersionCollector implements Collector<Artifact, List<Artifact>, List<Artifact>>{
        private Artifact lastArtifact;
        private ArtifactComparator comparator = new ArtifactComparator();

        @Override
        public Supplier<List<Artifact>> supplier() {
            return () -> new ArrayList<>();
        }

        @Override
        public BiConsumer<List<Artifact>, Artifact> accumulator() {
            return (list, artifact) -> accumulate(list, artifact);
        }

        private List<Artifact> accumulate(List<Artifact> list, Artifact artifact){
            if(lastArtifact != null && lastArtifact.isSameArtifact(artifact)){
                lastArtifact = comparator.newer(lastArtifact, artifact);
            }
            else{
                if(lastArtifact != null)
                    list.add(lastArtifact);
                lastArtifact = artifact;
            }
            return list;
        }

        @Override
        public BinaryOperator<List<Artifact>> combiner() {
            return (list1, list2) -> {
                list1.addAll(list2);
                return list1;
            };
        }

        @Override
        public Function<List<Artifact>, List<Artifact>> finisher() {
            return list -> list;
        }

        @Override
        public Set<Collector.Characteristics> characteristics() {
            Set<Collector.Characteristics> set = new HashSet<>();
            set.add(Collector.Characteristics.IDENTITY_FINISH);
            return set;
        }
    }
}
