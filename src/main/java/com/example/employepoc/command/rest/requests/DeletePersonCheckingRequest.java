package com.example.employepoc.command.rest.requests;

import com.example.employepoc.command.rest.dto.Checking;
import lombok.Data;
import lombok.ToString;

/**
 * Represents a request to delete a person's checking record.
 * This class encapsulates the data necessary to identify and optionally handle duplicates
 * for a checking record that needs to be deleted.
 */
@Data
@ToString
public class DeletePersonCheckingRequest {
    private Checking checking; // The checking record to be deleted
    private boolean duplicate; // Flag to indicate whether to handle duplicates in deletion
}