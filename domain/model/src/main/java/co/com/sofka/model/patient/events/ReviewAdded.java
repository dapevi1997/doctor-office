package co.com.sofka.model.patient.events;

import co.com.sofka.model.patient.generic.DomainEvent;

public class ReviewAdded extends DomainEvent {
    public ReviewAdded() {
        super("perez.daniel.reviewadded");
    }
    public ReviewAdded(String id){
        super("perez.daniel.reviewadded");

    }
}
