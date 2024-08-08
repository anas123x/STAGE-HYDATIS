package com.example.employepoc.query.queries;

import com.hydatis.cqrsref.queries.BaseQuery;
import com.example.employepoc.query.rest.dto.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.joda.time.LocalDate;

import java.util.*;

import com.hydatis.cqrsref.queries.BaseQuery;
import com.example.employepoc.query.rest.dto.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.joda.time.LocalDate;

import java.util.Map;
import java.util.Collection;

/**
 * Represents a query to retrieve checkings for multiple persons across multiple dates, organized by date then person ID.
 * This query extends {@link BaseQuery}, utilizing its basic query functionalities.
 */
@Data
@Getter
@AllArgsConstructor
public class GetUserCheckingsByDatesAndPersonsMapQuery extends BaseQuery {
    private List<String> personsIds;
    private Collection<LocalDate> dates;
}
