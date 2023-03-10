package co.com.sofka.model.week.utils;

import co.com.sofka.model.patient.generic.DomainEvent;

public class PatientExits extends DomainEvent {

    private Boolean exists;
    public PatientExits() {
        super("dont");
    }

    public PatientExits( Boolean exists) {
        super("type");
        this.exists = exists;
    }
}
