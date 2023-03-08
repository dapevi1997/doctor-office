package co.com.sofka.model.patient.values;

import co.com.sofka.model.patient.generic.ValueObject;

public class Identity implements ValueObject<String> {

   private final String identity;

    public Identity( String identity) {

        this.identity = identity;
    }

    @Override
    public String value() {
        return identity;
    }

    public String getIdentity() {
        return identity;
    }
}
