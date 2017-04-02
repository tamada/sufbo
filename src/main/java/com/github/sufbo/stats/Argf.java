package com.github.sufbo.stats;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Stream;

public class Argf {
    private String[] files;
    private boolean runFlag = true;

    public Argf(String[] from){
        files = new String[from.length];
        System.arraycopy(from, 0, files, 0, from.length);
    }

    public Argf(String[] from, boolean runFlag){
        this(from);
        this.runFlag = runFlag;
    }

    public boolean isRunRequested(){
        return runFlag;
    }
    
    public Stream<String> stream() throws IOException{
        if(files.length == 0)
            return openStdinStream();
        return multipleStream();
    }

    private Stream<String> openStdinStream() throws IOException{
        return new BufferedReader(new InputStreamReader(System.in, "utf-8"))
                .lines();
    }

    private Stream<String> multipleStream() throws IOException{
        return Arrays.stream(files)
                .map(file -> createReader(file))
                .flatMap(reader -> reader.lines());
    }

    private BufferedReader createReader(String file){
        try{
            return new BufferedReader(new FileReader(file));
        } catch(IOException e){
            throw new InternalError(e);
        }
    }
}
