package com.github.sufbo;

import java.util.Objects;

public enum Key {
    ARTIFACT_ID("-a", "--artifactId", true),
    GROUP_ID("-g", "--groupId", true),
    VERSION("-v", "--version", true),
    DEST("-d", "--dest", true),
    HELP("-h", "--help", false),
    NULL("", "", false);

    private String option;
    private String alias;
    private boolean hasArgument;

    private Key(String option, String alias, boolean hasArgument){
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
