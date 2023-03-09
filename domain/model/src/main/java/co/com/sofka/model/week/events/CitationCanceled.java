package co.com.sofka.model.week.events;

import co.com.sofka.model.patient.generic.DomainEvent;

public class CitationCanceled extends DomainEvent {
    public CitationCanceled() {
        super("perez.daniel.citationcanceled");
    }
    public CitationCanceled(String id, String information, String citationState, String patientId){
        super("perez.daniel.citationcanceled");

    }
}
