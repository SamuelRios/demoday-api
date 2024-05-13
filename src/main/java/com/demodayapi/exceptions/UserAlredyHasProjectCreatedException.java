package com.demodayapi.exceptions;

public class UserAlredyHasProjectCreatedException extends RuntimeException {
    public UserAlredyHasProjectCreatedException() {
        super("Já existe projeto cadastrado para este usuario.");
    }

    public UserAlredyHasProjectCreatedException(String message) {
        super(message);
    }

}
