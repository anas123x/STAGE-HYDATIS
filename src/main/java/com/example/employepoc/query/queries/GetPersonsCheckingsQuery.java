package com.example.employepoc.query.queries;

import com.hydatis.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.joda.time.LocalDateTime;

import java.util.Collection;
import java.util.List;
package com.example.employepoc.query.queries;

import com.hydatis.cqrsref.queries.BaseQuery;
import com.example.employepoc.query.rest.dto.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.joda.time.LocalDate;

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
    private Map<Long, Person> persons;
    private Collection<LocalDate> dates;
}
@Data
@Getter
@AllArgsConstructor
public class GetPersonsCheckingsQuery extends BaseQuery {
    private List<Long> personsId;
    private LocalDateTime from;
    private LocalDateTime to;
}
