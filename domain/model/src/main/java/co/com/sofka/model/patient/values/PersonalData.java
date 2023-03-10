package co.com.sofka.model.patient.values;

import co.com.sofka.model.patient.generic.ValueObject;

public class PersonalData implements ValueObject<String> {

   private final String identity;

    public PersonalData(String identity) {

        this.identity = identity;
    }

    @Override
    public String value() {
        return identity;
    }

    public String getIdentity() {
        return identity;
    }

    @Override
    public String toString() {
        return "Identity{" +
                "identity='" + identity + '\'' +
                '}';
    }
}
