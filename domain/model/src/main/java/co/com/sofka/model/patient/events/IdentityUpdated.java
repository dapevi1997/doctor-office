package co.com.sofka.model.patient.events;

import co.com.sofka.model.patient.generic.DomainEvent;

public class IdentityUpdated extends DomainEvent {
    private String id;
    private String identity;

    public IdentityUpdated() {
        super("perez.daniel.identityupdated");
    }
    public IdentityUpdated(String id, String identity){
        super("perez.daniel.identityupdated");

    }

    public String getId() {
        return id;
    }

    public String getIdentity() {
        return identity;
    }
}
