package co.com.sofka.usecase.updatepersonaldataevent;

import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.events.PersonalDataUpdated;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.patient.values.*;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.gateways.EventBus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

public class UpdatePersonalDataEventUseCase implements Function<Mono<PersonalDataUpdated>, Flux<DomainEvent>> {
    private DomainEventRepository repository;
    private EventBus bus;
    public UpdatePersonalDataEventUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<PersonalDataUpdated> personalDataUpdatedMono) {
        return personalDataUpdatedMono.flatMapIterable(event -> {
                    PersonalDataUpdated personalData = new PersonalDataUpdated("idPatient","personalData");
                    personalData.setAggregateRootId(event.aggregateRootId());
                    return List.of(personalData);
                }).flatMap(domainEvent -> {
                    return repository.saveEvent(domainEvent);
                })
                .map(domainEvent -> {
                    bus.publish(domainEvent);
                    return domainEvent;
                });
    }
}
