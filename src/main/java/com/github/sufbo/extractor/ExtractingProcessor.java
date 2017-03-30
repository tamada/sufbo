package com.github.sufbo.extractor;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.github.sufbo.Arguments;
import com.github.sufbo.Key;
import com.github.sufbo.Options;
import com.github.sufbo.Processor;
import com.github.sufbo.entities.ArtifactsBuilder;
import com.github.sufbo.entities.SimpleArtifactsBuilder;
import com.github.sufbo.entities.maven.ArtifactId;
import com.github.sufbo.entities.maven.Artifacts;
import com.github.sufbo.entities.maven.GroupId;
import com.github.sufbo.entities.maven.Ids;
import com.github.sufbo.entities.maven.MavenArtifactsBuilder;
import com.github.sufbo.entities.maven.Version;
import com.github.sufbo.extractor.result.CsvResultWriter;

public class ExtractingProcessor implements Processor{
    private Options options;
    private Arguments arguments;

    public ExtractingProcessor(Arguments arguments, Options options){
        this.arguments = arguments;
        this.options = options;
    }

    private PrintWriter destination() throws IOException{
        if(options.has(Key.DEST))
            return new PrintWriter(buildWriter(options.value(Key.DEST)));
        return new PrintWriter(new OutputStreamWriter(System.out, options.charset()));
    }

    private Writer buildWriter(String destination) throws IOException{
        Path path = Paths.get(destination);
        return Files
                .newBufferedWriter(path, options.charset());
    }
    
    @Override
    public void run() throws IOException{
        Stream<Artifacts> builders = mapArgsToBuilders(createBuilders());
        try(PrintWriter out = destination()){
            CsvResultWriter writer = new CsvResultWriter(out);
            builders.forEach(artifacts -> artifacts.accept(writer));
        }
    }

    private Stream<Artifacts> mapArgsToBuilders(List<ArtifactsBuilder> builders){
        return arguments.stream()
                .map(path -> Paths.get(path))
                .map(path -> create(builders, path));
    }

    private List<ArtifactsBuilder> createBuilders(){
        Optional<Ids> ids = buildIds(options);
        return Arrays.asList(new MavenArtifactsBuilder(), new SimpleArtifactsBuilder(ids));
    }

    private Artifacts create(List<ArtifactsBuilder> list, Path path){
        return list.stream()
                .filter(builder -> builder.acceptable(path))
                .reduce((first, second) -> first)
                .map(builder -> builder.build(path))
                .orElseThrow(() -> new NotSupportedArtifactsException(path + ": not supported"));
    }

    private Optional<Ids> buildIds(Options options){
        if(options.hasAll(Key.GROUP_ID, Key.ARTIFACT_ID, Key.VERSION))
            return Optional.of(new Ids(groupId(), artifactId(), version()));
        return Optional.empty();
    }

    private GroupId groupId(){
        return new GroupId(options.value(Key.GROUP_ID));
    }

    private ArtifactId artifactId(){
        return new ArtifactId(options.value(Key.ARTIFACT_ID));
    }

    private Version version(){
        return new Version(options.value(Key.VERSION));
    }
}
