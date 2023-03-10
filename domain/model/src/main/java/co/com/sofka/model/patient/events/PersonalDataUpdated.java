package co.com.sofka.model.patient.events;

import co.com.sofka.model.patient.generic.DomainEvent;

public class PersonalDataUpdated extends DomainEvent {
    private String id;
    private String personalData;

    public PersonalDataUpdated() {
        super("perez.daniel.personaldataupdated");
    }
    public PersonalDataUpdated(String id, String personalData){
        super("perez.daniel.personaldataupdated");
        this.id = id;
        this.personalData = personalData;

    }

    public String getId() {
        return id;
    }

    public String getPersonalData() {
        return personalData;
    }
}
