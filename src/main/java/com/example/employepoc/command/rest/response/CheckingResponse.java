package com.example.employepoc.command.rest.response;

import com.example.employepoc.command.rest.dto.Checking;
import com.example.employepoc.query.rest.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Represents a response wrapper for checking operations.
 * This class encapsulates the result of a checking operation, providing a message
 * and optionally the {@link Checking} object involved in the operation.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CheckingResponse extends BaseResponse {
    private String message; // Message describing the outcome of the operation
    private Checking checking; // The Checking object related to the operation, if applicable


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