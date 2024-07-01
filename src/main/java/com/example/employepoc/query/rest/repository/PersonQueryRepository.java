package com.example.employepoc.query.rest.repository;

import com.example.employepoc.query.rest.dto.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonQueryRepository extends MongoRepository<Person, Long> {
}
