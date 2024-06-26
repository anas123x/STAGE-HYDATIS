package com.example.employepoc.query.rest.repository;

import com.example.employepoc.query.rest.dto.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeQueryRepository extends MongoRepository<Employee, String> {
}