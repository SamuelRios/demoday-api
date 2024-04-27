package com.demodayapi.exceptions;

public class UserNotLoggedException extends RuntimeException{
    public UserNotLoggedException() {
        super("Não logado.");
    }

    public UserNotLoggedException(String message) {
        super(message);
    }
}
