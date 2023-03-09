package co.com.sofka.model.week.events;

import co.com.sofka.model.patient.generic.DomainEvent;


public class WeekAdded extends DomainEvent {
    private String id;
    private String availability;
    private String state;
    private String date;
    public WeekAdded() {
        super("perez.daniel.weekadded");
    }
    public WeekAdded(String id, String availability, String state, String date){
        super("perez.daniel.weekadded");
        this.id = id;
        this.availability = availability;
        this.state = state;
        this.date = date;

    }

    public String getId() {
        return id;
    }

    public String getAvailability() {
        return availability;
    }

    public String getState() {
        return state;
    }

    public String getDate() {
        return date;
    }
}
