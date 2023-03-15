package co.com.sofka.model.patient.events;

import co.com.sofka.model.patient.generic.DomainEvent;

public class PatientDeleted extends DomainEvent {

    private String idPatient;
    private String available;
    public PatientDeleted() {
        super("perez.daniel.patientdeleted");
    }
    public PatientDeleted( String idPatient, String available){
        super("perez.daniel.patientdeleted");

        this.idPatient = idPatient;
        this.available = available;
    }

    public String getIdPatient() {
        return idPatient;
    }

    public String getAvailable() {
        return available;
    }
}
