package co.com.sofka.usecase.addweekevent;

import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.patient.values.*;
import co.com.sofka.model.week.Week;
import co.com.sofka.model.week.events.WeekAdded;
import co.com.sofka.model.week.values.*;
import co.com.sofka.usecase.addweek.MapperUtils;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.gateways.EventBus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class AddWeekEventUseCase implements Function<Mono<WeekAdded>, Flux<DomainEvent>> {
    private DomainEventRepository repository;
    private EventBus bus;

    private MapperUtils mapperUtils;
    public AddWeekEventUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
        this.mapperUtils = new MapperUtils();
    }

    @Override
    public Flux<DomainEvent> apply(Mono<WeekAdded> weekAddedMono) {
        return weekAddedMono.flatMapIterable(event -> {
                    Availability availability = mapperUtils.mapperToStringToAvailability().apply(event.getAvailability());

                    Week week = new Week(WeekId.of(event.aggregateRootId()), CitationId.of(event.getId()), new Infomation(event.getInformation()),
                            new CitationState(event.getCitationState()), PatientId.of(event.getPatientId()),
                            availability,new Date(event.getDate()));

                    return week.getUncommittedChanges();
                }).flatMap(domainEvent -> {
                    return repository.saveEvent(domainEvent);
                })
                .map(domainEvent -> {
                    bus.publish(domainEvent);
                    return domainEvent;
                });
    }
}
