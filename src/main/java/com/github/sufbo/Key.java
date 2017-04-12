package com.github.sufbo;

import java.util.Objects;

public class Key {
    public static final Key ARTIFACT_ID = new Key("-a", "--artifactId", true);
    public static final Key GROUP_ID = new Key("-g", "--groupId", true);
    public static final Key VERSION = new Key("-v", "--version", true);
    public static final Key DEST = new Key("-d", "--dest", true);
    public static final Key HELP = new Key("-h", "--help", false);
    public static final Key NULL = new Key("", "", false);

    private String option;
    private String alias;
    private boolean hasArgument;

    public Key(String option, String alias, boolean hasArgument){
        this.option = option;
        this.alias = alias;
        this.hasArgument = hasArgument;
    }

    public boolean hasArgument(){
        return hasArgument;
    }

    public boolean isSpecified(String target){
        return Objects.equals(option, target)
                || Objects.equals(alias, target);
    }
}
