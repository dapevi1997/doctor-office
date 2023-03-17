package co.com.sofka.usecase.addreviewevent;

import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.events.ReviewAdded;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.patient.values.*;
import co.com.sofka.model.week.Week;
import co.com.sofka.model.week.values.*;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.gateways.EventBus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

public class AddReviewEventUseCase implements Function<Mono<ReviewAdded>, Flux<DomainEvent>> {
    private DomainEventRepository repository;
    private EventBus bus;
    public AddReviewEventUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<ReviewAdded> reviewAddedMono) {

        return reviewAddedMono.flatMapIterable(event -> {
                    final String AGGREGATE_ID = "test-addpatient-id";
                    ReviewAdded reviewAdded = new ReviewAdded("patientId","idReview","anotation");
                    event.setAggregateRootId(AGGREGATE_ID);



                    return List.of(reviewAdded);
                }).flatMap(domainEvent -> {
                    return repository.saveEvent(domainEvent);
                })
                .map(domainEvent -> {
                    bus.publish(domainEvent);
                    return domainEvent;
                });

/*        return reviewAddedMono.flatMapMany(event->
                repository.findById(event.getPatientId())
                        .collectList()
                        .flatMapIterable(events -> {
                            PatientId patientId = PatientId.of(event.aggregateRootId());
                            ReviewId reviewId = ReviewId.of(event.getIdReview());
                            Annotation annotation = new Annotation(event.getAnnotation());
                            ClinicHistory clinicHistory = new ClinicHistory("clinic history");
                            PersonalData personalData = new PersonalData("personal data");
                            Available available = new Available(Boolean.valueOf("true"));

                            Patient patient = new Patient(patientId,reviewId,annotation,personalData,clinicHistory, available);

                            patient.addReview(PatientId.of(event.getPatientId()), ReviewId.of(event.getIdReview()),
                                    new Annotation(event.getAnnotation()));

                            return patient.getUncommittedChanges();
                        }).map(
                                event1->{
                                    bus.publish(event1);
                                    return event1;
                                }
                        ).flatMap(event1->{
                            return repository.saveEvent(event1);
                        })
        );*/
    }
}
