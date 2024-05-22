package com.demodayapi.exceptions;

public class UserPedingException  extends RuntimeException {
    public UserPedingException() {
        super("Usuário pendente.");
    }

    public UserPedingException(String message) {
        super(message);
    }
}
