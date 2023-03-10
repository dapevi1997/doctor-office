package co.com.sofka.usecase.updatepersonaldata;

import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.patient.values.Annotation;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.model.patient.values.PersonalData;
import co.com.sofka.model.patient.values.ReviewId;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import co.com.sofka.usecase.generic.commands.UpdatePersonalDataCommand;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.gateways.EventBus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class UpdatePersonalDataUseCase extends UseCaseForCommand<UpdatePersonalDataCommand> {
    private final DomainEventRepository repository;
    private final EventBus bus;

    public UpdatePersonalDataUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<UpdatePersonalDataCommand> updatePersonalDataCommandMono) {
        return updatePersonalDataCommandMono.flatMapMany(command->
                repository.findById(command.getIdPatient())
                        .collectList()
                        .flatMapIterable(events -> {
                            Patient patient = Patient.from(PatientId.of(command.getIdPatient()), events);

                            patient.updatePersonalData(PatientId.of(command.getIdPatient()),new PersonalData(command.getPersonalData()) );

                            return patient.getUncommittedChanges();
                        }).map(
                                event->{
                                    bus.publish(event);
                                    return event;
                                }
                        ).flatMap(event->{
                            return repository.saveEvent(event);
                        })
        );
    }
}
