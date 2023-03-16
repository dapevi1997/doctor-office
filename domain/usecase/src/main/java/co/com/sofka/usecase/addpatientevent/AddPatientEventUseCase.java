package co.com.sofka.usecase.addpatientevent;

import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.events.PatientAdded;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.patient.values.*;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.gateways.EventBus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

public class AddPatientEventUseCase implements Function<Mono<PatientAdded>, Flux<DomainEvent>> {
    private DomainEventRepository repository;
    private EventBus bus;
    public AddPatientEventUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<PatientAdded> patientAddedMono) {
        return patientAddedMono.flatMapIterable(event -> {
                    PatientId patientId = PatientId.of(event.aggregateRootId());
                    ReviewId reviewId = ReviewId.of(event.getReviewId());
                    Annotation annotation = new Annotation(event.getAnnotation());
                    ClinicHistory clinicHistory = new ClinicHistory(event.getClinicHistory());
                    PersonalData personalData = new PersonalData(event.getIdentity());
                    Available available = new Available(Boolean.valueOf(event.getAvailable()));

                    Patient patient = new Patient(patientId,reviewId,annotation,personalData,clinicHistory, available);
                    return patient.getUncommittedChanges();
                }).flatMap(domainEvent -> {
                    return repository.saveEvent(domainEvent);
                })
                .map(domainEvent -> {
                    bus.publish(domainEvent);
                    return domainEvent;
                });
    }


}
