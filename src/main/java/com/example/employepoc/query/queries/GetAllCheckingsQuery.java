package com.example.employepoc.query.queries;

import com.hydatis.cqrsref.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
/**
 *  Represents a query to fetch all checkings.
 *
 */
@Data
@Getter
@AllArgsConstructor
public class GetAllCheckingsQuery   extends BaseQuery {
}
