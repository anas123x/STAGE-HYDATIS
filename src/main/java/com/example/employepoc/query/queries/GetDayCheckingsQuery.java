package com.example.employepoc.query.queries;

import com.hydatis.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.joda.time.LocalDate;
/**
 *  Represents a query to fetch checkings for a user on a specific date.
 * This class extends {@link BaseQuery} to utilize common query functionalities.
 *
 * @see BaseQuery
 */
@Data
@Getter
@AllArgsConstructor
public class GetDayCheckingsQuery extends BaseQuery {
    private Long personId;
    private LocalDate date;
}
