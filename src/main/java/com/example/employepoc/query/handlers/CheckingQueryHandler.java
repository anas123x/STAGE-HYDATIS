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

@Service
@Slf4j
public class CheckingQueryHandler implements CheckingQueryHandlerInterface {

    private final CheckingQueryService checkingQueryService;

    @Autowired
    public CheckingQueryHandler(CheckingQueryService checkingQueryService) {
        this.checkingQueryService = checkingQueryService;
    }

    @Override
    public List<Checking> handle(GetPersonCheckingsQuery query) {
        log.info("Handling GetPersonCheckingsQuery: {}", query);
        return checkingQueryService.getPersonCheckings(query.getPersonId(), query.getFrom(), query.getTo());
    }

    @Override
    public Map<Long, List<Checking>> handle(GetPersonsCheckingsQuery query) {
        log.info("Handling GetPersonsCheckingsQuery: {}", query);
        return checkingQueryService.getPersonsCheckings(query.getPersonsId(), query.getFrom(), query.getTo());
    }

    @Override
    public Map<LocalDate, Map<Long, List<Checking>>> handle(GetUserCheckingsByDatesAndPersonsMapQuery query) {
        log.info("Handling GetUserCheckingsByDatesAndPersonsMapQuery: {}", query);
        return checkingQueryService.getUserCheckingsByDatesAndPersonsMap(query.getPersons(), query.getDates());
    }

    @Override
    public Map<Long, Map<LocalDate, List<Checking>>> handle(GetUserCheckingsByPersonsAndDatesMapQuery query) {
        log.info("Handling GetUserCheckingsByPersonsAndDatesMapQuery: {}", query);
        return checkingQueryService.getUserCheckingsByPersonsAndDatesMap(query.getPersons(), query.getDates());
    }

    @Override
    public List<Checking> handle(GetUserCheckingsQuery query) {
        log.info("Handling GetUserCheckingsQuery: {}", query);
        return checkingQueryService.getUserCheckings(query.getPersonId(), query.getDate());
    }

    @Override
    public List<Checking> handle(GetCollectiveCheckingsQuery query) {
        log.info("Handling GetCollectiveCheckingsQuery: {}", query);
        return checkingQueryService.getCollectiveCheckings(query.getPersonId(), query.getDate());
    }

    @Override
    public List<Checking> handle(GetDayCheckingsQuery query) {
        log.info("Handling GetDayCheckingsQuery: {}", query);
        return checkingQueryService.getDayCheckings(query.getPersonId(), query.getDate());
    }
}
