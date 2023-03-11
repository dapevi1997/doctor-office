package co.com.sofka.usecase.addcitation;

import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.model.week.Week;
import co.com.sofka.model.week.utils.PatientExits;
import co.com.sofka.model.week.values.CitationId;
import co.com.sofka.model.week.values.CitationState;
import co.com.sofka.model.week.values.Infomation;
import co.com.sofka.model.week.values.WeekId;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import co.com.sofka.usecase.generic.commands.AddCitationCommand;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.gateways.EventBus;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

public class AddCitationUseCase extends UseCaseForCommand<AddCitationCommand> {
    private final DomainEventRepository repository;
    private final EventBus bus;

    public AddCitationUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
    }



    @Override
    public Flux<DomainEvent> apply(Mono<AddCitationCommand> addCitationCommandMono) {




        return addCitationCommandMono.flatMapMany(command -> {
                    return repository.findById(command.getWeekId())
                            .collectList()
                            .flatMapIterable(events -> {
                                Week week = Week.from(WeekId.of(command.getWeekId()), events);
                                week.addCitation(CitationId.of(command.getCitaId()), new Infomation(command.getInformation()),
                                        new CitationState(command.getCitationState()), PatientId.of(command.getPatientId()), WeekId.of(command.getWeekId()));
                                return week.getUncommittedChanges();
                            }).map(event -> {
                                bus.publish(event);
                                return event;
                            }).flatMap(event -> {
                                Mono<Boolean> exits = repository.exist(command.getPatientId());
                                Disposable subscribe = exits.subscribe(data -> {
                                    if (data.equals(true)){
                                         repository.saveEvent(event);
                                    }
                                });
                                subscribe.dispose();



                                return Mono.just(new PatientExits(false));
                            });
                }


                //exist.subscribe(data-> System.out.println(data));
                //return Mono.just(new PatientExits(true));


        );


    }
}
