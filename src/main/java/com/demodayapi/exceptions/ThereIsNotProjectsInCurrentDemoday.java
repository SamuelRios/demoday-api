package com.demodayapi.exceptions;

 
    public class ThereIsNotProjectsInCurrentDemoday extends RuntimeException  {
        public ThereIsNotProjectsInCurrentDemoday() {
            super("Não existem projetos pendentes.");
        }
    
        public ThereIsNotProjectsInCurrentDemoday(String message) {
            super(message);
        }
    }

