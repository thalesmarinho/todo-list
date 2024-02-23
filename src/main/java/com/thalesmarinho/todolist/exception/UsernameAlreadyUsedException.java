package com.thalesmarinho.todolist.exception;

public class UsernameAlreadyUsedException extends RuntimeException {

    public UsernameAlreadyUsedException() {
        super("Username already used.");
    }
}