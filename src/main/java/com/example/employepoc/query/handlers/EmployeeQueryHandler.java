package com.example.employepoc.query.handlers;

import com.example.employepoc.query.rest.dto.Employee;
import com.example.employepoc.query.queries.FindAllEmployeesQuery;
import com.example.employepoc.query.queries.FindEmployeeByIdQuery;
import com.example.employepoc.query.rest.repository.EmployeeQueryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@EnableMongoRepositories(basePackages = "com.example.employepoc.query.rest.repository")
public class EmployeeQueryHandler implements  EmployeeQueryHandlerInterface {
    private final EmployeeQueryRepository employeeQueryRepository;
    @Autowired
    public EmployeeQueryHandler(EmployeeQueryRepository employeeQueryRepository) {
        this.employeeQueryRepository = employeeQueryRepository;
    }

    @Override
    public List<Employee> handle(FindAllEmployeesQuery query) {
        return employeeQueryRepository.findAll();
    }

    @Override
    public Employee handle(FindEmployeeByIdQuery query) {
        return employeeQueryRepository.findById((query.getId())).orElseThrow(() -> new RuntimeException("Employee not found"));
    }
}
