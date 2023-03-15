package co.com.sofka.usecase.generic.gateways;

import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.generic.DomainEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DomainEventRepository {
    Flux<DomainEvent> findById(String aggregateId);
    Mono<DomainEvent> saveEvent(DomainEvent event);

    Mono<Boolean> exists(String aggregateId);

    Flux<DomainEvent> findCitation(String weekId);

    Flux<DomainEvent> findClinicHistory(String weekId);

    Flux<DomainEvent> findPatientbyId(String patientId);


    //Mono<Boolean> exist(String aggregateId);
}
