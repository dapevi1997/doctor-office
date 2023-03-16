package co.com.sofka.usecase.addweek;

import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.patient.values.*;
import co.com.sofka.model.week.Week;
import co.com.sofka.model.week.events.WeekAdded;
import co.com.sofka.model.week.utils.MessageEvent;
import co.com.sofka.model.week.values.*;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import co.com.sofka.usecase.generic.commands.AddWeekCommand;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.gateways.EventBus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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


            Availability availability = mapperUtils.mapperToStringToAvailability().apply(command.getAvailability());
            LocalDate[] date = mapperUtils.mapperToDate().apply(command.getDate());

            for (LocalDateTime localDateTime: availability.getAvailability()) {
                if (localDateTime.toLocalDate().isBefore(date[0]) || localDateTime.toLocalDate().isAfter(date[1])){
                    return List.of(new MessageEvent(""));
                }


            }


            Week week = new Week(WeekId.of(command.getWeekId()), CitationId.of(command.getCitaId()), new Infomation(command.getInformation()),
                    new CitationState(command.getCitationState()), PatientId.of(command.getPatientId()),
                     availability,new Date(command.getDate()));

            return week.getUncommittedChanges();
        }).flatMap(event -> {
            if (event.getClass() == WeekAdded.class){
                return repository.saveEvent(event);
            }
            return Mono.just(new MessageEvent("No se pudo guardar semana, verifique que las fechas " +
                    "disponibles estén dentro del rango de la semana"));

        }).map(event ->{
            bus.publish(event);
            return event;
        });
    }
}
