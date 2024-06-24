package com.example.employepoc.query.rest.services;

import com.example.employepoc.query.rest.dto.Employee;
import com.example.employepoc.query.rest.repository.EmployeeQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeQueryService {

    private final EmployeeQueryRepository employeeQueryRepository;

    @Autowired
    public EmployeeQueryService(EmployeeQueryRepository employeeQueryRepository) {
        this.employeeQueryRepository = employeeQueryRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeQueryRepository.findAll();
    }
    public Employee getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeQueryRepository.findById(id);
        return optionalEmployee.orElse(null);
    }
}