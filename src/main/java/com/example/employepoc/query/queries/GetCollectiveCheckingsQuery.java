package com.example.employepoc.query.queries;

import com.hydatis.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.joda.time.LocalDate;

/**
 *  Represents a query to fetch checkings for a user on a specific date.
 *
 */
@Data
@Getter
@AllArgsConstructor
public class GetCollectiveCheckingsQuery extends BaseQuery {
    private String personId;
    private LocalDate date;
}
