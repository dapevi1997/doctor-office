package co.com.sofka.model.week;

import co.com.sofka.model.patient.events.PatientAdded;
import co.com.sofka.model.patient.generic.AggregateRoot;
import co.com.sofka.model.week.events.WeekAdded;
import co.com.sofka.model.week.values.Availability;
import co.com.sofka.model.week.values.Date;
import co.com.sofka.model.week.values.State;
import co.com.sofka.model.week.values.WeekId;

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
}
