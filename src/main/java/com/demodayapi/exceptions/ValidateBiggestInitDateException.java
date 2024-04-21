package com.demodayapi.exceptions;

public class ValidateBiggestInitDateException extends RuntimeException {
    public ValidateBiggestInitDateException() {
        super("As datas iniciais das fases devem seguir ordem cronológica ");
    }
}