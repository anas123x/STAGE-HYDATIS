package com.example.employepoc.command.rest.requests;

import lombok.Data;
import org.joda.time.LocalDate;

/**
 * Represents a request to either create a new checking or update an existing one for a specific person.
 * This class encapsulates the data necessary for creating or updating a checking event, including the person's ID,
 * the checking ID (if updating), the date of the checking, and a custom field named "threeDaysTime".
 */
@Data
public class CreateOrUpdateCheckingRequest {
    private Long personId; // ID of the person associated with the checking
    private String checkingId;
    private LocalDate date; // Date of the checking event
    private String threeDaysTime; // Custom field, possibly indicating a time span or specific timing
}
