package com.example.employepoc.query.queries;

import com.hydatis.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class FindEmployeeByIdQuery extends BaseQuery {
    private String id;
}
