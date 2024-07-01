package com.example.employepoc.query.queries;

import com.hydatis.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.joda.time.LocalDate;

@Data
@Getter
@AllArgsConstructor
public class GetCollectiveCheckingsQuery extends BaseQuery {
    private Long personId;
    private LocalDate date;
}
