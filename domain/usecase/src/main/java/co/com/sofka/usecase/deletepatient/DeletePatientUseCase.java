package co.com.sofka.usecase.deletepatient;

import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.patient.values.Available;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.model.patient.values.PersonalData;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import co.com.sofka.usecase.generic.commands.DeletePatientCommand;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.gateways.EventBus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DeletePatientUseCase extends UseCaseForCommand<DeletePatientCommand> {
    private final DomainEventRepository repository;
    private final EventBus bus;

    public DeletePatientUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<DeletePatientCommand> deletePatientCommandMono) {
        return deletePatientCommandMono.flatMapMany(
                deletePatientCommand -> {
                   return repository.findById(deletePatientCommand.getIdPatient())
                           .collectList()
                           .flatMapIterable(events -> {
                               Patient patient = Patient.from(PatientId.of(deletePatientCommand.getIdPatient()), events);

                               patient.updateAvailable(PatientId.of(deletePatientCommand.getIdPatient()),new Available(Boolean.valueOf(deletePatientCommand.getAvailable())) );

                               return patient.getUncommittedChanges();
                           }).map(
                                   event->{
                                       bus.publish(event);
                                       return event;
                                   }
                           ).flatMap(event->{
                               return repository.saveEvent(event);
                           });

                }
        );
    }
}
