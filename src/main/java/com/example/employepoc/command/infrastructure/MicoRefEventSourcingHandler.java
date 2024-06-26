package com.example.employepoc.command.infrastructure;


import com.hydatis.cqrsref.domain.AggregateRoot;
import com.hydatis.cqrsref.handlers.EventSourcingHandler;
import com.hydatis.cqrsref.infrastructure.EventStore;
import com.hydatis.cqrsref.producer.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@ComponentScan({"com.hydatis.cqrsref.producer"})
public class MicoRefEventSourcingHandler implements EventSourcingHandler {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private EventProducer eventProducer;


    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

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
