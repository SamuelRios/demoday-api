package com.demodayapi.exceptions;

public class DuplicateEvaluationByCriteriaException  extends RuntimeException  {
    public DuplicateEvaluationByCriteriaException() {
        super("Avaliação de critério duplicada.");
    }

    public DuplicateEvaluationByCriteriaException(String message) {
        super(message);
    }
}
