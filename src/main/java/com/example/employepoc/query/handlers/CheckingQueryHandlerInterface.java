package com.example.employepoc.query.handlers;

import com.example.employepoc.query.queries.*;
import com.example.employepoc.query.rest.dto.Checking;
import org.joda.time.LocalDate;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CheckingQueryHandlerInterface {
    Collection<Checking> handle(GetPersonCheckingsQuery query);
    Map<Long, List<Checking>> handle(GetPersonsCheckingsQuery query);
    Map<LocalDate, Map<Long, List<Checking>>> handle(GetUserCheckingsByDatesAndPersonsMapQuery query);
    Map<Long, Map<LocalDate, List<Checking>>> handle(GetUserCheckingsByPersonsAndDatesMapQuery query);
    List<Checking> handle(GetUserCheckingsQuery query);
    List<Checking> handle(GetCollectiveCheckingsQuery query);
    List<Checking> handle(GetDayCheckingsQuery query);
}
