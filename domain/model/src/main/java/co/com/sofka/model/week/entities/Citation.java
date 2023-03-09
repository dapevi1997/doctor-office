package co.com.sofka.model.week.entities;

import co.com.sofka.model.patient.generic.Entity;
import co.com.sofka.model.patient.values.PatientId;
import co.com.sofka.model.week.values.CitationId;
import co.com.sofka.model.week.values.CitationState;
import co.com.sofka.model.week.values.Infomation;

public class Citation extends Entity<CitationId> {
    private Infomation infomation;
    private CitationState citationState;
    private PatientId patientId;

    public Citation(CitationId citationId, Infomation infomation, CitationState citationState, PatientId patientId) {
        super(citationId);
        this.infomation = infomation;
        this.citationState = citationState;
        this.patientId = patientId;
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
}
