package com.example.employepoc.command.rest.requests;

import com.example.employepoc.command.rest.dto.Checking;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a request to create a new checking record or multiple checking records associated with a person.
 * This class encapsulates the data necessary for creating a checking record, including the person ID and any additional checkings.
 */
@Data
public class CreateCheckingRequest {
    private Checking checking; // The primary checking record to be created
    private String personId; // The ID of the person associated with the checking
    private ArrayList<Checking> others; // Additional checking records related to the primary checking
}