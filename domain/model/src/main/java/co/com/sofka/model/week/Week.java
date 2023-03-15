package co.com.sofka.model.week;

import co.com.sofka.model.patient.Patient;
import co.com.sofka.model.patient.PatientChange;
import co.com.sofka.model.patient.generic.AggregateRoot;
import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.model.week.entities.Citation;
import co.com.sofka.model.week.events.CitationAdded;
import co.com.sofka.model.week.events.CitationCanceled;
import co.com.sofka.model.week.events.WeekAdded;
import co.com.sofka.model.week.events.WeekStateConsulted;
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
        appendChange(new CitationAdded(citationId.value(), infomation.value(), citationState.value(), patientId.value(), weekId.value()));

    }
    public void cancelateCitation(){

        appendChange(new CitationCanceled());

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
