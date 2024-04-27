package com.demodayapi.exceptions;

public class UserEmailAlreadyExistsException extends RuntimeException {
    public UserEmailAlreadyExistsException() {
        super("Email já registrado.");
    }

    public UserEmailAlreadyExistsException(String message) {
        super(message);
    }
}
