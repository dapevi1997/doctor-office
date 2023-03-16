package co.com.sofka.model.week.events;

import co.com.sofka.model.patient.generic.DomainEvent;

public class AvailabilityUpdated extends DomainEvent {
    private String idWeek;
    private String availability;

    public AvailabilityUpdated() {
        super("perez.daniel.availabilityupdated");
    }

    public AvailabilityUpdated(String idWeek, String availability) {
        super("perez.daniel.availabilityupdated");
        this.idWeek = idWeek;
        this.availability = availability;
    }

    public String getIdWeek() {
        return idWeek;
    }

    public String getAvailability() {
        return availability;
    }
}
