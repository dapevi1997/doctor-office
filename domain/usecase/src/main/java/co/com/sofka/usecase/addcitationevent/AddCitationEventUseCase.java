package co.com.sofka.usecase.addcitationevent;

import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.week.events.AvailabilityUpdated;
import co.com.sofka.model.week.events.CitationAdded;
import co.com.sofka.model.week.events.CitationCanceled;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.gateways.EventBus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

public class AddCitationEventUseCase implements Function<Mono<CitationAdded>, Flux<DomainEvent>> {
    private DomainEventRepository repository;
    private EventBus bus;
    public AddCitationEventUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<CitationAdded> citationAddedMono) {
        return citationAddedMono.flatMapIterable(event -> {
                    CitationAdded citationAdded = new CitationAdded("citaId","information",
                            "true","idPatient","idWeek");
                    citationAdded.setAggregateRootId(event.aggregateRootId());
                    AvailabilityUpdated availabilityUpdated = new AvailabilityUpdated("idWeek","availability");
                    availabilityUpdated.setAggregateRootId(event.aggregateRootId());
                    return List.of(citationAdded);
                }).flatMap(domainEvent -> {
                    return repository.saveEvent(domainEvent);
                })
                .map(domainEvent -> {
                    bus.publish(domainEvent);
                    return domainEvent;
                });
    }
}
