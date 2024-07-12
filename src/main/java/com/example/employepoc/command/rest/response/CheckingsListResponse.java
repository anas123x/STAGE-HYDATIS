package com.example.employepoc.command.rest.response;

import com.example.employepoc.command.rest.dto.Checking;
import com.example.employepoc.query.rest.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Response class for listing checkings.
 * Extends {@link BaseResponse} to include standard response attributes like success status and error messages.
 * This class is used to encapsulate the response data for requests that fetch a list of checkings.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CheckingsListResponse  extends BaseResponse {
    private String message;
    private List<Checking> checkings;
    /**
     * Response class for listing checkings.
     * Extends {@link BaseResponse} to include standard response attributes like success status and error messages.
     * This class is used to encapsulate the response data for requests that fetch a list of checkings.
     */
    public CheckingsListResponse(String message) {
        this.message = message;
    }
}
