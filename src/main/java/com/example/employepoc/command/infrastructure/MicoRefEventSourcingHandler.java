package com.example.employepoc.command.infrastructure;

import com.hydatis.cqrsref.domain.AggregateRoot;
import com.hydatis.cqrsref.handlers.EventSourcingHandler;
import com.hydatis.cqrsref.infrastructure.EventStore;
import com.hydatis.cqrsref.producer.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.Comparator;

/**
 * Service implementation of the EventSourcingHandler interface for handling event sourcing operations.
 * This class is responsible for persisting, retrieving, and republishing events related to aggregate roots.
 */
@Service
@ComponentScan({"com.hydatis.cqrsref.producer"})
public class MicoRefEventSourcingHandler implements EventSourcingHandler {

    @Autowired
    private EventStore eventStore; // EventStore instance for event persistence and retrieval

    @Autowired
    private EventProducer eventProducer; // EventProducer instance for publishing events to external systems or queues

    /**
     * Persists uncommitted changes (events) of an aggregate root to the event store and marks them as committed.
     * @param aggregate The aggregate root whose uncommitted changes are to be persisted.
     */
    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    /**
     * Retrieves an aggregate root by its ID, reconstructing its state by replaying its events.
     * @param id The unique identifier of the aggregate root to retrieve.
     * @return The reconstructed aggregate root, or a new instance if no events are found.
     */
    @Override
    public AggregateRoot getById(String id) {
        var aggregate = new AggregateRoot();
        var events = eventStore.getEvents(id);
        if (events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);
            var latestVersion = events.stream().map(x -> x.getVersion()).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }

    /**
     * Republishes all events for all aggregate roots stored in the event store.
     * This can be used to synchronize external systems or rebuild read models.
     */
    @Override
    public void republishEvents() {
        var aggregateIds = eventStore.getAggregateIds();
        for (var aggregateId : aggregateIds) {
            var aggregate = getById(aggregateId);
            if (aggregate == null) continue;
            var events = eventStore.getEvents(aggregateId);
            for (var event : events) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }
}