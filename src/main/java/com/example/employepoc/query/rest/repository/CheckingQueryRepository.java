package com.example.employepoc.query.rest.repository;

import com.example.employepoc.query.rest.dto.Checking;

import org.joda.time.LocalDateTime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckingQueryRepository  extends MongoRepository<Checking, String> {

    @Query("{ 'person.id' : ?0, 'actualTime' : ?1, 'direction' : ?2, 'actualSource' : ?3 }")
    List<Checking> findByPersonIdAndActualTimeAndDirectionAndActualSource(Long id, LocalDateTime actualTime, Checking.CheckingDirection direction, Checking.CheckingSource actualSource);}
