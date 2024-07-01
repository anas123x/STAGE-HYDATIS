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
 * This class that provides implementation of the EventStore interface for storing events.
 */

@Service
@EnableMongoRepositories(basePackages = "com.example.employepoc.command.events")
@ComponentScan({"com.hydatis.cqrsref.producer", "com.example.employepoc"})
public class MicroRefEventStore implements EventStore {

    @Autowired
    private EventStoreRepository eventStoreRepository;
    @Autowired
    private EventProducer eventProducer;

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
