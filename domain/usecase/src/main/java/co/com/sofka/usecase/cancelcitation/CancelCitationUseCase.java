package co.com.sofka.usecase.cancelcitation;

import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.model.patient.values.PersonalData;
import co.com.sofka.model.week.Week;
import co.com.sofka.model.week.entities.Citation;
import co.com.sofka.model.week.values.Availability;
import co.com.sofka.model.week.values.CitationId;
import co.com.sofka.model.week.values.CitationState;
import co.com.sofka.model.week.values.WeekId;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import co.com.sofka.usecase.generic.commands.CancelCitationCommand;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.gateways.EventBus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CancelCitationUseCase extends UseCaseForCommand<CancelCitationCommand> {
    private final DomainEventRepository repository;
    private final EventBus bus;

    public CancelCitationUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<CancelCitationCommand> cancelCitationCommandMono) {
        return cancelCitationCommandMono.flatMapMany(command->
                repository.findById(command.getWeekId())
                        .collectList()
                        .flatMapIterable(events -> {
                            Week week = Week.from(WeekId.of(command.getWeekId()), events);

                            week.cancelateCitation(WeekId.of(command.getWeekId()), CitationId.of(command.getCitaId()),
                                    new CitationState(command.getCitationState()) );

                            return week.getUncommittedChanges();
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
