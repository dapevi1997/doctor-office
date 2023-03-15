package co.com.sofka.model.patient.values;

import co.com.sofka.model.patient.generic.ValueObject;

public class Available implements ValueObject<Boolean> {
    private Boolean available;

    public Available(Boolean available) {
        this.available = available;
    }

    @Override
    public Boolean value() {
        return available;
    }

    public Boolean getAvailable() {
        return available;
    }
}
