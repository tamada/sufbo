package com.github.sufbo;

import java.util.Arrays;

import com.github.sufbo.extractor.ExtractingProcessor;

public class ExtractingProcessorBuilder extends ProcessorBuilder {
    private static final String COMMAND_STRING = "extract";

    @Override
    public Processor construct(String[] args) {
        OptionsArgumentsBuilder builder = OptionsArgumentsBuilder
                .build(Arrays.asList(availableOptions()), args);
        return new ExtractingProcessor(builder.arguments(), builder.options());
    }

    @Override
    public boolean isTarget(String flag) {
        return flag.equals(COMMAND_STRING);
    }

    private Key[] availableOptions(){
        return new Key[] { Key.GROUP_ID, Key.ARTIFACT_ID, Key.VERSION, Key.DEST, Key.HELP, }; 
    }
}
