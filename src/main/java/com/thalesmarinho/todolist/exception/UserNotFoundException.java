package com.thalesmarinho.todolist.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username) {
        super("Username " + username + " cannot be found");
    }
}