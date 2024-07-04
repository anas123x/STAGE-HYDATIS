package com.example.employepoc.query.queries;

import com.hydatis.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.joda.time.LocalDate;

/**
 * Represents a query to fetch checkings for a user on a specific date.
 * This class extends {@link BaseQuery} to utilize common query functionalities.
 */
@Data
@Getter
@AllArgsConstructor
public class GetUserCheckingsQuery extends BaseQuery {
    private Long personId; // The ID of the person whose checkings are to be fetched
    private LocalDate date; // The specific date for which to fetch the checkings
}