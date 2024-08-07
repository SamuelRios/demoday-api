package com.demodayapi.exceptions;

public class PhaseThreeNotCompletedException   extends RuntimeException  {
    public PhaseThreeNotCompletedException() {
        super("Fase de votação (fase 3) do Demoday ainda não foi concluída.");
    }

    public PhaseThreeNotCompletedException(String message) {
        super(message);
    }
}
