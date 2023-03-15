package co.com.sofka.model.week.events;

import co.com.sofka.model.patient.generic.DomainEvent;


public class WeekAdded extends DomainEvent {
    private String id;

    private String citationId;

    private String information;
    private String citationState;
    private String patientId;
    private String availability;
    private String date;
    public WeekAdded() {
        super("perez.daniel.weekadded");
    }
    public WeekAdded(String id,String citationId, String information, String citationState, String patientId,
                     String availability, String date){
        super("perez.daniel.weekadded");
        this.id = id;
        this.availability = availability;

        this.date = date;
        this.citationId = citationId;
        this.information = information;
        this.citationState = citationState;
        this.patientId = patientId;

    }

    public String getId() {
        return id;
    }

    public String getAvailability() {
        return availability;
    }



    public String getDate() {
        return date;
    }

    public String getCitationId() {
        return citationId;
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
