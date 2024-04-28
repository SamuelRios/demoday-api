package com.demodayapi.exceptions;

public class UserIsNotAdminException extends RuntimeException {
    public UserIsNotAdminException() {
        super("Usuário não é um admin.");
    }

    public UserIsNotAdminException(String message) {
        super(message);
    }
}
