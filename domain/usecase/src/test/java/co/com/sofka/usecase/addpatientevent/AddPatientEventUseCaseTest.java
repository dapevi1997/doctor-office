package co.com.sofka.usecase.addpatientevent;

import co.com.sofka.model.patient.events.PatientAdded;
import co.com.sofka.model.patient.generic.DomainEvent;
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
class AddPatientEventUseCaseTest {
    @Mock
    private DomainEventRepository repository;

    @Mock
    private EventBus bus;

    private AddPatientEventUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new AddPatientEventUseCase(repository,bus);
    }

    @Test
    void apply() {
        //Arrange
        final String AGGREGATE_ID = "test-addpatient-id";

        PatientAdded event = new PatientAdded("idPatient", "idReview", "anotation",
                "email", "clinicHistory", "false");
        event.setAggregateRootId(AGGREGATE_ID);

        Mockito.when(repository.saveEvent(ArgumentMatchers.any(PatientAdded.class))).thenAnswer(
                invocation -> Mono.just(invocation.getArgument(0))
        );

        Mockito.doAnswer(i->null).when(bus).publish(ArgumentMatchers.any(DomainEvent.class));

        //Act
        Flux<DomainEvent> result = useCase.apply(Mono.just(event));

        StepVerifier.create(result)
                .expectNextMatches(event1 -> {
                    PatientAdded patientAdded = (PatientAdded) event1;
                    Assertions.assertEquals(patientAdded.getAvailable(), event.getAvailable());
                    return true;
                }).verifyComplete();

    }
}