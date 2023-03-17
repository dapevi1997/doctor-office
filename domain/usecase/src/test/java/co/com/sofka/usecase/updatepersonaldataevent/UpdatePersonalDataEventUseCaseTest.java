package co.com.sofka.usecase.updatepersonaldataevent;

import co.com.sofka.model.patient.events.PersonalDataUpdated;
import co.com.sofka.model.patient.events.ReviewAdded;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.usecase.addreviewevent.AddReviewEventUseCase;
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
class UpdatePersonalDataEventUseCaseTest {
    @Mock
    private DomainEventRepository repository;

    @Mock
    private EventBus bus;

    private UpdatePersonalDataEventUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new UpdatePersonalDataEventUseCase(repository,bus);
    }

    @Test
    void apply() {
        final String AGGREGATE_ID = "test-addpatient-id";
        PersonalDataUpdated event = new PersonalDataUpdated("patientId","personalData");
        event.setAggregateRootId(AGGREGATE_ID);

        Mockito.when(repository.saveEvent(ArgumentMatchers.any(PersonalDataUpdated.class))).thenAnswer(
                invocation -> Mono.just(invocation.getArgument(0))
        );

        //Act
        Flux<DomainEvent> result = useCase.apply(Mono.just(event));

        StepVerifier.create(result)
                .expectNextMatches(event1 -> {
                    PersonalDataUpdated personalDataUpdated = (PersonalDataUpdated) event1;
                    Assertions.assertEquals(personalDataUpdated.getPersonalData(), event.getPersonalData());
                    return true;
                })
                .verifyComplete();



    }
}