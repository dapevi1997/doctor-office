package co.com.sofka.usecase.getclinichistory;

import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import co.com.sofka.usecase.generic.commands.GetClinicHistoryCommand;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.gateways.EventBus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GetClinicHistoryUseCase extends UseCaseForCommand<GetClinicHistoryCommand> {
    private final DomainEventRepository repository;
    private final EventBus bus;

    public GetClinicHistoryUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<GetClinicHistoryCommand> getClinicHistoryCommandMono) {
        Flux<DomainEvent> domainEventFlux = getClinicHistoryCommandMono.flatMapMany(command -> {
            return repository.findClinicHistory(command.getIdPatient());
        });
        return domainEventFlux;
    }
}
