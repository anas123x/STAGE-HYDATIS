package com.example.employepoc.query.rest.repository;

import com.example.employepoc.query.rest.dto.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for person queries.
 * Extends {@link MongoRepository} to provide standard MongoDB operations for {@link Person} entities.
 */
@Repository
public interface PersonQueryRepository extends MongoRepository<Person, String> {
}