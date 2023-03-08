package co.com.sofka.events;

import co.com.sofka.events.data.Notification;
import co.com.sofka.model.patient.events.PatientAdded;
import co.com.sofka.serializer.JSONMapper;
import co.com.sofka.serializer.JSONMapperImpl;
import co.com.sofka.usecase.addpatient.AddPatientUseCase;
import co.com.sofka.usecase.addpatientevent.AddPatientEventUseCase;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Component
public class RabbitMqEventHandler {
    public static final String EVENTS_QUEUE = "events.queue";
    public static final String GENERAL_QUEUE = "general.queue";
    private final Logger logger = Logger.getLogger("RabbitMqEventHandler");
    private final JSONMapper mapper = new JSONMapperImpl();

    private final AddPatientEventUseCase addPatientUseCase;

    public RabbitMqEventHandler(AddPatientEventUseCase addPatientEventUseCase) {
        this.addPatientUseCase = addPatientEventUseCase;
    }

    @RabbitListener(queues = EVENTS_QUEUE)
    public void listener(String message) throws ClassNotFoundException {
        Notification notification = Notification.from(message);
        if(notification.getType()
                .equals("co.com.sofka.model.post.events.PatientAdded")){
            logger.info("1: " + notification.toString());
            this.addPatientUseCase.apply(Mono
                            .just((PatientAdded) mapper.readFromJson(notification.getBody(),
                                    PatientAdded.class)))
                    .subscribe();
        }else{
            logger.info("1: " + "we currently don't have a listener for that event " +notification.toString());
        }
    }

    @RabbitListener(queues = GENERAL_QUEUE)
    public void listenerGeneral(String message) throws ClassNotFoundException {
        Notification notification = Notification.from(message);
        if(notification.getType()
                .equals("co.com.alpha.bcb.model.post.events.PostCreated")){
            logger.info("2:" + notification.toString());
            /*this.useCase.apply(Mono
                    .just((PostCreated) mapper.readFromJson(notification.getBody(),
                            PostCreated.class)))
                    .subscribe();*/
        }else{
            logger.info("2:" + "we currently don't have a listener for that event " +notification.toString());
        }
    }
}
