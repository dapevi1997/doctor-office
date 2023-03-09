package co.com.sofka.usecase.addpatient;


import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.patient.values.ClinicHistory;
import co.com.sofka.model.patient.values.Identity;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import co.com.sofka.usecase.generic.commands.AddPatientCommand;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.gateways.EventBus;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

            ClinicHistory clinicHistory = new ClinicHistory(command.getClinicHistory());
            PatientId patientId = PatientId.of(command.getPatientId());
            Identity identity = new Identity(command.getIdentity());

            Patient patient = new Patient(patientId,identity,clinicHistory);

            return patient.getUncommittedChanges();
        }).flatMap(event -> {
            return repository.saveEvent(event);
        }).map(event ->{
            bus.publish(event);
            return event;
        });
    }
}
