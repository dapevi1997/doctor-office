package co.com.sofka.model.week.values;

import co.com.sofka.model.patient.generic.ValueObject;

public class Date implements ValueObject<String> {
    private String date;

    public Date(String date) {
        this.date = date;
    }

    @Override
    public String value() {
        return date;
    }

    public String getDate() {
        return date;
    }
}
