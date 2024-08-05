package com.demodayapi.exceptions;

public class ProjectIsNotFinalistException extends RuntimeException  {
    public ProjectIsNotFinalistException() {
        super("O projeto não é finalista.");
    }

    public ProjectIsNotFinalistException(String message) {
        super(message);
    }
}
