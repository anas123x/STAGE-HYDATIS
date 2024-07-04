package com.example.employepoc.query.handlers;

import com.example.employepoc.query.queries.*;
import com.example.employepoc.query.rest.dto.Checking;
import org.joda.time.LocalDate;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Interface for handling various checking-related queries.
 * This interface defines methods for processing queries related to checkings,
 * such as retrieving checkings for a single person, multiple persons, by date, and more.
 */
public interface CheckingQueryHandlerInterface {
    /**
     * Handles a query to retrieve checkings for a single person.
     *
     * @param query The query containing the person's ID and the date range for the checkings.
     * @return A collection of {@link Checking} instances for the specified person.
     */
    Collection<Checking> handle(GetPersonCheckingsQuery query);

    /**
     * Handles a query to retrieve checkings for multiple persons.
     *
     * @param query The query containing the list of person IDs and the date range for the checkings.
     * @return A map where each key is a person's ID and the value is a list of {@link Checking} instances for that person.
     */
    Map<Long, List<Checking>> handle(GetPersonsCheckingsQuery query);

    /**
     * Handles a query to retrieve checkings for users by dates and persons map.
     *
     * @param query The query specifying the date range and person IDs for the checkings.
     * @return A map where each key is a date and the value is another map.
     *         The nested map's key is a person's ID and the value is a list of {@link Checking} instances for that date and person.
     */
    Map<LocalDate, Map<Long, List<Checking>>> handle(GetUserCheckingsByDatesAndPersonsMapQuery query);

    /**
     * Handles a query to retrieve checkings for users by persons and dates map.
     *
     * @param query The query specifying the person IDs and the date range for the checkings.
     * @return A map where each key is a person's ID and the value is another map.
     *         The nested map's key is a date and the value is a list of {@link Checking} instances for that person and date.
     */
    Map<Long, Map<LocalDate, List<Checking>>> handle(GetUserCheckingsByPersonsAndDatesMapQuery query);

    /**
     * Handles a query to retrieve all checkings for a user.
     *
     * @param query The query containing the user's ID.
     * @return A list of {@link Checking} instances for the specified user.
     */
    List<Checking> handle(GetUserCheckingsQuery query);

    /**
     * Handles a query to retrieve collective checkings.
     *
     * @param query The query specifying the criteria for the collective checkings.
     * @return A list of {@link Checking} instances that meet the criteria for collective checkings.
     */
    List<Checking> handle(GetCollectiveCheckingsQuery query);

    /**
     * Handles a query to retrieve checkings for a specific day.
     *
     * @param query The query specifying the date for the checkings.
     * @return A list of {@link Checking} instances for the specified day.
     */
    List<Checking> handle(GetDayCheckingsQuery query);
}