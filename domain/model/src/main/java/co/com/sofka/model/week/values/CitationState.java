package co.com.sofka.model.week.values;

import co.com.sofka.model.patient.generic.ValueObject;

public class CitationState implements ValueObject<String> {
    private String citationState;

    public CitationState(String citationState) {
        this.citationState = citationState;
    }

    @Override
    public String value() {
        return null;
    }

    public String getCitationState() {
        return citationState;
    }
}
