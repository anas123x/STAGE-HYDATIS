package com.example.employepoc.query.rest.services;

import com.example.employepoc.query.rest.dto.Checking;
import com.example.employepoc.query.rest.dto.Person;
import com.example.employepoc.query.rest.repository.CheckingQueryRepository;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class for querying checking records related to persons.
 * Implements the {@link ICheckingQueryService} interface to provide detailed querying capabilities.
 * Utilizes the {@link CheckingQueryRepository} for data access.
 */
@Service
public class CheckingQueryService implements ICheckingQueryService {
    private final CheckingQueryRepository repository;

    /**
     * Constructs a CheckingQueryService with dependency injection of {@link CheckingQueryRepository}.
     *
     * @param repository The repository used for accessing checking data.
     */
    @Autowired
    public CheckingQueryService(CheckingQueryRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves a list of {@link Checking} records for a specific person within a given time range.
     *
     * @param personId The ID of the person.
     * @param from The start of the time range.
     * @param to The end of the time range.
     * @return List of {@link Checking} records.
     */
    @Override
    public List<Checking> getPersonCheckings(Long personId, LocalDateTime from, LocalDateTime to) {
        return repository.findAll().stream()
                .filter(checking -> checking.getPerson().getId().equals(personId)
                        && checking.getActualTime().isAfter(from)
                        && checking.getActualTime().isBefore(to))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a map of person IDs to lists of {@link Checking} records for multiple persons within a given time range.
     *
     * @param personsId List of person IDs.
     * @param from The start of the time range.
     * @param to The end of the time range.
     * @return Map of person IDs to lists of {@link Checking} records.
     */
    @Override
    public Map<Long, List<Checking>> getPersonsCheckings(List<Long> personsId, LocalDateTime from, LocalDateTime to) {
        return repository.findAll().stream()
                .filter(checking -> personsId.contains(checking.getPerson().getId())
                        && checking.getActualTime().isAfter(from)
                        && checking.getActualTime().isBefore(to))
                .collect(Collectors.groupingBy(checking -> checking.getPerson().getId()));
    }

    /**
     * Retrieves a map of dates to maps of person IDs to lists of {@link Checking} records, for multiple persons across specific dates.
     *
     * @param persons Map of person IDs to {@link Person} instances.
     * @param dates Collection of dates.
     * @return Map of dates to maps of person IDs to lists of {@link Checking} records.
     */
    @Override
    public Map<LocalDate, Map<Long, List<Checking>>> getUserCheckingsByDatesAndPersonsMap(Map<Long, Person> persons, Collection<LocalDate> dates) {
        return dates.stream()
                .collect(Collectors.toMap(
                        date -> date,
                        date -> persons.keySet().stream()
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
     * Retrieves a map of person IDs to maps of dates to lists of {@link Checking} records, for multiple persons across specific dates.
     *
     * @param persons Map of person IDs to {@link Person} instances.
     * @param dates Collection of dates.
     * @return Map of person IDs to maps of dates to lists of {@link Checking} records.
     */
    @Override
    public Map<Long, Map<LocalDate, List<Checking>>> getUserCheckingsByPersonsAndDatesMap(Map<Long, Person> persons, Collection<LocalDate> dates) {
        return persons.keySet().stream()
                .collect(Collectors.toMap(
                        personId -> personId,
                        personId -> dates.stream()
                                .collect(Collectors.toMap(
                                        date -> date,
                                        date -> repository.findAll().stream()
                                                .filter(checking -> checking.getPerson().getId().equals(personId)
                                                        && checking.getActualTime().toLocalDate().equals(date))
                                                .collect(Collectors.toList())
                                ))
                ));
    }

    /**
     * Retrieves a list of {@link Checking} records for a given person on a specific date.
     *
     * @param personId The ID of the person.
     * @param date The specific date.
     * @return List of {@link Checking} records.
     */
    @Override
    public List<Checking> getUserCheckings(Long personId, LocalDate date) {
        return repository.findAll().stream()
                .filter(checking -> checking.getPerson().getId().equals(personId)
                        && checking.getActualTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of {@link Checking} records for a given person on a specific date, potentially aggregating or summarizing checkings.
     * This method may be intended for special cases where collective checkings are analyzed.
     *
     * @param personId The ID of the person.
     * @param date The specific date.
     * @return List of {@link Checking} records.
     */
    @Override
    public List<Checking> getCollectiveCheckings(Long personId, LocalDate date) {
        return repository.findAll().stream()
                .filter(checking -> checking.getPerson().getId().equals(personId)
                        && checking.getActualTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of {@link Checking} records for a given person on a specific date, focusing on day-time checkings.
     * This method may be used for analyzing checkings that occur during the day.
     *
     * @param personId The ID of the person.
     * @param date The specific date.
     * @return List of {@link Checking} records.
     */
    @Override
    public List<Checking> getDayCheckings(Long personId, LocalDate date) {
        return repository.findAll().stream()
                .filter(checking -> checking.getPerson().getId().equals(personId)
                        && checking.getActualTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

}