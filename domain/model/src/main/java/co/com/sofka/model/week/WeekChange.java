package co.com.sofka.model.week;

import co.com.sofka.model.patient.entities.Review;
import co.com.sofka.model.patient.generic.EventChange;
import co.com.sofka.model.patient.values.Annotation;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.model.patient.values.ReviewId;
import co.com.sofka.model.week.entities.Citation;
import co.com.sofka.model.week.events.*;
import co.com.sofka.model.week.utils.MapperUtils;
import co.com.sofka.model.week.values.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class WeekChange extends EventChange {
    private MapperUtils mapperUtils;

    public WeekChange(Week week) {

        apply((WeekAdded event) -> {
            this.mapperUtils = new MapperUtils();
            Availability availability = mapperUtils.mapperToStringToAvailability().apply(event.getAvailability());

            week.availability = availability;


        });
        apply((CitationAdded event) -> {

            Citation citation = new Citation(CitationId.of(event.getId()), new Infomation(event.getInformation()), new CitationState(event.getCitationState())
                    , PatientId.of(event.getPatientId()), WeekId.of(event.getWeekId()));

            week.citations = new HashSet<>();
            week.citations.add(citation);





        });
        apply((CitationCanceled event) -> {

        });
        apply((WeekStateConsulted event) -> {

        });
        apply((AvailabilityUpdated event) -> {
            this.mapperUtils = new MapperUtils();
            Availability availability = mapperUtils.mapperToStringToAvailability().apply(event.getAvailability());
            week.availability = availability;

        });
    }
}
