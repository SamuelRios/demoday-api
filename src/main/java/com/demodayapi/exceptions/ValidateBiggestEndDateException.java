package com.demodayapi.exceptions;

public class ValidateBiggestEndDateException extends RuntimeException {
    public ValidateBiggestEndDateException() {
        super("As datas finais das fases devem seguir ordem cronológica ");
    }
}

