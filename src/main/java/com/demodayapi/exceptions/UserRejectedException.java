package com.demodayapi.exceptions;

public class UserRejectedException  extends RuntimeException {
    public UserRejectedException() {
        super("Cadastro de usuário negado.");
    }

    public UserRejectedException(String message) {
        super(message);
    }
}
