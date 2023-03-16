package co.com.sofka.usecase.getcitations;


import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.week.Week;
import co.com.sofka.model.week.events.CitationAdded;
import co.com.sofka.model.week.values.WeekId;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import co.com.sofka.usecase.generic.commands.GetCitationCommand;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.gateways.EventBus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GetCitationUseCase extends UseCaseForCommand<GetCitationCommand> {
    private final DomainEventRepository repository;
    private final EventBus bus;

    public GetCitationUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<GetCitationCommand> getCitationCommandMono) {
        return getCitationCommandMono.flatMapMany(command -> {
            return repository.findById(command.getIdWeek())
                     .collectList()
                     .flatMapIterable(events -> {
                         Week week = Week.from(WeekId.of(command.getIdWeek()), events);
                         return week.getCitations();
                     }).map(citation -> {
                         return new CitationAdded(citation.identity().value(),citation.getInfomation().value(),citation.getCitationState().value(),
                                 citation.getPatientId().value(),citation.getWeekId().value());
                    });
        });

    }

/*    @Override
    public Flux<DomainEvent> apply(Mono<GetCitationCommand> getCitationCommandMono) {
        Flux<DomainEvent> domainEventFlux = getCitationCommandMono.flatMapMany(command -> {
            return repository.findCitation(command.getIdWeek());
        });
        return domainEventFlux;
    }*/
}
