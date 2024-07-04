package com.example.employepoc.query.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Base class for all response objects in the application.
 * Provides a common structure for response messages.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
    /**
     * The message associated with the response, typically used for conveying the status or result.
     */
    private String message;
}