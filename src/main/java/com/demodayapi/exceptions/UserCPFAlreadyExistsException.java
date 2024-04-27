package com.demodayapi.exceptions;

public class UserCPFAlreadyExistsException extends RuntimeException {
    public UserCPFAlreadyExistsException() {
        super("CPF já registrado.");
    }

    public UserCPFAlreadyExistsException(String message) {
        super(message);
    }
}
