package co.com.sofka.model.week.events;

import co.com.sofka.model.patient.generic.DomainEvent;

public class CitationCanceled extends DomainEvent {
    private String weekId;
    private String citaId;
    private String citationState;
    public CitationCanceled() {
        super("perez.daniel.citationcanceled");
    }
    public CitationCanceled(String weekId, String citaId, String citationState){
        super("perez.daniel.citationcanceled");
        this.weekId = weekId;
        this.citaId = citaId;
        this.citationState = citationState;

    }

    public String getWeekId() {
        return weekId;
    }

    public String getCitaId() {
        return citaId;
    }

    public String getCitationState() {
        return citationState;
    }
}
