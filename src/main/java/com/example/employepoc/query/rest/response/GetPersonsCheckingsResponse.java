package com.example.employepoc.query.rest.response;

import com.example.employepoc.query.rest.dto.Checking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Represents the response structure for queries fetching checkings information for multiple persons.
 * Extends {@link BaseResponse} to include common response attributes like messages.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GetPersonsCheckingsResponse extends BaseResponse {
    /**
     * A map where each key is a person's ID and the value is a list of {@link Checking} objects associated with that person.
     */
    private Map<Long, List<Checking>> checkings;

    /**
     * Constructs a new GetPersonsCheckingsResponse with a message and checkings data.
     *
     * @param message The response message, typically indicating the status or result of the query.
     * @param checkings A map of person IDs to their respective lists of {@link Checking} objects.
     */
    public GetPersonsCheckingsResponse(String message, Map<Long, List<Checking>> checkings) {
        super(message);
        this.checkings = checkings;
    }
}