package com.demodayapi.exceptions;

public class TherIsNotActiveDemodayException extends RuntimeException {

    public TherIsNotActiveDemodayException() {
        super("Nao existe demoday ativo.");
    }

    public TherIsNotActiveDemodayException(String message) {
        super(message);
    }

}
