package com.example.employepoc.command.rest.response;

import com.example.employepoc.command.rest.dto.Checking;

/**
 * Represents a response wrapper for checking operations.
 * This class encapsulates the result of a checking operation, providing a message
 * and optionally the {@link Checking} object involved in the operation.
 */
public class CheckingResponse {
    private String message; // Message describing the outcome of the operation
    private Checking checking; // The Checking object related to the operation, if applicable

    /**
     * Constructs a new CheckingResponse with a message and a Checking object.
     *
     * @param message The message describing the outcome of the operation.
     * @param checking The Checking object involved in the operation.
     */
    public CheckingResponse(String message, Checking checking) {
        this.message = message;
        this.checking = checking;
    }

    /**
     * Constructs a new CheckingResponse with only a message.
     * This constructor can be used when there's no Checking object to associate with the response.
     *
     * @param message The message describing the outcome of the operation.
     */
    public CheckingResponse(String message) {
        this.message = message;
    }
}