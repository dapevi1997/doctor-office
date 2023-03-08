package co.com.sofka.events;

import co.com.sofka.events.data.Notification;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.serializer.JSONMapper;
import co.com.sofka.usecase.generic.gateways.EventBus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RabbitMqEventBus implements EventBus {
    public static final String EXCHANGE = "core-posts-events";
    public static final String ROUTING_KEY = "events.routing.key";
    private final RabbitTemplate rabbitTemplate;
    private final JSONMapper serializer;

    public RabbitMqEventBus(RabbitTemplate rabbitTemplate,  JSONMapper serializer) {
        this.serializer = serializer;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(DomainEvent event) {
        var notification = new Notification(
                event.getClass().getTypeName(),
                serializer.writeToJson(event)
        );
        rabbitTemplate.convertAndSend(
                this.EXCHANGE, this.ROUTING_KEY, notification.serialize().getBytes()
        );
    }

    @Override
    public void publishError(Throwable errorEvent) {

    }
}
