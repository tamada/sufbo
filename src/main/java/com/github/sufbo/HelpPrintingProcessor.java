package com.github.sufbo;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class HelpPrintingProcessor implements Processor {
    @Override
    public void run() {
        try(PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out, "utf-8"))){
            printHelp(out);
        } catch(UnsupportedEncodingException e){
            throw new InternalError(e);
        }
    }

    private void printHelp(PrintWriter out) {
        out.println("java -jar sufbo.jar <COMMAND> [OPTIONS] <ARGUMENTS...>");
        out.println("COMMAND:");
        out.println("    help:    print this message and exit program.");
        out.println("    extract: extract method information from given arguments.");
        out.println("    find:    find suitable method name of given method from database.");
        out.println("OPTIONS:");
        out.println("    This part depends on command.");
        out.println("ARGUMENTS:");
        out.println("    This part depends on command.");
    }
}
