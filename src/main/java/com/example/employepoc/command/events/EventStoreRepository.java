package com.example.employepoc.command.events;

import com.hydatis.cqrsref.events.EventModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Interface for the repository handling the persistence of event models into MongoDB.
 * Utilizes Spring Data MongoDB's repository abstraction for CRUD operations and custom query methods.
 */
@Qualifier
public interface EventStoreRepository extends MongoRepository<EventModel, String> {
    /**
     * Retrieves a list of EventModel instances by their aggregate identifier.
     * This method supports the retrieval of all events associated with a specific aggregate.
     *
     * @param aggregateIdentifier The unique identifier of the aggregate.
     * @return List of EventModel instances associated with the provided aggregate identifier.
     */
    List<EventModel> findByAggregateIdentifier(String aggregateIdentifier);
}