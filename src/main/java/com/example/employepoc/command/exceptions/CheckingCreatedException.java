package com.example.employepoc.command.exceptions;

/**
 * Custom exception class that represents an error when a checking operation is successfully created.
 * This exception is thrown to indicate that a specific checking-related operation,
 * such as creating a new checking record, has encountered an issue that warrants
 * throwing an exception, despite the operation's successful completion.
 *
 * but additional validation or post-creation checks fail.
 */
public class CheckingCreatedException extends RuntimeException{
    /**
     * Constructs a new CheckingCreatedException with the specified detail message.
     * The message can be used to provide additional information about the error.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
     */
    public CheckingCreatedException(String message) {
        super(message);
    }
}