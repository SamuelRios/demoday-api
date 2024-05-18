package com.demodayapi.exceptions;

public class AreadyExistInProgressDemodayException extends RuntimeException  {
    public AreadyExistInProgressDemodayException() {
        super("Já existe um demoday em andamento.");
    }

    public AreadyExistInProgressDemodayException(String message) {
        super(message);
    }
}
