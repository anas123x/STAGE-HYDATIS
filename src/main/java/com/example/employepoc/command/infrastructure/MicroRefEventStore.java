package com.example.employepoc.command.infrastructure;



import com.hydatis.cqrsref.domain.AggregateRoot;
import com.hydatis.cqrsref.events.BaseEvent;
import com.hydatis.cqrsref.events.EventModel;
import com.hydatis.cqrsref.exceptions.ConcurrencyException;
import com.hydatis.cqrsref.infrastructure.EventStore;
import com.hydatis.cqrsref.producer.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;
import com.example.employepoc.command.events.EventStoreRepository;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the EventStore interface, providing mechanisms for storing and retrieving events
 * from a MongoDB database. This class interacts with the EventStoreRepository to persist event data
 * and utilizes an EventProducer for publishing events after they are stored.
 */
@Service
@EnableMongoRepositories(basePackages = "com.example.employepoc.command.events")
@ComponentScan({"com.hydatis.cqrsref.producer", "com.example.employepoc"})
public class MicroRefEventStore implements EventStore {

    @Autowired
    private EventStoreRepository eventStoreRepository;
    @Autowired
    private EventProducer eventProducer;

    /**
     * Saves a series of events for a given aggregate with an expected version.
     * If the current version does not match the expected version, a ConcurrencyException is thrown.
     * Each event is saved with an incremented version number and published using the event producer.
     *
     * @param aggregateId The identifier of the aggregate root associated with the events.
     * @param events An iterable collection of events to be saved.
     * @param expectedVersion The expected current version of the aggregate root.
     * @throws ConcurrencyException if the expected version does not match the current version.
     */
    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }
        var version = expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .id(UUID.randomUUID().toString()) // generate a unique ID for each event
                    .timeStamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AggregateRoot.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();

            var persistedEvent = eventStoreRepository.save(eventModel);
            if (persistedEvent.getId() != null) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
                System.out.println("Event produced: " + event.getClass().getSimpleName()+event.toString());
            }
        }
    }
    /**
     * Retrieves all events for a given aggregate identifier.
     * The events are converted from EventModel to BaseEvent before being returned.
     *
     * @param aggregateId The identifier of the aggregate root for which events are to be retrieved.
     * @return A list of BaseEvent instances associated with the aggregate identifier.
     */

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        return eventStream.stream().map(EventModel::getEventData).collect(Collectors.toList());
    }

    @Override
    public List<String> getAggregateIds() {
        var eventStream = eventStoreRepository.findAll();
        return eventStream.stream().map(EventModel::getAggregateIdentifier).distinct().collect(Collectors.toList());
    }
}
