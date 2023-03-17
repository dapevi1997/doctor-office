package co.com.sofka.usecase.addreviewevent;

import co.com.sofka.model.patient.events.ReviewAdded;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.week.events.WeekAdded;
import co.com.sofka.usecase.addweekevent.AddWeekEventUseCase;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.gateways.EventBus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class AddReviewEventUseCaseTest {
    @Mock
    private DomainEventRepository repository;

    @Mock
    private EventBus bus;

    private AddReviewEventUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new AddReviewEventUseCase(repository,bus);
    }

    @Test
    void apply() {
        final String AGGREGATE_ID = "test-addpatient-id";
        ReviewAdded event = new ReviewAdded("patientId","idReview","anotation");
        event.setAggregateRootId(AGGREGATE_ID);


        Mockito.when(repository.saveEvent(ArgumentMatchers.any(ReviewAdded.class))).thenAnswer(
                invocation -> Mono.just(invocation.getArgument(0))
        );

        //Act
        Flux<DomainEvent> result = useCase.apply(Mono.just(event));

        StepVerifier.create(result)
                .expectNextMatches(event1 -> {
                    ReviewAdded reviewAdded = (ReviewAdded) event1;
                    Assertions.assertEquals(reviewAdded.getAnnotation(), event.getAnnotation());
                    return true;
                })
                .verifyComplete();

    }
}