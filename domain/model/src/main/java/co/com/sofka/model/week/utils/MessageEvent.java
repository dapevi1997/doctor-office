package co.com.sofka.model.week.utils;

import co.com.sofka.model.patient.generic.DomainEvent;

public class MessageEvent extends DomainEvent {


    public MessageEvent() {
        super("error: citation no available");
    }

    public MessageEvent(String message) {
        super(message);
    }
}
