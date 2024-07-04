package com.example.employepoc.query.queries;

import com.hydatis.cqrsref.queries.BaseQuery;
import com.example.employepoc.query.rest.dto.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.joda.time.LocalDate;

import java.util.Map;
import java.util.Collection;

import com.hydatis.cqrsref.queries.BaseQuery;
import com.example.employepoc.query.rest.dto.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.joda.time.LocalDate;

import java.util.Map;
import java.util.Collection;

@Data
@Getter
@AllArgsConstructor
public class GetUserCheckingsByDatesAndPersonsMapQuery extends BaseQuery {
    private Map<Long, Person> persons;
    private Collection<LocalDate> dates;
}
