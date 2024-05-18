package com.demodayapi.exceptions;

public class ThereIsNotPeriodOfSubmissionException extends RuntimeException  {
    public ThereIsNotPeriodOfSubmissionException() {
        super("O periodo de submissão não está ativo.");
    }

    public ThereIsNotPeriodOfSubmissionException(String message) {
        super(message);
    }
}
