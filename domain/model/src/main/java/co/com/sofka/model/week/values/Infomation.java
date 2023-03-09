package co.com.sofka.model.week.values;

import co.com.sofka.model.patient.generic.ValueObject;

public class Infomation implements ValueObject<String> {
    private String information;

    public Infomation(String information) {
        this.information = information;
    }

    @Override
    public String value() {
        return null;
    }

    public String getInformation() {
        return information;
    }
}
