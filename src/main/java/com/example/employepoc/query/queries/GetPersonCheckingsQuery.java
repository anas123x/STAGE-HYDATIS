package com.example.employepoc.query.queries;

import com.hydatis.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.joda.time.LocalDateTime;

/**
 * Represents a query to fetch checkings for a person over a specific date range.
 *  This class extends {@link BaseQuery} to utilize common query functionalities.
 *
 */
@Data
@Getter
@AllArgsConstructor
public class GetPersonCheckingsQuery extends BaseQuery {
    private String personId;
    private LocalDateTime from;
    private LocalDateTime to;
}
