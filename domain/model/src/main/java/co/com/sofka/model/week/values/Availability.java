package co.com.sofka.model.week.values;

import co.com.sofka.model.patient.generic.ValueObject;

import java.time.LocalDateTime;
import java.util.Set;

public class Availability implements ValueObject<Set<LocalDateTime>> {
    private Set<LocalDateTime> availability;

    public Availability() {
    }

    public Availability(Set<LocalDateTime> availability) {


        this.availability = availability;
    }

    @Override
    public Set<LocalDateTime> value() {
        return availability;
    }

    public Set<LocalDateTime> getAvailability() {
        return availability;
    }

    @Override
    public String toString() {
        return "Availability{" +
                "availability=" + availability +
                '}';
    }
}
