package co.com.sofka.model.week;

import co.com.sofka.model.patient.generic.EventChange;
import co.com.sofka.model.week.events.CitationAdded;
import co.com.sofka.model.week.events.CitationCanceled;
import co.com.sofka.model.week.events.WeekAdded;
import co.com.sofka.model.week.events.WeekStateConsulted;
import co.com.sofka.model.week.values.Availability;

public class WeekChange extends EventChange {
    public WeekChange(Week week) {
        apply((WeekAdded event)->{
             String information = event.getInformation();
             week.availability = new Availability(event.getAvailability());



        });
        apply((CitationAdded event) ->{

        });
        apply((CitationCanceled event) ->{

        });
        apply((WeekStateConsulted event) ->{

        });
    }
}
