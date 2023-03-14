package co.com.sofka.model.week;

import co.com.sofka.model.patient.generic.EventChange;
import co.com.sofka.model.week.events.CitationAdded;
import co.com.sofka.model.week.events.CitationCanceled;
import co.com.sofka.model.week.events.WeekAdded;
import co.com.sofka.model.week.events.WeekStateConsulted;
import co.com.sofka.model.week.utils.MapperUtils;
import co.com.sofka.model.week.values.Availability;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class WeekChange extends EventChange {
    private MapperUtils mapperUtils;

    public WeekChange(Week week) {

        apply((WeekAdded event)->{
          this.mapperUtils = new MapperUtils();
            Availability availability = mapperUtils.mapperToStringToAvailability().apply(event.getAvailability());

             week.availability = availability;



        });
        apply((CitationAdded event) ->{



        });
        apply((CitationCanceled event) ->{

        });
        apply((WeekStateConsulted event) ->{

        });
    }
}
