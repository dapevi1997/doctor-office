package co.com.sofka.model.patient;


import co.com.sofka.model.patient.entities.Review;
import co.com.sofka.model.patient.events.*;
import co.com.sofka.model.patient.generic.EventChange;
import co.com.sofka.model.patient.values.*;

import java.util.HashSet;

public class PatientChange extends EventChange {

    public PatientChange(Patient patient) {
        apply((PatientAdded event) -> {
            patient.clinicHistory = new ClinicHistory(event.getClinicHistory());
            patient.available = new Available(Boolean.valueOf(event.getAvailable()));
            patient.personalData = new PersonalData(event.getIdentity());

        });
        apply((PersonalDataUpdated event) -> {
            patient.personalData = new PersonalData(event.getPersonalData());


        });
        apply((ReviewAdded event) -> {
            Review review = new Review(PatientId.of(event.getPatientId()),ReviewId.of(event.getIdReview()),  new Annotation(event.getAnnotation()));
            patient.reviews = new HashSet<>();
            patient.reviews.add(review);


        });
        apply((ClinicHistoryObtained event) -> {


        });
        apply((PatientDeleted event) -> {
            patient.available = new Available(Boolean.valueOf(event.getAvailable()));


        });


    }
}
