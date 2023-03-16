package co.com.sofka.usecase.addcitation;

import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.model.week.Week;
import co.com.sofka.model.week.events.AvailabilityUpdated;
import co.com.sofka.model.week.events.CitationAdded;
import co.com.sofka.model.week.events.WeekAdded;
import co.com.sofka.model.week.utils.MessageEvent;
import co.com.sofka.model.week.values.*;
import co.com.sofka.usecase.addweek.MapperUtils;
import co.com.sofka.usecase.generic.UseCaseForCommand;
import co.com.sofka.usecase.generic.commands.AddCitationCommand;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import co.com.sofka.usecase.generic.gateways.EventBus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class AddCitationUseCase extends UseCaseForCommand<AddCitationCommand> {
    private final DomainEventRepository repository;
    private final EventBus bus;

    private MapperUtils mapperUtils;

    public AddCitationUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
        this.mapperUtils = new MapperUtils();
    }


    @Override
    public Flux<DomainEvent> apply(Mono<AddCitationCommand> addCitationCommandMono) {

        AtomicReference<Availability> availabilityAtomicReference = new AtomicReference<>();

        return addCitationCommandMono.flatMapMany(command->{
            return repository.findById(command.getPatientId())
                    .collectList()
                    .flatMap(events -> {
                        if (events.size() == 0){
                            return Mono.just(Boolean.FALSE);
                        }
                        Patient patient = Patient.from(PatientId.of(command.getPatientId()), events);
                        return Mono.just(patient.getAvailable().getAvailable());

                    }).filter(aBoolean -> aBoolean.equals(true)).flatMapMany(aBoolean -> {
                        return repository.findById(command.getWeekId())
                                .collectList()
                                .flatMapIterable(events -> {
                                    Week week = Week.from(WeekId.of(command.getWeekId()), events);
                                    availabilityAtomicReference.set(week.getAvailability());
                                    week.addCitation(CitationId.of(command.getCitaId()), new Infomation(command.getInformation()),
                                            new CitationState(command.getCitationState()), PatientId.of(command.getPatientId()), WeekId.of(command.getWeekId()));

                                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                                    LocalDateTime citationInformation = LocalDateTime.parse(command.getInformation(), dateTimeFormatter);

                                    Set<LocalDateTime> availabilityCopy = new HashSet<>();
                                    availabilityCopy.addAll(week.getAvailability().getAvailability());


                                    availabilityCopy.remove(citationInformation);


                                    week.updateAvailability(WeekId.of(command.getWeekId()), new Availability(availabilityCopy));
                                    return week.getUncommittedChanges();
                                }).flatMap(event -> {
                                    if (event.getClass() == CitationAdded.class) {
                                        CitationAdded citationAdded = (CitationAdded) event;
                                        Availability availability = availabilityAtomicReference.get();
                                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                                        LocalDateTime citationInformation = LocalDateTime.parse(citationAdded.getInformation(), dateTimeFormatter);
                                        for (LocalDateTime localDateTime : availability.getAvailability()) {
                                            if (localDateTime.equals(citationInformation)) {
                                                bus.publish(event);

                                                return repository.saveEvent(event);
                                            }
                                        }

                                    }

                                    if (event.getClass() == AvailabilityUpdated.class) {

                                        return repository.saveEvent(event);
                                    }


                                    return Mono.just(new MessageEvent("Error creating a citation"));
                                });
                    }
                    );


        });





/*        AtomicReference<Availability> availabilityAtomicReference = new AtomicReference<>();

        return addCitationCommandMono.flatMapMany(command -> {
            return repository.exists(command.getPatientId())
                    .flatMapMany(aBoolean -> {
                        if (aBoolean == true) {
                            //return new PatientExits(aBoolean);

                            return repository.findById(command.getWeekId()).collectList()
                                    .flatMapIterable(
                                            domainEvent -> {
                                                Week week = Week.from(WeekId.of(command.getWeekId()), domainEvent);
                                                availabilityAtomicReference.set(week.getAvailability());

                                                week.addCitation(CitationId.of(command.getCitaId()), new Infomation(command.getInformation()),
                                                        new CitationState(command.getCitationState()), PatientId.of(command.getPatientId()), WeekId.of(command.getWeekId()));

                                                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                                                LocalDateTime citationInformation = LocalDateTime.parse(command.getInformation(), dateTimeFormatter);

                                                Set<LocalDateTime> availabilityCopy = new HashSet<>();
                                                availabilityCopy.addAll(week.getAvailability().getAvailability());


                                                availabilityCopy.remove(citationInformation);


                                                week.updateAvailability(WeekId.of(command.getWeekId()), new Availability(availabilityCopy));


                                                return week.getUncommittedChanges();
                                            }
                                    )
.map(domainEvent -> {
                                        bus.publish(domainEvent);
                                        return domainEvent;
                                    })

                                    .flatMap(domainEvent -> {

                                        if (domainEvent.getClass() == CitationAdded.class) {
                                            CitationAdded citationAdded = (CitationAdded) domainEvent;
                                            Availability availability = availabilityAtomicReference.get();
                                            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                                            LocalDateTime citationInformation = LocalDateTime.parse(citationAdded.getInformation(), dateTimeFormatter);
                                            for (LocalDateTime localDateTime : availability.getAvailability()) {
                                                if (localDateTime.equals(citationInformation)) {
                                                    bus.publish(domainEvent);

                                                    return repository.saveEvent(domainEvent);
                                                }
                                            }

                                        }

                                        if (domainEvent.getClass() == AvailabilityUpdated.class) {

                                            return repository.saveEvent(domainEvent);
                                        }


                                        return Mono.just(new MessageEvent("Error creating a citation"));
                                    });


                        }

                        Flux<DomainEvent> byId = repository.findById(command.getWeekId());
                        return byId;
                    });
        });*/






    }
}
