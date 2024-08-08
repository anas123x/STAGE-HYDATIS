package com.example.employepoc.query.queries;

import com.hydatis.cqrsref.queries.BaseQuery;
import com.example.employepoc.query.rest.dto.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * Represents a query to fetch checkings for multiple persons over a collection of dates.
 * This class extends {@link BaseQuery} to utilize common query functionalities.
 *
    * @see BaseQuery
    */
@Data
@Getter
@AllArgsConstructor
public class GetUserCheckingsByPersonsAndDatesMapQuery extends BaseQuery {
    private List<String> personsIds;
    private Collection<LocalDate> dates;
}