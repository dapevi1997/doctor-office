package co.com.sofka.usecase.deletepatientevent;

import co.com.sofka.model.patient.events.PatientDeleted;
import co.com.sofka.model.patient.generic.DomainEvent;
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
class DeletePatientEventUseCaseTest {

    @Mock
    private DomainEventRepository repository;

    @Mock
    private EventBus bus;

    private DeletePatientEventUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new DeletePatientEventUseCase(repository,bus);
    }

    @Test
    void apply() {
        final String AGGREGATE_ID = "test-addpatient-id";
        PatientDeleted event = new PatientDeleted("idPatient","false");
        event.setAggregateRootId(AGGREGATE_ID);

        Mockito.when(repository.saveEvent(ArgumentMatchers.any(PatientDeleted.class))).thenAnswer(
                invocation -> Mono.just(invocation.getArgument(0))
        );

        //Act
        Flux<DomainEvent> result = useCase.apply(Mono.just(event));

        StepVerifier.create(result)
                .expectNextMatches(event1 -> {
                    PatientDeleted patientDeleted = (PatientDeleted) event1;
                    Assertions.assertEquals(patientDeleted.getAvailable(), event.getAvailable());
                    return true;
                })
                .verifyComplete();
    }
}