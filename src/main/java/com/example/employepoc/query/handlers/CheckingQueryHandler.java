package com.example.employepoc.query.handlers;

import com.example.employepoc.command.exceptions.UserNotFoundException;
import com.example.employepoc.query.queries.*;
import com.example.employepoc.query.rest.dto.Checking;
import com.example.employepoc.query.rest.dto.Person;
import com.example.employepoc.query.rest.repository.CheckingQueryRepository;
import com.example.employepoc.query.rest.repository.PersonQueryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class for handling checking-related queries.
 * Implements {@link CheckingQueryHandlerInterface} to process various types of checking queries,
 * such as retrieving checkings for a single person, multiple persons, by date, and more.
 */
@Service
@Slf4j
@AllArgsConstructor
public class CheckingQueryHandler implements CheckingQueryHandlerInterface {

   private final CheckingQueryRepository repository;
   private final PersonQueryRepository personQueryRepository;

    /**
     * Constructs a CheckingQueryHandler with a specified {@link CheckingQueryService}.
     *
     * @param checkingQueryService The service used to query checking data.
     */

    /**
     * Handles a query to retrieve checkings for a single person.
     *
     * @param query The query containing the person's ID and the date range for the checkings.
     * @return A list of {@link Checking} instances for the specified person.
     */
    @Override
    public List<Checking> handle(GetPersonCheckingsQuery query) {
        log.info("Handling GetPersonCheckingsQuery: {}", query);
        return repository.findAll().stream()
                .filter(checking -> checking.getPerson().getId().equals(query.getPersonId())
                        && checking.getActualTime().isAfter(query.getFrom())
                        && checking.getActualTime().isBefore(query.getTo()))
                .collect(Collectors.toList());
    }

    /**
     * Handles a query to retrieve checkings for multiple persons.
     *
     * @param query The query containing the list of person IDs and the date range for the checkings.
     * @return A map where each key is a person's ID and the value is a list of {@link Checking} instances for that person.
     */
    @Override
    public Map<String, List<Checking>> handle(GetPersonsCheckingsQuery query) {
        log.info("Handling GetPersonsCheckingsQuery: {}", query);
        return repository.findAll().stream()
                .filter(checking -> query.getPersonsId().contains(checking.getPerson().getId())
                        && checking.getActualTime().isAfter(query.getFrom())
                        && checking.getActualTime().isBefore(query.getTo()))
                .collect(Collectors.groupingBy(checking -> checking.getPerson().getId()));    }

    /**
     * Handles a query to retrieve checkings for users by dates and persons map.
     *
     * @param query The query specifying the date range and person IDs for the checkings.
     * @return A map where each key is a date and the value is another map.
     *         The nested map's key is a person's ID and the value is a list of {@link Checking} instances for that date and person.
     */
    @Override
    public Map<LocalDate, Map<String, List<Checking>>> handle(GetUserCheckingsByDatesAndPersonsMapQuery query) {
        log.info("Handling GetUserCheckingsByDatesAndPersonsMapQuery: {}", query);
        // Assuming personQueryRepository.findById(id) returns an Optional<Person>, hence the need for .orElse(null).
        // Adjust according to your actual method signature and handling of missing persons.
        Map<String, Person> personsMap = query.getPersonsIds().stream()
                .collect(Collectors.toMap(
                        personId -> personId,
                        personId -> personQueryRepository.findById(personId).orElse(null)
                ));

        return query.getDates().stream()
                .collect(Collectors.toMap(
                        date -> date,
                        date -> personsMap.keySet().stream()
                                .collect(Collectors.toMap(
                                        personId -> personId,
                                        personId -> repository.findAll().stream()
                                                .filter(checking -> checking.getPerson().getId().equals(personId)
                                                        && checking.getActualTime().toLocalDate().equals(date))
                                                .collect(Collectors.toList())
                                ))
                ));
    }

    /**
     * Handles a query to retrieve checkings for users by persons and dates map.
     *
     * @param query The query specifying the person IDs and the date range for the checkings.
     * @return A map where each key is a person's ID and the value is another map.
     *         The nested map's key is a date and the value is a list of {@link Checking} instances for that person and date.
     */
    @Override
    public Map<String, Map<LocalDate, List<Checking>>> handle(GetUserCheckingsByPersonsAndDatesMapQuery query) {
        log.info("Handling GetUserCheckingsByPersonsAndDatesMapQuery: {}", query);
        // Assuming personQueryRepository.findById(id) returns
        Map<String, Person> personsMap = query.getPersonsIds().stream()
                .collect(Collectors.toMap(
                        personId -> personId,
                        personId -> personQueryRepository.findById(personId).orElse(null)
                ));
        return query.getPersonsIds().stream().collect(Collectors.toMap(
                personId->personId,
                personId->query.getDates().stream().collect(Collectors.toMap(
                        date->date,
                        date->repository.findAll().stream()
                                .filter(checking -> checking.getActualTime().toLocalDate().equals(date)
                                        && checking.getPerson().getId().equals(personId))
                                .collect(Collectors.toList())
                ))
        ));
    }

    /**
     * Handles a query to retrieve all checkings for a user.
     *
     * @param query The query containing the user's ID.
     * @return A list of {@link Checking} instances for the specified user.
     */
    @Override
    public List<Checking> handle(GetUserCheckingsQuery query) {
        // Fetch the Person object for the given personId
        Person person = personQueryRepository.findById(query.getPersonId()).orElse(null);

        // Check if the user associated with the person is null
        if (person == null || person.getUser() == null) {
            throw new UserNotFoundException("User not found for personId: " + query.getPersonId());
        }

        // Proceed with filtering the checkings if the user exists
        return repository.findAll().stream().filter(checking ->
                checking.getPerson().getId().equals(query.getPersonId())
                        && checking.getActualTime().toLocalDate().equals(query.getDate())
        ).collect(Collectors.toList());
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
        return repository.findAll().stream()
                .filter(checking -> checking.getPerson().getId().equals(query.getPersonId())
                        && checking.getActualTime().toLocalDate().equals(query.getDate())
                        && checking.getData().get("collective").equals("true"))
                .collect(Collectors.toList());    }

    /**
     * Handles a query to retrieve checkings for a specific day.
     *
     * @param query The query specifying the date for the checkings.
     * @return A list of {@link Checking} instances for the specified day.
     */
    @Override
    public List<Checking> handle(GetDayCheckingsQuery query) {
        log.info("Handling GetDayCheckingsQuery: {}", query);
        return repository.findAll().stream()
                .filter(checking -> checking.getPerson().getId().equals(query.getPersonId())
                        && checking.getActualTime().toLocalDate().equals(query.getDate())
                )
                .collect(Collectors.toList());    }

    /**
     * Retrieves all checkings
     *

     * @return A collection of {@link Checking}
     */
    @Override
    public List<Checking> handle(GetAllCheckingsQuery query) {
        return repository.findAll();
    }
}


