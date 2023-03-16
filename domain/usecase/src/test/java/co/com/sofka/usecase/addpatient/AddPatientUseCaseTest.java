package co.com.sofka.usecase.addpatient;

import co.com.sofka.model.patient.events.PatientAdded;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.patient.values.*;
import co.com.sofka.usecase.generic.commands.AddPatientCommand;
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
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddPatientUseCaseTest {

    @Mock
    private DomainEventRepository repository;

    @Mock
    private EventBus bus;
    @SpyBean
    private AddPatientUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new AddPatientUseCase(repository, bus);
    }

    @Test
    void apply() {
        //Arrange
        final String AGGREGATE_ID = "test-addPatient-id";
        AddPatientCommand command = new AddPatientCommand(PatientId.of("patientId"), new PersonalData("personalData"),
                new ClinicHistory("clinicHistory"), ReviewId.of("reviewId"), new Annotation("anotation"), "available");

        PatientAdded event = new PatientAdded("idPatient", "idReview", "anotation",
                "email", "clinicHistory", "available");
        event.setAggregateRootId(AGGREGATE_ID);

        Mockito.when(repository.saveEvent(ArgumentMatchers.any(PatientAdded.class)))
                .thenAnswer(
                     invocation ->    Mono.just(invocation.getArgument(0))
                );
       Mockito.doAnswer(i -> null).when(bus).publish(ArgumentMatchers.any(DomainEvent.class));

        //Act
        Flux<DomainEvent> result = useCase.apply(Mono.just(command));

        StepVerifier.create(result)
                //Assert
                .expectNextMatches(event1 -> {
                    PatientAdded patientAdded = (PatientAdded) event1;
                    Assertions.assertEquals(patientAdded.getId(), event.getId());
                    return true;
                })
                .verifyComplete();


    }

}