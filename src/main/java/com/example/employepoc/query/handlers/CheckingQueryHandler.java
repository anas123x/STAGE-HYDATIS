package com.example.employepoc.query.handlers;

import com.example.employepoc.query.queries.*;
import com.example.employepoc.query.rest.dto.Checking;
import com.example.employepoc.query.rest.services.CheckingQueryService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Service class for handling checking-related queries.
 * Implements {@link CheckingQueryHandlerInterface} to process various types of checking queries,
 * such as retrieving checkings for a single person, multiple persons, by date, and more.
 */
@Service
@Slf4j
public class CheckingQueryHandler implements CheckingQueryHandlerInterface {

    private final CheckingQueryService checkingQueryService;

    /**
     * Constructs a CheckingQueryHandler with a specified {@link CheckingQueryService}.
     *
     * @param checkingQueryService The service used to query checking data.
     */
    @Autowired
    public CheckingQueryHandler(CheckingQueryService checkingQueryService) {
        this.checkingQueryService = checkingQueryService;
    }

    /**
     * Handles a query to retrieve checkings for a single person.
     *
     * @param query The query containing the person's ID and the date range for the checkings.
     * @return A list of {@link Checking} instances for the specified person.
     */
    @Override
    public List<Checking> handle(GetPersonCheckingsQuery query) {
        log.info("Handling GetPersonCheckingsQuery: {}", query);
        return checkingQueryService.getPersonCheckings(query.getPersonId(), query.getFrom(), query.getTo());
    }

    /**
     * Handles a query to retrieve checkings for multiple persons.
     *
     * @param query The query containing the list of person IDs and the date range for the checkings.
     * @return A map where each key is a person's ID and the value is a list of {@link Checking} instances for that person.
     */
    @Override
    public Map<Long, List<Checking>> handle(GetPersonsCheckingsQuery query) {
        log.info("Handling GetPersonsCheckingsQuery: {}", query);
        return checkingQueryService.getPersonsCheckings(query.getPersonsId(), query.getFrom(), query.getTo());
    }

    /**
     * Handles a query to retrieve checkings for users by dates and persons map.
     *
     * @param query The query specifying the date range and person IDs for the checkings.
     * @return A map where each key is a date and the value is another map.
     *         The nested map's key is a person's ID and the value is a list of {@link Checking} instances for that date and person.
     */
    @Override
    public Map<LocalDate, Map<Long, List<Checking>>> handle(GetUserCheckingsByDatesAndPersonsMapQuery query) {
        log.info("Handling GetUserCheckingsByDatesAndPersonsMapQuery: {}", query);
        return checkingQueryService.getUserCheckingsByDatesAndPersonsMap(query.getPersons(), query.getDates());
    }

    /**
     * Handles a query to retrieve checkings for users by persons and dates map.
     *
     * @param query The query specifying the person IDs and the date range for the checkings.
     * @return A map where each key is a person's ID and the value is another map.
     *         The nested map's key is a date and the value is a list of {@link Checking} instances for that person and date.
     */
    @Override
    public Map<Long, Map<LocalDate, List<Checking>>> handle(GetUserCheckingsByPersonsAndDatesMapQuery query) {
        log.info("Handling GetUserCheckingsByPersonsAndDatesMapQuery: {}", query);
        return checkingQueryService.getUserCheckingsByPersonsAndDatesMap(query.getPersons(), query.getDates());
    }

    /**
     * Handles a query to retrieve all checkings for a user.
     *
     * @param query The query containing the user's ID.
     * @return A list of {@link Checking} instances for the specified user.
     */
    @Override
    public List<Checking> handle(GetUserCheckingsQuery query) {
        log.info("Handling GetUserCheckingsQuery: {}", query);
        return checkingQueryService.getUserCheckings(query.getPersonId(), query.getDate());
    }

    /**
     * Handles a query to retrieve collective checkings.
     *
     * @param query The query specifying the criteria for the collective checkings.
     * @return A list of {@link Checking} instances that meet the criteria for collective checkings.
     */
    @Override
    public List<Checking> handle(GetCollectiveCheckingsQuery query) {
        log.info("Handling GetCollectiveCheckingsQuery: {}", query);
        return checkingQueryService.getCollectiveCheckings(query.getPersonId(), query.getDate());
    }

    /**
     * Handles a query to retrieve checkings for a specific day.
     *
     * @param query The query specifying the date for the checkings.
     * @return A list of {@link Checking} instances for the specified day.
     */
    @Override
    public List<Checking> handle(GetDayCheckingsQuery query) {
        log.info("Handling GetDayCheckingsQuery: {}", query);
        return checkingQueryService.getDayCheckings(query.getPersonId(), query.getDate());
    }
}