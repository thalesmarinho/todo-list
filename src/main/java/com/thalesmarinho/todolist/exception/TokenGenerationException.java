package com.thalesmarinho.todolist.exception;

public class TokenGenerationException extends RuntimeException {

    public TokenGenerationException() {
        super("Error while generating token.");
    }
}