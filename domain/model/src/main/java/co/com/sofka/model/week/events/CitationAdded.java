package co.com.sofka.model.week.events;

import co.com.sofka.model.patient.generic.DomainEvent;

public class CitationAdded extends DomainEvent {
    private String id;
    private String information;
    private String citationState;
    private String patientId;
    private String weekId;

    public CitationAdded() {
        super("perez.daniel.citationadded");
    }
    public CitationAdded(String id, String information, String citationState, String patientId, String weekId){
        super("perez.daniel.citationadded");
        this.id = id;
        this.information = information;
        this.citationState = citationState;
        this.patientId = patientId;
        this.weekId = weekId;

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

    public String getWeekId() {
        return weekId;
    }

    @Override
    public String toString() {
        return "CitationAdded{" +
                "id='" + id + '\'' +
                ", information='" + information + '\'' +
                ", citationState='" + citationState + '\'' +
                ", patientId='" + patientId + '\'' +
                ", weekId='" + weekId + '\'' +
                '}';
    }
}
