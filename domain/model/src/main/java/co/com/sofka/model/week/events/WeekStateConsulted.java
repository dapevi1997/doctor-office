package co.com.sofka.model.week.events;

import co.com.sofka.model.patient.generic.DomainEvent;

public class WeekStateConsulted extends DomainEvent {
    public WeekStateConsulted() {
        super("perez.daniel.weekstateconsulted");
    }
    public WeekStateConsulted(String id, String information, String citationState, String patientId){
        super("perez.daniel.weekstateconsulted");

    }
}
