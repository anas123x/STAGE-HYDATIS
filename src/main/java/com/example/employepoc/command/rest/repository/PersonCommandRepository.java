package com.example.employepoc.command.rest.repository;

import com.example.employepoc.command.rest.dto.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * PersonCommandRepository interface for accessing and manipulating person data in the database.
 * Extends JpaRepository to leverage Spring Data JPA's repository capabilities.
 * This interface is used to perform CRUD operations on {@link Person} entities.
 */
@Repository
public interface PersonCommandRepository extends JpaRepository<Person, Long> {
}