package com.example.employepoc.command.exceptions;

import com.example.employepoc.command.rest.response.CheckingResponse;
import com.example.employepoc.query.rest.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;

/**
 * Global exception handler class that intercepts exceptions across the whole application.
 * It extends {@link ResponseEntityExceptionHandler} to provide centralized exception handling across all {@code @RequestMapping} methods.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles exceptions of type {@link HttpMessageNotReadableException} which occur when the request JSON is malformed.
     * Overrides the default method from {@link ResponseEntityExceptionHandler}.
     *
     * @param ex The exception that was thrown.
     * @param headers HTTP headers that can be used to modify the ResponseEntity.
     * @param status The HTTP status code to be applied to the ResponseEntity.
     * @param request The current request object.
     * @return A {@link ResponseEntity} object containing the custom error message and HTTP status code.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
        // Extract the root cause message
        String errorMessage = "Invalid request data: " + ex.getMostSpecificCause().getMessage();

        // Construct a response entity with the custom error message and HTTP status code
        return ResponseEntity.status(400).body(new BaseResponse().builder()
                .message(errorMessage)
                .build());
    }
}