package com.demodayapi.exceptions;

public class AccEvalCriteriaNameCanNotBeNullException extends RuntimeException {
    public AccEvalCriteriaNameCanNotBeNullException() {
        super("Nome,criterio de aceitação e avaliação não podem ser nulos.");
    }

    public AccEvalCriteriaNameCanNotBeNullException(String message) {
        super(message);
    }
}