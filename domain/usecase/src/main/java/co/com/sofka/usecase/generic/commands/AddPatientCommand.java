package co.com.sofka.usecase.generic.commands;

import co.com.sofka.model.patient.values.*;
import co.com.sofka.usecase.generic.Command;

public class AddPatientCommand extends Command {
    private PatientId patientId;
    private PersonalData personalData;
    private ClinicHistory clinicHistory;
    private ReviewId reviewId;

    private Annotation annotation;



    public AddPatientCommand() {
    }

    public AddPatientCommand(PatientId patientId, PersonalData personalData,
                             ClinicHistory clinicHistory, ReviewId reviewId, Annotation annotation) {
        this.patientId = patientId;
        this.personalData = personalData;
        this.clinicHistory = clinicHistory;
        this.reviewId = reviewId;
        this.annotation = annotation;
    }

    public PatientId getPatientId() {
        return patientId;
    }



    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setIdentity(PersonalData personalData) {
        this.personalData = personalData;
    }

    public ClinicHistory getClinicHistory() {
        return clinicHistory;
    }

    public void setClinicHistory(ClinicHistory clinicHistory) {
        this.clinicHistory = clinicHistory;
    }

    public ReviewId getReviewId() {
        return reviewId;
    }

    public Annotation getAnnotation() {
        return annotation;
    }
}




