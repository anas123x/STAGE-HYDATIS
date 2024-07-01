package com.example.employepoc.query.queries;

import com.hydatis.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.joda.time.LocalDateTime;

import java.util.Collection;
import java.util.List;

@Data
@Getter
@AllArgsConstructor
public class GetPersonsCheckingsQuery extends BaseQuery {
    private List<Long> personsId;
    private LocalDateTime from;
    private LocalDateTime to;
}
