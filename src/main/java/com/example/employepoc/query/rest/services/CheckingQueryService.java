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

@Service
public class CheckingQueryService implements ICheckingQueryService {
    private final CheckingQueryRepository repository;


    @Autowired
    public CheckingQueryService(CheckingQueryRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<Checking> getPersonCheckings(Long personId, LocalDateTime from, LocalDateTime to) {
        return repository.findAll().stream()
                .filter(checking -> checking.getPerson().getId().equals(personId)
                        && checking.getActualTime().isAfter(from)
                        && checking.getActualTime().isBefore(to))
                .collect(Collectors.toList());
    }


    @Override
    public Map<Long, List<Checking>> getPersonsCheckings(List<Long> personsId, LocalDateTime from, LocalDateTime to) {
        return repository.findAll().stream()
                .filter(checking -> personsId.contains(checking.getPerson().getId())
                        && checking.getActualTime().isAfter(from)
                        && checking.getActualTime().isBefore(to))
                .collect(Collectors.groupingBy(checking -> checking.getPerson().getId()));
    }


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

    @Override
    public List<Checking> getUserCheckings(Long personId, LocalDate date) {
        return repository.findAll().stream()
                .filter(checking -> checking.getPerson().getId().equals(personId)
                        && checking.getActualTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }


    @Override
    public List<Checking> getCollectiveCheckings(Long personId, LocalDate date) {
        return repository.findAll().stream()
                .filter(checking -> checking.getPerson().getId().equals(personId)
                        && checking.getActualTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }


    @Override
    public List<Checking> getDayCheckings(Long personId, LocalDate date) {
        return repository.findAll().stream()
                .filter(checking -> checking.getPerson().getId().equals(personId)
                        && checking.getActualTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

}
