package co.com.sofka.usecase.deletepatientevent;

import co.com.sofka.model.patient.events.PatientDeleted;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.week.events.CitationCanceled;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.gateways.EventBus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

public class DeletePatientEventUseCase implements Function<Mono<PatientDeleted>, Flux<DomainEvent>> {
    private DomainEventRepository repository;
    private EventBus bus;
    public DeletePatientEventUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<PatientDeleted> patientDeletedMono) {
        return patientDeletedMono.flatMapIterable(event -> {
                    PatientDeleted patientDeleted = new PatientDeleted("idPatient","false");
                    patientDeleted.setAggregateRootId(event.aggregateRootId());
                    return List.of(patientDeleted);
                }).flatMap(domainEvent -> {
                    return repository.saveEvent(domainEvent);
                })
                .map(domainEvent -> {
                    bus.publish(domainEvent);
                    return domainEvent;
                });
    }
}
