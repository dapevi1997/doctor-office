package co.com.sofka.model.week;

import co.com.sofka.model.patient.generic.EventChange;
import co.com.sofka.model.week.events.WeekAdded;

public class WeekChange extends EventChange {
    public WeekChange(Week week) {
        apply((WeekAdded event)->{

        });
    }
}
