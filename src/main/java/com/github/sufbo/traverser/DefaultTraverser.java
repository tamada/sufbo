package com.github.sufbo.traverser;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.spi.FileSystemProvider;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.github.sufbo.utils.LogHelper;

public class DefaultTraverser extends AbstractTraverser{
    private FileSystemProvider provider;
    private Path entryPoint;

    DefaultTraverser(FileSystem system, Path entryPoint, Path basePath){
        super(basePath);
        this.provider = system.provider();
        this.entryPoint = entryPoint;
    }

    @Override
    public Stream<Path> stream(Filter filter) throws IOException {
        return traversePathImpl(entryPoint, filter)
                .filter(path -> filter.accept(path));
    }

    private Stream<Path> traversePath(Path path, Filter filter){
        try{
            return traversePathImpl(path, filter);
        } catch(IOException e){
            LogHelper.warning(getClass(), "some error", e);
        }
        return Stream.empty();
    }

    private Stream<Path> traversePathImpl(Path path, Filter filter) throws IOException{
        if(isDirectory(path))
            return directoryStream(path, filter);
        return Stream.of(path);
    }

    private Stream<Path> directoryStream(Path path, Filter filter) throws IOException{
        DirectoryStream<Path> stream = provider.newDirectoryStream(path, target -> true);
        return StreamSupport.stream(stream.spliterator(), false)
                .flatMap(target -> traversePath(target, filter));
    }

    private boolean isDirectory(Path path) throws IOException{
        BasicFileAttributes attributes = provider
                .readAttributes(path, BasicFileAttributes.class);
        return attributes.isDirectory();
    }
}
