package com.demodayapi.exceptions;

public class ThereIsNotPeriodOfEvaluationException extends RuntimeException  {
    public ThereIsNotPeriodOfEvaluationException() {
        super("O periodo de avaliação de projetos não está ativo.");
    }

    public ThereIsNotPeriodOfEvaluationException(String message) {
        super(message);
    }
}
