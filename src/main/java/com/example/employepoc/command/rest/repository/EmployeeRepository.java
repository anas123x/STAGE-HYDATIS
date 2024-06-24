package com.example.employepoc.command.rest.repository;

import com.example.employepoc.command.rest.dto.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}