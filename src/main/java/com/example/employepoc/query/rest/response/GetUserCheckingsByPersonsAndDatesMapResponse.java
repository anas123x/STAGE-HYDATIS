package com.example.employepoc.query.rest.response;

import com.example.employepoc.query.rest.dto.Checking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.Map;

/**
 * Represents the response structure for queries fetching checkings by person IDs and date ranges.
 * This class extends {@link BaseResponse} to include a message alongside the main data.
 * The main data is a nested map where the first key is a person's ID (Long),
 * and the value is another map with dates as keys and lists of {@link Checking} objects as values.
 *
 * @author Data
 * @SuperBuilder Enables the builder pattern for this class, facilitating an easier way to create instances.
 * @NoArgsConstructor Generates a no-argument constructor.
 * @AllArgsConstructor Generates a constructor with arguments for all fields.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserCheckingsByPersonsAndDatesMapResponse extends BaseResponse{
    // Map structure holding the checkings information. The outer map's key is the person's ID,
    // the inner map's key is the LocalDate, and the value is a list of checkings for that date.
    Map<Long, Map<LocalDate, List<Checking>>> checkings;

    /**
     * Constructs a new response object with a message and checkings data.
     *
     * @param message The message associated with the response, typically used for indicating the status or result of the query.
     * @param checkings The nested map of checkings data, organized by person ID and date.
     */
    public GetUserCheckingsByPersonsAndDatesMapResponse(String message, Map<Long, Map<LocalDate, List<Checking>>> checkings) {
        super(message);
        this.checkings = checkings;
    }
}