package co.com.sofka.model.week.values;

import co.com.sofka.model.patient.generic.Identity;

public class WeekId extends Identity {
    public WeekId() {
    }

    public WeekId(String uuid) {
        super(uuid);
    }

    public static WeekId of(String uuid){
        return new WeekId(uuid);

    }

}
