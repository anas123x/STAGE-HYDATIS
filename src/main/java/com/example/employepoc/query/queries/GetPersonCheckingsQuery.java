package com.example.employepoc.query.queries;

import com.hydatis.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.joda.time.LocalDateTime;

@Data
@Getter
@AllArgsConstructor
public class GetPersonCheckingsQuery extends BaseQuery {
    private Long personId;
    private LocalDateTime from;
    private LocalDateTime to;
}
