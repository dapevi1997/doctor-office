package co.com.sofka.model.week;

import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.PatientChange;
import co.com.sofka.model.patient.events.PatientDeleted;
import co.com.sofka.model.patient.generic.AggregateRoot;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.patient.values.Available;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.model.week.entities.Citation;
import co.com.sofka.model.week.events.*;
import co.com.sofka.model.week.values.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Week extends AggregateRoot<WeekId> {
    protected Set<Citation> citations;
    protected Availability availability;
    protected Date date;



    public Week(WeekId weekId, CitationId citationId, Infomation infomation, CitationState citationState,
                PatientId patientId, Availability availability, Date date) {
        super(weekId);

         subscribe(new WeekChange(this));
        appendChange(new WeekAdded(weekId.value(), citationId.value(), infomation.value(),citationState.value()
                , patientId.value(), availability.getAvailability().toString(),date.value())).apply();
    }

    private Week(WeekId weekId){
        super(weekId);
        subscribe(new WeekChange(this));
    }

    public static Week from(WeekId weekId, List<DomainEvent> events){
        Week week = new Week(weekId);
        events.forEach(domainEvent -> week.applyEvent(domainEvent));
        return week;

    }
    public void addCitation(CitationId citationId, Infomation infomation, CitationState citationState, PatientId patientId, WeekId weekId){
        Objects.requireNonNull(patientId);
        Objects.requireNonNull(citationId);
        Objects.requireNonNull(infomation);
        Objects.requireNonNull(citationState);
        subscribe(new WeekChange(this));
        appendChange(new CitationAdded(citationId.value(), infomation.value(), citationState.value(), patientId.value(), weekId.value()));

    }

    public void updateAvailability(WeekId weekId,Availability availability) {

        Objects.requireNonNull(weekId);
        Objects.requireNonNull(availability);
        subscribe(new WeekChange(this));
        appendChange(new AvailabilityUpdated(weekId.value(), availability.getAvailability().toString()));
    }


    public void cancelateCitation(WeekId weekId, CitationId citationId, CitationState citationState){
        Objects.requireNonNull(weekId);
        Objects.requireNonNull(citationId);
        Objects.requireNonNull(citationState);
        subscribe(new WeekChange(this));
        appendChange(new CitationCanceled(weekId.value(), citationId.value(), citationState.value()));

    }
    public void consultWeekState(){

        appendChange(new WeekStateConsulted());

    }

    public Set<Citation> getCitations() {
        return citations;
    }

    public Availability getAvailability() {
        return availability;
    }


    public Date getDate() {
        return date;
    }



}
