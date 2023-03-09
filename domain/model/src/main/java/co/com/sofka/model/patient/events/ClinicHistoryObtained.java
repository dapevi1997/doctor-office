package co.com.sofka.model.patient.events;

import co.com.sofka.model.patient.generic.DomainEvent;

public class ClinicHistoryObtained extends DomainEvent {
    public ClinicHistoryObtained() {
        super("perez.daniel.clinichistoryobtained");
    }
    public ClinicHistoryObtained(String id){
        super("perez.daniel.clinichistoryobtained");

    }
}
