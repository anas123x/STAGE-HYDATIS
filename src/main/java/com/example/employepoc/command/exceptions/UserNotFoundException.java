package com.example.employepoc.command.exceptions;

/**
 * Custom exception class for handling user not found scenarios.
 * This exception is thrown when an operation is requested on a user that does not exist in the system.
 */
public class UserNotFoundException extends RuntimeException{
    /**
     * Constructor for UserNotFoundException with a message parameter.
     * @param message The detail message. The detail message is saved for later retrieval by the {@link Throwable#getMessage()} method.
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}