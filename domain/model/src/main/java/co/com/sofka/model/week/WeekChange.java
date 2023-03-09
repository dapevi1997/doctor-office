package co.com.sofka.model.week;

import co.com.sofka.model.patient.generic.EventChange;
import co.com.sofka.model.week.events.CitationAdded;
import co.com.sofka.model.week.events.CitationCanceled;
import co.com.sofka.model.week.events.WeekAdded;
import co.com.sofka.model.week.events.WeekStateConsulted;

public class WeekChange extends EventChange {
    public WeekChange(Week week) {
        apply((WeekAdded event)->{

        });
        apply((CitationAdded event) ->{

        });
        apply((CitationCanceled event) ->{

        });
        apply((WeekStateConsulted event) ->{

        });
    }
}
