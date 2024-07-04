package com.example.employepoc.query.rest.response;

import com.example.employepoc.query.rest.dto.Checking;
import com.hydatis.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

/**
 * Response class for fetching checking records for a single person.
 * Extends {@link BaseResponse} to include common response attributes like messages.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GetPersonCheckingsResponse extends BaseResponse {
    /**
     * A collection of {@link Checking} objects representing the checkings associated with a person.
     */
    private Collection<Checking> checkings;

    /**
     * Constructs a new GetPersonCheckingsResponse with a message and checkings data.
     *
     * @param message The response message, typically indicating the status or result of the query.
     * @param checkings A collection of {@link Checking} objects associated with the person.
     */
    public GetPersonCheckingsResponse(String message, Collection<Checking> checkings) {
        super(message);
        this.checkings = checkings;
    }
}