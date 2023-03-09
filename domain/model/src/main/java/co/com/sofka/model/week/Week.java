package co.com.sofka.model.week;

import co.com.sofka.model.patient.events.PatientAdded;
import co.com.sofka.model.patient.generic.AggregateRoot;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.model.week.events.CitationAdded;
import co.com.sofka.model.week.events.CitationCanceled;
import co.com.sofka.model.week.events.WeekAdded;
import co.com.sofka.model.week.events.WeekStateConsulted;
import co.com.sofka.model.week.values.*;

import java.util.Objects;

public class Week extends AggregateRoot<WeekId> {
    //List<Cita>
    private Availability availability;
    private State state;
    private Date date;

    public Week(WeekId weekId, Availability availability, State state, Date date) {
        super(weekId);
        this.availability = availability;
        this.state = state;
        this.date = date;
        subscribe(new WeekChange(this));
        appendChange(new WeekAdded(weekId.value(), availability.value(), state.value(),date.value())).apply();
    }
    public void addCitation(CitationId citationId, Infomation infomation, CitationState citationState, PatientId patientId){
        Objects.requireNonNull(patientId);
        Objects.requireNonNull(citationId);
        Objects.requireNonNull(infomation);
        Objects.requireNonNull(citationState);
        appendChange(new CitationAdded(citationId.value(), infomation.value(), citationState.value(), patientId.value()));

    }
    public void cancelateCitation(){

        appendChange(new CitationCanceled());

    }
    public void consultWeekState(){

        appendChange(new WeekStateConsulted());

    }
}
