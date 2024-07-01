package com.example.employepoc.command.rest.repository;

import com.example.employepoc.command.rest.dto.Checking;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckingCommandRepository extends JpaRepository<Checking, String> {


    @Query("SELECT c FROM Checking c WHERE c.person.id = :personId AND c.actualTime = :actualTime AND c.direction = :direction AND c.actualSource = :actualSource")
    List<Checking> findByPersonIdAndActualTimeAndDirectionAndActualSource(
            @Param("personId") Long personId,
            @Param("actualTime") LocalDateTime actualTime,
            @Param("direction") Checking.CheckingDirection direction,
            @Param("actualSource") Checking.CheckingSource actualSource
    );}
