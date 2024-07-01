package com.example.employepoc.command.rest.response;

import com.example.employepoc.command.rest.dto.Checking;

public class CheckingResponse {
    private String message;
    private Checking checking;

    public CheckingResponse(String message, Checking checking) {
        this.message = message;
        this.checking = checking;
    }
    public CheckingResponse(String message) {
        this.message = message;
    }
}
