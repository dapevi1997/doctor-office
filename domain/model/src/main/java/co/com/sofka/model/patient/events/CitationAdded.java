package co.com.sofka.model.patient.events;

import co.com.sofka.model.patient.generic.DomainEvent;

public class CitationAdded extends DomainEvent {
    private String id;
    private String information;
    private String citationState;
    private String patientId;

    public CitationAdded() {
        super("perez.daniel.citationadded");
    }
    public CitationAdded(String id, String information, String citationState, String patientId){
        super("perez.daniel.citationadded");
        this.id = id;
        this.information = information;
        this.citationState = citationState;
        this.patientId = patientId;

    }

    public String getId() {
        return id;
    }

    public String getInformation() {
        return information;
    }

    public String getCitationState() {
        return citationState;
    }

    public String getPatientId() {
        return patientId;
    }
}
