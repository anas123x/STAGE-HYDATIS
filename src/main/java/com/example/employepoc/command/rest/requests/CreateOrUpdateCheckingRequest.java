package com.example.employepoc.command.rest.requests;

import lombok.Data;
import org.joda.time.LocalDate;

@Data
public class CreateOrUpdateCheckingRequest {
    private Long personId; // ID of the person associated with the checking
    private String checkingId;
    private LocalDate date; // Date of the checking event
    private String threeDaysTime; // Custom field, possibly indicating a time span or specific timing
}
