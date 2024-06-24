package com.example.employepoc.query.rest.repository;

import com.example.employepoc.query.rest.dto.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeQueryRepository extends MongoRepository<Employee, Long> {
}