package co.com.sofka.usecase.addpatient;


import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.entities.Review;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.patient.values.*;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import co.com.sofka.usecase.generic.commands.AddPatientCommand;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.gateways.EventBus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

//@RequiredArgsConstructor
public class AddPatientUseCase extends UseCaseForCommand<AddPatientCommand> {
    private final DomainEventRepository repository;
    private final EventBus bus;

    public AddPatientUseCase(DomainEventRepository repository, EventBus bus){
        this.repository = repository;
        this.bus = bus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<AddPatientCommand> addPatientCommandMono) {
        return addPatientCommandMono.flatMapIterable(command->{
            PatientId patientId = PatientId.of(command.getPatientId().value());
            ReviewId reviewId = ReviewId.of(command.getReviewId().value());
            Annotation annotation = new Annotation(command.getAnnotation().value());
            ClinicHistory clinicHistory = new ClinicHistory(command.getClinicHistory().value());
            PersonalData personalData = new PersonalData(command.getPersonalData().value());



            Patient patient = new Patient(patientId,reviewId,annotation,personalData,clinicHistory);


            return patient.getUncommittedChanges();
        }).flatMap(event -> {
            return repository.saveEvent(event);
        }).map(event ->{
            bus.publish(event);
            return event;
        });
    }
}
