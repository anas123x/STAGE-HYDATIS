package com.example.employepoc.command.rest.requests;

import lombok.Data;
import org.joda.time.LocalDate;

import java.util.List;
/**
 * Represents a request to create checkings for multiple persons on a specific date, with an option for collective checkings.
 * This request supports the creation of either individual or collective checkings for a group of persons.
 */
@Data
public class CreatePersonsCheckingRequest {
    private List<String> personIds;
    private LocalDate date;
    private String threeDaysTime;
}
