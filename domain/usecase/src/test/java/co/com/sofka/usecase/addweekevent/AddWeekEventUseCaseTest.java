package co.com.sofka.usecase.addweekevent;

import co.com.sofka.model.patient.events.PatientAdded;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.week.events.WeekAdded;
import co.com.sofka.usecase.addpatientevent.AddPatientEventUseCase;
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
class AddWeekEventUseCaseTest {

    @Mock
    private DomainEventRepository repository;

    @Mock
    private EventBus bus;

    private AddWeekEventUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new AddWeekEventUseCase(repository,bus);
    }

    @Test
    void apply() {
        //Arrange
        final String AGGREGATE_ID = "test-addpatient-id";
        WeekAdded event = new WeekAdded("idWeek","citationId","informarion","citationState","patientId",
                "[2022-03-14T08:00,2022-03-14T09:00]","2022-03-14;2022-03-20");
        event.setAggregateRootId(AGGREGATE_ID);

        Mockito.when(repository.saveEvent(ArgumentMatchers.any(WeekAdded.class))).thenAnswer(
                invocation -> Mono.just(invocation.getArgument(0))
        );

        //Act
        Flux<DomainEvent> result = useCase.apply(Mono.just(event));

        StepVerifier.create(result)
                .expectNextMatches(event1 -> {
                    WeekAdded weekAdded = (WeekAdded) event1;
                    Assertions.assertEquals(weekAdded.getInformation(), event.getInformation());
                    return true;
                }).verifyComplete();



    }
}