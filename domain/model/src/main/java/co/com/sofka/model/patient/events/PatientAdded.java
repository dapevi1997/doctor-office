package co.com.sofka.model.patient.events;

import co.com.sofka.model.patient.generic.DomainEvent;

public class PatientAdded extends DomainEvent {
    private String id;
    private String identity;
    private String clinicHistory;

    public PatientAdded() {
        super("perez.daniel.patientadded");
    }
    public PatientAdded(String id, String identity, String clinicHistory){
        super("perez.daniel.patientadded");
        this.id = id;
        this.identity = identity;
        this.clinicHistory = clinicHistory;

    }

    public String getId() {
        return id;
    }

    public String getIdentity() {
        return identity;
    }

    public String getClinicHistory() {
        return clinicHistory;
    }
}
