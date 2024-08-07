package com.example.employepoc.query.rest.response;

import com.example.employepoc.query.rest.dto.Checking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

/**
 * Represents the response structure for queries fetching checkings for a specific day.
 * This class extends {@link BaseResponse} to include a message alongside the main data.
 * The main data is a collection of {@link Checking} objects representing the checkings for the day.
 *
 * @Data Generates getters and setters for the class fields.
 * @SuperBuilder Enables the builder pattern for this class, facilitating an easier way to create instances.
 * @NoArgsConstructor Generates a no-argument constructor.
 * @AllArgsConstructor Generates a constructor with arguments for all fields.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GetDayCheckingsResponse extends BaseResponse{
    // Collection of checkings for the specified day.
    private Collection<Checking> checkings;

    /**
     * Constructs a new response object with a message and checkings data.
     *
     * @param message The message associated with the response, typically used for indicating the status or result of the query.
     * @param checkings The collection of checkings data for the day.
     */
    public GetDayCheckingsResponse(String message, Collection<Checking> checkings) {
        super(message);
        this.checkings = checkings;
    }

    public Collection<Checking> getCheckings() {
        return checkings;
    }

    public void setCheckings(Collection<Checking> checkings) {
        this.checkings = checkings;
    }
}