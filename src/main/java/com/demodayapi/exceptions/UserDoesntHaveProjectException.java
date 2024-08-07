package com.demodayapi.exceptions;

public class  UserDoesntHaveProjectException extends RuntimeException{
    public UserDoesntHaveProjectException() {
        super("O usuário não possui projeto submetido.");
    }

    public UserDoesntHaveProjectException(String message) {
        super(message);
    }
    
}

