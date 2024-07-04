package com.example.employepoc.query.rest.services;

import com.example.employepoc.query.rest.dto.Checking;
import com.example.employepoc.query.rest.dto.Person;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Interface defining the operations for querying checkings related to persons.
 * Provides methods to retrieve checkings for individual or multiple persons over specific time periods,
 * and to organize these checkings by various criteria such as date and person.
 */
public interface ICheckingQueryService {
    /**
     * Retrieves all checkings for a given person within a specified time range.
     *
     * @param personId The ID of the person.
     * @param from The start of the time range.
     * @param to The end of the time range.
     * @return A collection of {@link Checking} instances within the specified time range for the given person.
     */
    Collection<Checking> getPersonCheckings(Long personId, LocalDateTime from, LocalDateTime to);

    /**
     * Retrieves checkings for multiple persons within a specified time range, organized by person ID.
     *
     * @param personsId A list of person IDs.
     * @param from The start of the time range.
     * @param to The end of the time range.
     * @return A map with person IDs as keys and lists of {@link Checking} instances as values.
     */
    Map<Long, List<Checking>> getPersonsCheckings(List<Long> personsId, LocalDateTime from, LocalDateTime to);

    /**
     * Retrieves checkings for multiple persons across specific dates, organized by date and person ID.
     *
     * @param persons A map with person IDs as keys and {@link Person} instances as values.
     * @param dates A collection of dates.
     * @return A map with dates as keys and another map as values, which contains person IDs and their respective lists of {@link Checking} instances.
     */
    Map<LocalDate, Map<Long, List<Checking>>> getUserCheckingsByDatesAndPersonsMap(Map<Long, Person> persons,
                                                                                   Collection<LocalDate> dates);

    /**
     * Retrieves checkings for multiple persons across specific dates, organized by person ID and date.
     *
     * @param persons A map with person IDs as keys and {@link Person} instances as values.
     * @param dates A collection of dates.
     * @return A map with person IDs as keys and another map as values, which contains dates and their respective lists of {@link Checking} instances.
     */
    Map<Long, Map<LocalDate, List<Checking>>> getUserCheckingsByPersonsAndDatesMap(Map<Long, Person> persons,
                                                                                   Collection<LocalDate> dates);

    /**
     * Retrieves all checkings for a given person on a specific date.
     *
     * @param personId The ID of the person.
     * @param date The specific date.
     * @return A list of {@link Checking} instances for the given person on the specified date.
     */
    List<Checking> getUserCheckings(Long personId, LocalDate date);

    /**
     * Retrieves collective checkings for a given person on a specific date.
     * This method may be intended to aggregate or summarize checkings in some manner.
     *
     * @param personId The ID of the person.
     * @param date The specific date.
     * @return A list of {@link Checking} instances representing collective checkings for the given person on the specified date.
     */
    List<Checking> getCollectiveCheckings(Long personId, LocalDate date);

    /**
     * Retrieves day checkings for a given person on a specific date.
     * This method may focus on checkings that occur during the day as opposed to other time periods.
     *
     * @param personId The ID of the person.
     * @param date The specific date.
     * @return A list of {@link Checking} instances for the given person on the specified date, possibly filtered to include only day-time checkings.
     */
    List<Checking> getDayCheckings(Long personId, LocalDate date);
}