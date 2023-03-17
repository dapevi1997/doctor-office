package co.com.sofka.usecase.cancelcitationevent;

import co.com.sofka.model.patient.events.PersonalDataUpdated;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.week.events.CitationCanceled;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.gateways.EventBus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

public class CancelCitationEventUseCase implements Function<Mono<CitationCanceled>, Flux<DomainEvent>> {
    private DomainEventRepository repository;
    private EventBus bus;
    public CancelCitationEventUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<CitationCanceled> citationCanceledMono) {
        return citationCanceledMono.flatMapIterable(event -> {
                    CitationCanceled citationCanceled = new CitationCanceled("weekId","citaId","true");
                    citationCanceled.setAggregateRootId(event.aggregateRootId());
                    return List.of(citationCanceled);
                }).flatMap(domainEvent -> {
                    return repository.saveEvent(domainEvent);
                })
                .map(domainEvent -> {
                    bus.publish(domainEvent);
                    return domainEvent;
                });
    }
}
