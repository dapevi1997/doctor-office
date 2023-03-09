package co.com.sofka.model.week.values;

import co.com.sofka.model.patient.generic.ValueObject;

public class State implements ValueObject<String> {
    private String state;

    public State(String state) {
        this.state = state;
    }

    @Override
    public String value() {
        return state;
    }

    public String getState() {
        return state;
    }
}
