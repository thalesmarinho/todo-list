package com.thalesmarinho.todolist.exception;

public class IdMustBeNullException extends RuntimeException {

    public IdMustBeNullException() {
        super("A new user cannot already have an ID.");
    }
}