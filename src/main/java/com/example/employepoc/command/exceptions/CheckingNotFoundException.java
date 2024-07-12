package com.example.employepoc.command.exceptions;

/**
 * Custom exception class for handling scenarios where a checking account is not found.
 * This exception is thrown when an operation is attempted on a checking account that does not exist in the system.
 */
public class CheckingNotFoundException extends RuntimeException{
    /**
     * Constructor for CheckingNotFoundException with a message parameter.
     * @param s The detail message. The detail message is saved for later retrieval by the {@link Throwable#getMessage()} method.
     */
    public CheckingNotFoundException(String s) {
        super(s);
    }
}