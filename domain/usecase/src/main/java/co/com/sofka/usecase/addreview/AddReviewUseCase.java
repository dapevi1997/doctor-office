package co.com.sofka.usecase.addreview;

import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.patient.values.Annotation;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.model.patient.values.ReviewId;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import co.com.sofka.usecase.generic.commands.AddReviewCommand;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.gateways.EventBus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class AddReviewUseCase extends UseCaseForCommand<AddReviewCommand> {
    private final DomainEventRepository repository;
    private final EventBus bus;

    public AddReviewUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<AddReviewCommand> addReviewCommandMono) {
        return addReviewCommandMono.flatMapMany(command->
            repository.findById(command.getPatientId())
                    .collectList()
                    .flatMapIterable(events -> {
                        Patient patient = Patient.from(PatientId.of(command.getPatientId()), events);

                        patient.addReview(PatientId.of(command.getPatientId()), ReviewId.of(command.getReviewId()),
                                new Annotation(command.getAnnotation()));

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
