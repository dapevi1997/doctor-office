package co.com.sofka.usecase.generic.gateways;


import co.com.sofka.model.patient.generic.DomainEvent;

public interface EventBus {
    void publish(DomainEvent event);

    void publishError(Throwable errorEvent);
}
