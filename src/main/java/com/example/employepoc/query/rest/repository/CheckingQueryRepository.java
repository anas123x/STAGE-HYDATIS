package com.example.employepoc.query.rest.repository;

import com.example.employepoc.query.rest.dto.Checking;
import org.joda.time.LocalDateTime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for checking queries.
 * Extends {@link MongoRepository} to provide standard MongoDB operations for {@link Checking} entities.
 * Utilizes custom query methods to fetch {@link Checking} records based on various criteria.
 */
@Repository
public interface CheckingQueryRepository extends MongoRepository<Checking, String> {

    /**
     * Finds a list of {@link Checking} records based on person ID, actual time, direction, and actual source.
     *
     * @param id The ID of the person to match.
     * @param actualTime The actual time of the checking to match.
     * @param direction The direction of the checking to match.
     * @param actualSource The source of the checking to match.
     * @return A list of {@link Checking} records matching the specified criteria.
     */
    @Query("{ 'person.id' : ?0, 'actualTime' : ?1, 'direction' : ?2, 'actualSource' : ?3 }")
    List<Checking> findByPersonIdAndActualTimeAndDirectionAndActualSource(Long id, LocalDateTime actualTime, Checking.CheckingDirection direction, Checking.CheckingSource actualSource);
}