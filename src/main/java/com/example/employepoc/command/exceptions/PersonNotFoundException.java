package com.example.employepoc.command.exceptions;

/**
 * Represents an exception that is thrown when a specified person cannot be found.
 * This custom exception class extends {@link RuntimeException} and is used throughout
 * the application to signal the absence of a person entity that is expected to exist
 * in the context of a given operation, such as deleting a person's checking information.
 */
public class PersonNotFoundException extends RuntimeException {
    /**
     * Constructs a new {@code PersonNotFoundException} with the specified detail message.
     * The detail message provides more information about the cause of the exception.
     *
     * @param message the detail message, which is saved for later retrieval by the {@link Throwable#getMessage()} method.
     */
    public PersonNotFoundException(String message) {
        super(message);
    }
}