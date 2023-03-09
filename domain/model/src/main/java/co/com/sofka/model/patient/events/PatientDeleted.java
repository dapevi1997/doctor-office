package co.com.sofka.model.patient.events;

import co.com.sofka.model.patient.generic.DomainEvent;

public class PatientDeleted extends DomainEvent {
    public PatientDeleted() {
        super("perez.daniel.patientdeleted");
    }
    public PatientDeleted(String id){
        super("perez.daniel.patientdeleted");

    }
}
