package com.meutley.studioadmintoolkit.core;

public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1569724951321181229L;

    public EntityNotFoundException(int id) {
        super(String.format("No row with id %d id could be found"));
    }
    
}