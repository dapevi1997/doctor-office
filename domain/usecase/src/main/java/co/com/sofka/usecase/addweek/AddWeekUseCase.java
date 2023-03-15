package co.com.sofka.usecase.addweek;

import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.patient.values.*;
import co.com.sofka.model.week.Week;
import co.com.sofka.model.week.values.*;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import co.com.sofka.usecase.generic.commands.AddWeekCommand;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.gateways.EventBus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class AddWeekUseCase extends UseCaseForCommand<AddWeekCommand> {
    private final DomainEventRepository repository;
    private final EventBus bus;

    private MapperUtils mapperUtils;



    public AddWeekUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
        this.mapperUtils = new MapperUtils();

    }

    @Override
    public Flux<DomainEvent> apply(Mono<AddWeekCommand> addWeekCommandMono) {
        return addWeekCommandMono.flatMapIterable(command->{

            Availability availability = mapperUtils.mapperToAvailability().apply(command.getAvailability());


            Week week = new Week(WeekId.of(command.getWeekId()), CitationId.of(command.getCitaId()), new Infomation(command.getInformation()),
                    new CitationState(command.getCitationState()), PatientId.of(command.getPatientId()),
                     availability, new Date(command.getDate()));

            return week.getUncommittedChanges();
        }).flatMap(event -> {
            return repository.saveEvent(event);
        }).map(event ->{
            bus.publish(event);
            return event;
        });
    }
}
