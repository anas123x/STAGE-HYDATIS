package com.example.employepoc.command.rest.repository;

import com.example.employepoc.command.rest.dto.Checking;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for executing operations on the Checking entity within the database.
 * It extends JpaRepository to provide standard CRUD operations and includes custom query methods.
 */
@Repository
public interface CheckingCommandRepository extends JpaRepository<Checking, String> {

    /**
     * Custom query to find a list of Checking entities based on person ID, actual time, direction, and actual source.
     * This method allows for precise retrieval of checkings matching specific criteria.
     *
     * @param personId The ID of the person associated with the checking.
     * @param actualTime The actual time of the checking.
     * @param direction The direction of the checking (e.g., IN, OUT).
     * @param actualSource The source of the checking.
     * @return A list of Checking entities matching the specified criteria.
     */
    @Query("SELECT c FROM Checking c WHERE c.person.id = :personId AND c.actualTime = :actualTime AND c.direction = :direction AND c.actualSource = :actualSource")
    List<Checking> findByPersonIdAndActualTimeAndDirectionAndActualSource(
            @Param("personId") Long personId,
            @Param("actualTime") LocalDateTime actualTime,
            @Param("direction") Checking.CheckingDirection direction,
            @Param("actualSource") Checking.CheckingSource actualSource
    );
}