package com.demodayapi.exceptions;

public class ValidateBiggestBetweenInitEndException extends RuntimeException {
    public ValidateBiggestBetweenInitEndException() {
        super("A data final de uma fase deve ser menor que a inicial da fase seguinte ");
    }
}

