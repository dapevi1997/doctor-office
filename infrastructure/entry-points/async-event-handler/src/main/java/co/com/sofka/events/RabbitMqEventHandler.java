package co.com.sofka.events;

import co.com.sofka.events.data.Notification;
import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.model.patient.values.PersonalData;
import co.com.sofka.model.week.events.CitationAdded;
import co.com.sofka.serializer.JSONMapper;
import co.com.sofka.serializer.JSONMapperImpl;
import co.com.sofka.usecase.addpatientevent.AddPatientEventUseCase;
import co.com.sofka.usecase.generic.gateways.DomainEventRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

@Component
public class RabbitMqEventHandler {
    public static final String EVENTS_QUEUE = "events.queue";
    public static final String GENERAL_QUEUE = "general.queue";
    private final Logger logger = Logger.getLogger("RabbitMqEventHandler");
    private final JSONMapper mapper = new JSONMapperImpl();

    private final JavaMailSender javaMailSender;
    private final DomainEventRepository repository;

    private final AddPatientEventUseCase addPatientUseCase;

    public RabbitMqEventHandler(JavaMailSender javaMailSender, DomainEventRepository repository, AddPatientEventUseCase addPatientEventUseCase) {
        this.javaMailSender = javaMailSender;
        this.repository = repository;

        this.addPatientUseCase = addPatientEventUseCase;
    }

    @RabbitListener(queues = EVENTS_QUEUE)
    public void listener(String message) throws ClassNotFoundException {
        AtomicReference<PersonalData> personalData = new AtomicReference<>();
        Notification notification = Notification.from(message);
        if (notification.getType()
                .equals("co.com.sofka.model.week.events.CitationAdded")) {
            CitationAdded citationAdded = (CitationAdded) mapper.readFromJson(notification.getBody(), CitationAdded.class);
            logger.info("1: " + notification.toString());
            repository.findById(citationAdded.getPatientId())
                    .collectList().subscribe(events -> {
                                Patient patient = Patient.from(PatientId.of(citationAdded.getPatientId()), events);
                                SimpleMailMessage email = new SimpleMailMessage();
                                email.setTo(patient.getPersonalData().getPersonalData());
                                email.setFrom("danielperezvitola@gmail.com");
                                email.setSubject("CITA AGENDADA");
                                email.setText("Se ha agendado la cita correctamente");

                                javaMailSender.send(email);

                                logger.info("1: Email enviado con éxito" );
                                //System.out.println(patient.getPersonalData().getPersonalData());
                            }

                    );



        } else {
            logger.info("1: " + "we currently don't have a listener for that event " + notification.toString());
        }
    }

/*    @RabbitListener(queues = GENERAL_QUEUE)
    public void listenerGeneral(String message) throws ClassNotFoundException {
        Notification notification = Notification.from(message);
        if(notification.getType()
                .equals("co.com.alpha.bcb.model.post.events.PostCreated")){
            logger.info("2:" + notification.toString());
            *//*this.useCase.apply(Mono
                    .just((PostCreated) mapper.readFromJson(notification.getBody(),
                            PostCreated.class)))
                    .subscribe();*//*
        }else{
            logger.info("2:" + "we currently don't have a listener for that event " +notification.toString());
        }
    }*/
}
