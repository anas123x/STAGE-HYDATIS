package com.example.employepoc.query.handlers;

import com.example.employepoc.query.rest.dto.Employee;
import com.example.employepoc.query.queries.FindAllEmployeesQuery;
import com.example.employepoc.query.queries.FindEmployeeByIdQuery;

import java.util.List;

public interface EmployeeQueryHandlerInterface {
    List<Employee> handle(FindAllEmployeesQuery query);
    Employee handle(FindEmployeeByIdQuery query);
}
