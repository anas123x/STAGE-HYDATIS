package com.example.employepoc.command.rest.repository;

import com.example.employepoc.command.rest.dto.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonCommandRepository extends JpaRepository<Person, Long> {
}
