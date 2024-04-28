package com.demodayapi.exceptions;

public class UserIsNotAdminException extends RuntimeException {
    public UserIsNotAdminException() {
        super("Usuário Admin requerido");
    }

    public UserIsNotAdminException(String message) {
        super(message);
    }
}
