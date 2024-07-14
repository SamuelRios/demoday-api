package com.demodayapi.exceptions;

public class UserHasAlreadyRatedProjectException extends RuntimeException {
    public UserHasAlreadyRatedProjectException() {
        super("O usuário já avaliou o projeto.");
    }

    public UserHasAlreadyRatedProjectException(String message) {
        super(message);
    }
}
