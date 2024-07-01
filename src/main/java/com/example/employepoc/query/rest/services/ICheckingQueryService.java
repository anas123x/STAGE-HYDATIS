package com.example.employepoc.query.rest.services;

import com.example.employepoc.query.rest.dto.Checking;
import com.example.employepoc.query.rest.dto.Person;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ICheckingQueryService {
    Collection<Checking> getPersonCheckings(Long personId, LocalDateTime from, LocalDateTime to);

    Map<Long, List<Checking>> getPersonsCheckings(List<Long> personsId, LocalDateTime from, LocalDateTime to);
    Map<LocalDate, Map<Long, List<Checking>>> getUserCheckingsByDatesAndPersonsMap(Map<Long, Person> persons,
                                                                                   Collection<LocalDate> dates);

    Map<Long, Map<LocalDate, List<Checking>>> getUserCheckingsByPersonsAndDatesMap(Map<Long, Person> persons,
                                                                                   Collection<LocalDate> dates);

    List<Checking> getUserCheckings(Long personId, LocalDate date);

    List<Checking> getCollectiveCheckings(Long personId, LocalDate date);

    List<Checking> getDayCheckings(Long personId, LocalDate date);
}
