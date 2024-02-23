package com.thalesmarinho.todolist.exception;

public class EmailAlreadyUsedException extends RuntimeException {

    public EmailAlreadyUsedException() {
        super("Email already used.");
    }
}