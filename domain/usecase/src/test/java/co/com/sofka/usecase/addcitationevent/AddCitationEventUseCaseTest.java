package co.com.sofka.usecase.addcitationevent;

import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.week.events.AvailabilityUpdated;
import co.com.sofka.model.week.events.CitationAdded;
import co.com.sofka.model.week.events.CitationCanceled;
import co.com.sofka.usecase.cancelcitationevent.CancelCitationEventUseCase;
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

@ExtendWith(MockitoExtension.class)
class AddCitationEventUseCaseTest {

    @Mock
    private DomainEventRepository repository;

    @Mock
    private EventBus bus;

    private AddCitationEventUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new AddCitationEventUseCase(repository,bus);
    }

    @Test
    void apply() {
        final String AGGREGATE_ID = "test-addpatient-id";
        CitationAdded event = new CitationAdded("citaId","information",
                "true","idPatient","idWeek");
        event.setAggregateRootId(AGGREGATE_ID);


        Mockito.when(repository.saveEvent(ArgumentMatchers.any(CitationAdded.class))).thenAnswer(
                invocation -> Mono.just(invocation.getArgument(0))
        );



        //Act
        Flux<DomainEvent> result = useCase.apply(Mono.just(event));

        StepVerifier.create(result)
                .expectNextMatches(event1 -> {
                        CitationAdded citationAdded = (CitationAdded) event1;
                        assertEquals(citationAdded.getCitationState(), event.getCitationState());
                        return true;
                })
                .verifyComplete();
    }



}