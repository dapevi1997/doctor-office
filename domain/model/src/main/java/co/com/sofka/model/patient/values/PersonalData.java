package co.com.sofka.model.patient.values;

import co.com.sofka.model.patient.generic.ValueObject;

public class PersonalData implements ValueObject<String> {

   private final String personalData;

    public PersonalData(String personalData) {

        this.personalData = personalData;
    }

    @Override
    public String value() {
        return personalData;
    }

    public String getPersonalData() {
        return personalData;
    }

    @Override
    public String toString() {
        return "Identity{" +
                "identity='" + personalData + '\'' +
                '}';
    }
}
