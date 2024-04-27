package com.demodayapi.exceptions;

public class ValidateBiggestBetweenInitEndException extends RuntimeException {
    public ValidateBiggestBetweenInitEndException() {
        super("As datas devem ser dispostas de forma cronológica: Data inicial menor que data final em todas as fases");
    }

    public ValidateBiggestBetweenInitEndException(String message) {
        super(message);
    }
}

