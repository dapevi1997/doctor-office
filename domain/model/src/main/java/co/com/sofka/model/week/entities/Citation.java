package co.com.sofka.model.week.entities;

import co.com.sofka.model.patient.generic.Entity;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.model.week.values.CitationId;
import co.com.sofka.model.week.values.CitationState;
import co.com.sofka.model.week.values.Infomation;
import co.com.sofka.model.week.values.WeekId;

public class Citation extends Entity<CitationId> {
    private Infomation infomation;
    private CitationState citationState;
    private PatientId patientId;

    private WeekId weekId;

    public Citation(CitationId citationId, Infomation infomation, CitationState citationState, PatientId patientId, WeekId weekId) {
        super(citationId);
        this.infomation = infomation;
        this.citationState = citationState;
        this.patientId = patientId;
        this.weekId = weekId;
    }

    public Infomation getInfomation() {
        return infomation;
    }

    public CitationState getCitationState() {
        return citationState;
    }

    public PatientId getPatientId() {
        return patientId;
    }

    public WeekId getWeekId() {
        return weekId;
    }
}
