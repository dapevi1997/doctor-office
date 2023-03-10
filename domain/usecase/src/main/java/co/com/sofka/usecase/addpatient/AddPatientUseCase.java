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

            ClinicHistory clinicHistory = new ClinicHistory(command.getClinicHistory().value());
            PatientId patientId = PatientId.of(command.getPatientId().value());
            PersonalData personalData = new PersonalData(command.getPersonalData().value());




            Annotation annotation = new Annotation("anotation");

            Review review = new Review(ReviewId.of("idreviw"),PatientId.of("patienId"),annotation);

            Set<Review> reviews = new HashSet<>();
            reviews.add(review);

            Patient patient = new Patient(patientId, reviews ,personalData,clinicHistory);
            repository.savePatient(patient);

            return patient.getUncommittedChanges();
        }).flatMap(event -> {
            return repository.saveEvent(event);
        }).map(event ->{
            bus.publish(event);
            return event;
        });
    }
}
