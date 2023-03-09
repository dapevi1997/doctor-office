package co.com.sofka.model.week.values;

import co.com.sofka.model.patient.generic.ValueObject;

public class Availability implements ValueObject<String> {
    private String availability;

    public Availability(String availability) {
        this.availability = availability;
    }

    @Override
    public String value() {
        return availability;
    }

    public String getAvailability() {
        return availability;
    }
}
