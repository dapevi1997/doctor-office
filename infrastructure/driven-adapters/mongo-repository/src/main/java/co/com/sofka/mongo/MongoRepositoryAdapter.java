package co.com.sofka.mongo;

import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.mongo.data.StoredEvent;
import co.com.sofka.serializer.JSONMapper;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class MongoRepositoryAdapter implements DomainEventRepository
{
    private final ReactiveMongoTemplate template;
    private final JSONMapper eventSerializer;
    private Object Boolean;

    public MongoRepositoryAdapter(ReactiveMongoTemplate template, JSONMapper eventSerializer) {
        this.template = template;
        this.eventSerializer = eventSerializer;
    }

    @Override
    public Flux<DomainEvent> findById(String aggregateId) {
        var query = new Query(Criteria.where("aggregateRootId").is(aggregateId));
        return template.find(query, StoredEvent.class)
                .sort(Comparator.comparing(event -> event.getOccurredOn()))
                .map(storeEvent -> storeEvent.deserializeEvent(eventSerializer));
    }

    @Override
    public Mono<DomainEvent> saveEvent(DomainEvent event) {
        StoredEvent eventStored = new StoredEvent();
        eventStored.setAggregateRootId(event.aggregateRootId());
        eventStored.setTypeName(event.getClass().getTypeName());
        eventStored.setOccurredOn(new Date());
        eventStored.setEventBody(StoredEvent.wrapEvent(event, eventSerializer));
        return template.save(eventStored)
                .map(storeEvent -> storeEvent.deserializeEvent(eventSerializer));
    }

    @Override
    public Mono<java.lang.Boolean> exists(String aggregateId) {
        var query = new Query(Criteria.where("aggregateRootId").is(aggregateId));
        return template.exists(query, StoredEvent.class);
    }

    /*    @Override
    public Mono<Boolean> exist(String aggregateId) {
        AtomicReference<Boolean> flag;
        var query = new Query(Criteria.where("aggregateRootId").is(aggregateId).and("typeName").is("co.com.sofka.model.patient.events.PatientAdded"));
        return template.findOne(query, StoredEvent.class)
                .map(storeEvent -> true)
                .switchIfEmpty(Mono.just(false));


    }*/
}
