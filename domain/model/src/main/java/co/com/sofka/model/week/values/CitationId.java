package co.com.sofka.model.week.values;

import co.com.sofka.model.patient.generic.Identity;

public class CitationId extends Identity {
    public CitationId() {
    }

    public CitationId(String uuid) {
        super(uuid);
    }
    public static CitationId of(String uuid){
        return new CitationId(uuid);

    }
}
