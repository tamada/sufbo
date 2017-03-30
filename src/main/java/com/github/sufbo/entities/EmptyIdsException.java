package com.github.sufbo.entities;

public class EmptyIdsException extends RuntimeException {
    private static final long serialVersionUID = 6977481992236409668L;

    public EmptyIdsException(String message){
        super(message);
    }
}
